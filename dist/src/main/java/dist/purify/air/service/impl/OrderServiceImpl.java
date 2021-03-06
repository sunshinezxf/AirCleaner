package dist.purify.air.service.impl;

import dist.purify.air.dao.OrderAssignDao;
import dist.purify.air.dao.OrderDao;
import dist.purify.air.model.goods.GoodsAssign;
import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.service.OrderService;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by sunshine on 16/9/22.
 */
@Service
public class OrderServiceImpl implements OrderService {
    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderAssignDao orderAssignDao;

    @Override
    public ResultData createConsumerOrder(ConsumerOrder order) {
        ResultData result = new ResultData();
        ResultData response = orderDao.insertConsumerOrder(order);
        result.setResponseCode(response.getResponseCode());
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result.setData(response.getData());
        } else {
            result.setDescription(response.getDescription());
        }
        return result;
    }

    @Override
    public ResultData fetchConsumerOrder(Map<String, Object> condition) {
        ResultData result = new ResultData();
        ResultData response = orderDao.queryConsumerOrder(condition);
        result.setResponseCode(response.getResponseCode());
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result.setData(response.getData());
        } else if (response.getResponseCode() == ResponseCode.RESPONSE_NULL) {
            result.setData(response.getData());
        } else {
            result.setDescription(response.getDescription());
        }
        return result;
    }

    @Override
    public ResultData fetchConsumerOrder(Map<String, Object> condition, DataTableParam param) {
        ResultData result = new ResultData();
        ResultData response = orderDao.queryConsumerOrder(condition, param);
        result.setResponseCode(response.getResponseCode());
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result.setData(response.getData());
        } else if (response.getResponseCode() == ResponseCode.RESPONSE_NULL) {
            result.setData(response.getData());
        } else {
            result.setDescription(response.getDescription());
        }
        return result;
    }

    @Override
    public ResultData renovateConsumerOrder(ConsumerOrder order) {
        ResultData result = new ResultData();
        ResultData response = orderDao.updateConsumerOrder(order);
        result.setResponseCode(response.getResponseCode());
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result.setData(response.getData());
        } else {
            result.setDescription(response.getDescription());
        }
        return result;
    }

    @Override
    public ResultData fetchOrderAssign(Map<String, Object> condition) {
        ResultData result = new ResultData();
        ResultData response = orderAssignDao.queryAssign(condition);
        result.setResponseCode(response.getResponseCode());
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result.setData(response.getData());
        } else if (response.getResponseCode() == ResponseCode.RESPONSE_ERROR) {
            result.setDescription(response.getDescription());
        }
        return result;
    }

    @Override
    public ResultData createOrderAssign(GoodsAssign assign) {
        ResultData result = new ResultData();
        ResultData response = orderAssignDao.insertAssign(assign);
        result.setResponseCode(response.getResponseCode());
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result.setData(response.getData());
        } else if (response.getResponseCode() == ResponseCode.RESPONSE_ERROR) {
            result.setDescription(response.getDescription());
        }
        return result;
    }
}
