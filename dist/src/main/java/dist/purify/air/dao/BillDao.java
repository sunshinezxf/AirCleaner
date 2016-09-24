package dist.purify.air.dao;

import dist.purify.air.model.bill.OrderBill;
import dist.purify.air.utils.ResultData;

import java.util.Map;

/**
 * Created by sunshine on 16/9/21.
 */
public interface BillDao {
    ResultData insertBill(OrderBill bill);

    ResultData queryBill(Map<String, Object> condition);
}
