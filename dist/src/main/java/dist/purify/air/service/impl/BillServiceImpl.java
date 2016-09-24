package dist.purify.air.service.impl;

import dist.purify.air.dao.BillDao;
import dist.purify.air.model.bill.OrderBill;
import dist.purify.air.service.BillService;
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
public class BillServiceImpl implements BillService {
    private Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private BillDao billDao;

    @Override
    public ResultData createBill(OrderBill bill) {
        ResultData result = new ResultData();
        ResultData response = billDao.insertBill(bill);
        result.setResponseCode(response.getResponseCode());
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            result.setDescription(response.getDescription());
        } else {
            result.setData(response.getData());
        }
        return result;
    }

    @Override
    public ResultData fetchBill(Map<String, Object> condition) {
        ResultData result = new ResultData();

        return result;
    }
}
