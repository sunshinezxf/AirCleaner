package dist.purify.air.dao.impl;

import dist.purify.air.dao.BaseDao;
import dist.purify.air.dao.BillDao;
import dist.purify.air.model.bill.OrderBill;
import dist.purify.air.utils.IDGenerator;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by sunshine on 16/9/21.
 */
@Repository
public class BillDaoImpl extends BaseDao implements BillDao {
    private Logger logger = LoggerFactory.getLogger(BillDaoImpl.class);

    private Object lock = new Object();

    @Override
    public ResultData insertBill(OrderBill bill) {
        ResultData result = new ResultData();
        bill.setBillId(IDGenerator.generate("ODB"));
        synchronized (lock) {
            try {
                sqlSession.insert("purify.air.bill.order_bill.insert", bill);
                result.setData(bill);
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
    public ResultData queryBill(Map<String, Object> condition) {
        ResultData result = new ResultData();
        try {
            List<OrderBill> list = sqlSession.selectList("purify.air.bill.order_bill.query", condition);
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
