package dist.purify.air.controller;

import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.model.Charge;
import dist.purify.air.model.bill.OrderBill;
import dist.purify.air.model.bill.ext.BillStatus;
import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.service.BillService;
import dist.purify.air.service.ChargeService;
import dist.purify.air.service.OrderService;
import dist.purify.air.utils.IPUtil;
import dist.purify.air.utils.JSONUtil;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import dist.purify.air.vo.prompt.Prompt;
import dist.purify.air.vo.prompt.PromptCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
        Map<String, Object> condition = new HashMap<>();
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
        bill = (OrderBill) response.getData();
        response = chargeService.createCharge(bill, params.getString("open_id"));
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result = (Charge) response.getData();
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{billId}/result/{status}")
    public ModelAndView prompt(@PathVariable("billId") String billId, @PathVariable("status") String status) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> condition = new HashMap<>();
        String message = "";
        if ("failure".equals(status)) {
            message = "您已取消支付或尚未完成支付";
            Prompt prompt = new Prompt(PromptCode.ERROR, message);
            view.addObject("prompt", prompt);
            view.setViewName("/client/payment/inform");
            return view;
        }
        if (StringUtils.isEmpty(billId)) {
            message = "请求参数不正确";
            Prompt prompt = new Prompt(PromptCode.ERROR, message);
            view.addObject("prompt", prompt);
            view.setViewName("/client/payment/inform");
            return view;
        }
        condition.put("billId", billId);
        ResultData response = billService.fetchBill(condition);
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            message = "系统中暂无此编号的支付订单,请核实";
            Prompt prompt = new Prompt(PromptCode.ERROR, message);
            view.addObject("prompt", prompt);
        } else {
            OrderBill bill = ((List<OrderBill>) response.getData()).get(0);
            if (bill.getStatus() == BillStatus.PAYED) {
                message = "空气堡温馨提示,您的订单已付款成功,您可以关注空气堡在线获取订单信息.";
                Prompt prompt = new Prompt(message);
                view.addObject("prompt", prompt);
            } else if (bill.getStatus() == BillStatus.NOT_PAYED) {
                message = "空气堡温馨提示,您的订单付款正在处理中";
                Prompt prompt = new Prompt(PromptCode.WARNING, message);
                view.addObject("prompt", prompt);
            }
        }
        view.setViewName("/client/payment/inform");
        return view;
    }
}
