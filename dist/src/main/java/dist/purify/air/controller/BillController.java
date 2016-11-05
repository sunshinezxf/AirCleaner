package dist.purify.air.controller;

import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import dist.purify.air.model.bill.OrderBill;
import dist.purify.air.model.bill.ext.BillStatus;
import dist.purify.air.model.coupon.Coupon;
import dist.purify.air.model.coupon.ext.CouponStatus;
import dist.purify.air.model.goods.AbstractGoods;
import dist.purify.air.model.goods.GoodsAssign;
import dist.purify.air.model.goods.ext.GoodsType;
import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.model.order.ext.OrderStatus;
import dist.purify.air.service.BillService;
import dist.purify.air.service.CouponService;
import dist.purify.air.service.OrderService;
import dist.purify.air.utils.JSONUtil;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunshine on 8/30/16.
 */
@RestController
@RequestMapping("/bill")
public class BillController {
    private final static String ORDER_NO = "order_no";

    private final static String CHARGE_SUCCESS = "charge.succeeded";

    private Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CouponService couponService;

    @RequestMapping(method = RequestMethod.POST, value = "/inform/payment")
    public ResultData inform(HttpServletRequest request, HttpServletResponse response) {
        ResultData result = new ResultData();
        JSONObject webhooks = JSONUtil.getParams(request);
        JSONObject charge = webhooks.getJSONObject("data").getJSONObject("object");
        String billId = charge.getString(ORDER_NO);
        if (StringUtils.isEmpty(billId)) {
            response.setStatus(HttpStatus.OK.value());
            result.setResponseCode(ResponseCode.RESPONSE_NULL);
            result.setDescription("订单号不存在");
            return result;
        }
        Event event = Webhooks.eventParse(webhooks.toString());
        if (CHARGE_SUCCESS.equals(event.getType())) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            result.setDescription("不是订单支付成功的消息通知");
            return result;
        }
        if (billId.startsWith("ODB")) {
            Map<String, Object> condition = new HashMap<>();
            condition.put("billId", billId);
            condition.put("status", BillStatus.NOT_PAYED.getCode());
            ResultData fetchResponse = billService.fetchBill(condition);
            if (fetchResponse.getResponseCode() != ResponseCode.RESPONSE_OK) {
                result.setResponseCode(ResponseCode.RESPONSE_NULL);
                result.setDescription("不存在改编号的支付订单");
                return result;
            }
            OrderBill bill = ((List<OrderBill>) fetchResponse.getData()).get(0);
            bill.setStatus(BillStatus.PAYED);
            ResultData r = billService.modifyBill(bill);
            if (r.getResponseCode() != ResponseCode.RESPONSE_OK) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return result;
            }
            condition.clear();
            condition = new HashMap<>();
            condition.put("orderId", bill.getOrder().getOrderId());
            ConsumerOrder target = bill.getOrder();
            target.setStatus(OrderStatus.PAYED);
            r = orderService.renovateConsumerOrder(target);
            if (r.getResponseCode() != ResponseCode.RESPONSE_OK) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                logger.error("订单状态更新失败");
                return result;
            }
            condition.clear();
            condition.put("orderId", bill.getOrder().getOrderId());
            r = orderService.fetchConsumerOrder(condition);
            target = ((List<ConsumerOrder>) r.getData()).get(0);
            AbstractGoods goods = target.getGoods();
            if (goods.getGoodsType() == GoodsType.VIRTUAL) {
                condition.clear();
                condition.put("status", CouponStatus.VALIDATED.getCode());
                fetchResponse = couponService.fetchCoupon(condition);
                if (fetchResponse.getResponseCode() == ResponseCode.RESPONSE_OK) {
                    Coupon coupon = ((List<Coupon>) fetchResponse.getData()).get(0);
                    GoodsAssign assign = new GoodsAssign(target, "优惠码分配", coupon.getCouponId(), coupon.getCouponSerial());
                    r = orderService.createOrderAssign(assign);
                    if (r.getResponseCode() == ResponseCode.RESPONSE_OK) {
                        logger.debug("优惠码分配成功，订单: " + target.getOrderId() + "分配的优惠码为: " + coupon.getCouponId());
                    }
                    ResultData consumeResponse = couponService.consumeCoupon(coupon);
                    if (consumeResponse.getResponseCode() != ResponseCode.RESPONSE_OK) {
                        logger.error("优惠码分配失败");
                    }
                }
            }
        }
        return result;
    }
}
