package dist.purify.air.service;

import dist.purify.air.model.bill.OrderBill;
import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.utils.ResultData;

/**
 * Created by sunshine on 16/9/22.
 */
public interface ChargeService {
    ResultData createCharge(OrderBill bill, String openId);
}
