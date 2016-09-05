package dist.purify.air.service.impl;

import dist.purify.air.service.QRCodeService;
import dist.purify.air.utils.IDGenerator;
import dist.purify.air.utils.PlatformConfig;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunshine on 8/28/16.
 */
@Service
public class QRCodeServiceImpl implements QRCodeService {
    private Logger logger = LoggerFactory.getLogger(QRCodeServiceImpl.class);

    private Object lock = new Object();

    public ResultData batch(int num) {
        PlatformConfig config = PlatformConfig.instance();
        ResultData result = new ResultData();
        if (num <= 0) {
            result.setResponseCode(ResponseCode.RESPONSE_NULL);
            result.setDescription("需要批量生成的二维码的数量为:" + num + ", 无需进行处理");
            return result;
        }
        synchronized (lock) {
            Map<String, String> assembly = new HashMap<String, String>();
            Map<String, String> receipt = new HashMap<String, String>();
            for (int i = 0; i < num; i++) {
                String device = IDGenerator.generate("EM");
                String purchase = new StringBuffer("http://").append(config.getValue("server_url")).append("/").append(device).append("/purchase").toString();
                String bind = new StringBuffer("http://").append(config.getValue("server_url")).append("/").append(device).append("/bind").toString();

            }
        }
        return null;
    }

    private String generate() {
        return "";
    }
}
