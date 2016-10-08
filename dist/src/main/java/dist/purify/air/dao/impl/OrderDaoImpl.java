package dist.purify.air.dao.impl;

import com.alibaba.fastjson.JSON;
import dist.purify.air.dao.BaseDao;
import dist.purify.air.dao.OrderDao;
import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.pagination.DataTablePage;
import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.utils.IDGenerator;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sunshine on 16/9/22.
 */
@Repository
public class OrderDaoImpl extends BaseDao implements OrderDao {
    private Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    private Object lock = new Object();

    @Override
    public ResultData insertConsumerOrder(ConsumerOrder order) {
        ResultData result = new ResultData();
        order.setOrderId(IDGenerator.generate("COR"));
        synchronized (lock) {
            try {
                sqlSession.insert("purify.air.order.consumer_order.insert", order);
                result.setData(order);
            } catch (Exception e) {
                logger.error("插入订单异常: " + e.getMessage() + ", 订单信息为: " + JSON.toJSONString(order));
                result.setResponseCode(ResponseCode.RESPONSE_ERROR);
                result.setDescription(e.getMessage());
            } finally {
                return result;
            }
        }
    }

    @Override
    public ResultData queryConsumerOrder(Map<String, Object> condition) {
        ResultData result = new ResultData();
        try {
            List<ConsumerOrder> list = sqlSession.selectList("purify.air.order.consumer_order.query", condition);
            if (list.isEmpty()) {
                result.setResponseCode(ResponseCode.RESPONSE_NULL);
            }
            result.setData(list);
        } catch (Exception e) {
            logger.error("订单信息查询异常: " + e.getMessage());
            result.setResponseCode(ResponseCode.RESPONSE_ERROR);
            result.setDescription(e.getMessage());
        } finally {
            return result;
        }
    }

    @Override
    public ResultData queryConsumerOrder(Map<String, Object> condition, DataTableParam param) {
        ResultData result = new ResultData();
        DataTablePage<ConsumerOrder> page = new DataTablePage<>(param);
        ResultData total = queryConsumerOrder(condition);
        if (total.getResponseCode() == ResponseCode.RESPONSE_ERROR) {
            result.setResponseCode(ResponseCode.RESPONSE_ERROR);
            result.setDescription(total.getDescription());
            return result;
        } else if (total.getResponseCode() == ResponseCode.RESPONSE_NULL) {
            result.setResponseCode(ResponseCode.RESPONSE_NULL);
            result.setDescription(total.getDescription());
            return result;
        }
        page.setiTotalRecords(((List) total.getData()).size());
        page.setiTotalDisplayRecords(((List) total.getData()).size());
        List<ConsumerOrder> current = queryConsumerOrder(condition, param.getiDisplayStart(), param.getiDisplayLength());
        if (current.isEmpty()) {
            result.setResponseCode(ResponseCode.RESPONSE_NULL);
        }
        page.setData(current);
        result.setData(page);
        return result;
    }

    @Override
    public ResultData updateConsumerOrder(ConsumerOrder order) {
        ResultData result = new ResultData();
        synchronized (lock) {
            try {
                sqlSession.update("purify.air.order.consumer_order.update", order);
                result.setData(order);
            } catch (Exception e) {
                logger.error("订单状态更新异常:" + e.getMessage());
                result.setResponseCode(ResponseCode.RESPONSE_ERROR);
                result.setDescription(e.getMessage());
            } finally {
                return result;
            }
        }
    }

    private List<ConsumerOrder> queryConsumerOrder(Map<String, Object> condition, int start, int length) {
        List<ConsumerOrder> list = new ArrayList<>();
        try {
            list = sqlSession.selectList("purify.air.order.consumer_order.query", condition, new RowBounds(start, length));
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            return list;
        }
    }
}
