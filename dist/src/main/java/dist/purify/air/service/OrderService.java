package dist.purify.air.service;

import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.model.qrcode.QRCode;
import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.utils.ResultData;

import java.util.Map;

/**
 * Created by sunshine on 16/9/22.
 */
public interface OrderService {
    ResultData createConsumerOrder(ConsumerOrder order);

    ResultData fetchConsumerOrder(Map<String, Object> condition);

    ResultData fetchConsumerOrder(Map<String, Object> condition, DataTableParam param);

    ResultData renovateConsumerOrder(ConsumerOrder order);
}
