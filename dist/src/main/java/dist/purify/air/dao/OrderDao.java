package dist.purify.air.dao;

import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.utils.ResultData;

import java.util.Map;

/**
 * Created by sunshine on 16/9/22.
 */
public interface OrderDao {
    ResultData insertConsumerOrder(ConsumerOrder order);

    ResultData queryConsumerOrder(Map<String, Object> condition);
}
