package dist.purify.air.controller;

import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.model.Charge;
import dist.purify.air.model.bill.OrderBill;
import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.service.BillService;
import dist.purify.air.service.ChargeService;
import dist.purify.air.service.OrderService;
import dist.purify.air.utils.IPUtil;
import dist.purify.air.utils.JSONUtil;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunshine on 16/9/22.
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private BillService billService;

    @Autowired
    private ChargeService chargeService;

    @RequestMapping(method = RequestMethod.POST, value = "/consumer/create")
    public Charge charge(HttpServletRequest request) {
        Charge result = new Charge();
        JSONObject params = JSONUtil.getParams(request);
        String clientIp = IPUtil.getIP(request);
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderId", String.valueOf(params.get("order_id")));
        ResultData response = orderService.fetchConsumerOrder(condition);
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            return result;
        }
        ConsumerOrder order = ((List<ConsumerOrder>) response.getData()).get(0);
        OrderBill bill = new OrderBill(order, Double.parseDouble(String.valueOf(params.get("amount"))), params.getString("channel"), params.getString("open_id"), clientIp);
        response = billService.createBill(bill);
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            return result;
        }
        response = chargeService.createCharge(bill, params.getString("open_id"));
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result = (Charge) response.getData();
        }
        return result;
    }
}
