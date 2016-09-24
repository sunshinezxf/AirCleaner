package dist.purify.air.dao.impl;

import dist.purify.air.dao.BaseDao;
import dist.purify.air.dao.OrderDao;
import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.utils.IDGenerator;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
                logger.error(e.getMessage());
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
            logger.error(e.getMessage());
            result.setResponseCode(ResponseCode.RESPONSE_ERROR);
            result.setDescription(e.getMessage());
        } finally {
            return result;
        }
    }
}
