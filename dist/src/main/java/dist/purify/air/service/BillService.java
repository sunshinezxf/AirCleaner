package dist.purify.air.service;

import dist.purify.air.model.bill.OrderBill;
import dist.purify.air.utils.ResultData;

import java.util.Map;

/**
 * Created by sunshine on 16/9/21.
 */
public interface BillService {
    ResultData createBill(OrderBill bill);

    ResultData fetchBill(Map<String, Object> condition);
}
