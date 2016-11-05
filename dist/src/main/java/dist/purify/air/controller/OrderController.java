package dist.purify.air.controller;

import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.model.order.ext.OrderStatus;
import dist.purify.air.pagination.DataTablePage;
import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.service.OrderService;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunshine on 2016/10/8.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "/overview")
    public ModelAndView overview() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/backend/order/overview");
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/overview")
    public DataTablePage<ConsumerOrder> overview(DataTableParam param) {
        DataTablePage<ConsumerOrder> result = new DataTablePage<>(param);
        if (StringUtils.isEmpty(param)) {
            return result;
        }
        Map<String, Object> condition = new HashMap<>();
        ResultData response = orderService.fetchConsumerOrder(condition, param);
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            return result;
        }
        result = (DataTablePage<ConsumerOrder>) response.getData();
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ModelAndView search() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/client/order/search");
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public ModelAndView search(String phone) {
        ModelAndView view = new ModelAndView();
        if (StringUtils.isEmpty(phone)) {
            view.setViewName("redirect:/order/search");
        }
        Map<String, Object> condition = new HashMap<>();
        condition.put("consumerPhone", phone);
        condition.put("status", OrderStatus.PAYED.getCode());
        condition.put("blockFlag", false);
        ResultData response = orderService.fetchConsumerOrder(condition);
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            List<ConsumerOrder> list = (List<ConsumerOrder>) response.getData();
            view.addObject("list", list);
        }
        view.setViewName("/client/order/list");
        return view;
    }
}
