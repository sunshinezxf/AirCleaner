package dist.purify.air.service.impl;

import com.alibaba.fastjson.JSON;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import dist.purify.air.dao.BillDao;
import dist.purify.air.model.bill.OrderBill;
import dist.purify.air.service.ChargeService;
import dist.purify.air.utils.PlatformConfig;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunshine on 16/9/22.
 */
@Service
public class ChargeServiceImpl implements ChargeService {
    private Logger logger = LoggerFactory.getLogger(ChargeServiceImpl.class);

    {
        Pingpp.apiKey = PlatformConfig.instance().getValue("pingxx_api_key");
    }

    @Autowired
    private BillDao billDao;

    @Override
    public ResultData createCharge(OrderBill bill, String openId) {
        ResultData result = new ResultData();
        Map<String, Object> params = new HashMap<>();
        params.put("order_no", bill.getBillId());
        params.put("amount", bill.getBillAmount() * 100);
        Map<String, Object> app = new HashMap<>();
        app.put("id", PlatformConfig.instance().getValue("pingxx_app_id"));
        params.put("app", app);
        params.put("channel", bill.getChannel());
        if (!StringUtils.isEmpty(bill.getChannel()) && bill.getChannel().equals("wx_pub")) {
            Map<String, String> user = new HashMap<>();
            user.put("open_id", openId);
            params.put("extra", user);
        }
        if (!StringUtils.isEmpty(bill.getChannel()) && bill.getChannel().equals("alipay_wap")) {
            Map<String, String> url = new HashMap<>();
            String server = PlatformConfig.instance().getValue("server_url");
            String success = new StringBuffer("http://").append(server).append("/payment/").append(bill.getBillId()).append("/result/success").toString();
            String cancel = new StringBuffer("http://").append(server).append("/payment/").append(bill.getBillId()).append("/result/failure").toString();
            url.put("success_url", success);
            url.put("cancel_url", cancel);
            params.put("extra", url);
        }
        params.put("currency", "cny");
        params.put("client_ip", bill.getClientIp());
        params.put("subject", "订单支付");
        params.put("body", "支付金额为:" + bill.getBillAmount() + "元");
        try {
            Charge charge = Charge.create(params);
            logger.debug(JSON.toJSONString(charge));
            result.setData(charge);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResponseCode(ResponseCode.RESPONSE_ERROR);
            result.setDescription(e.getMessage());
        }
        return result;
    }
}
