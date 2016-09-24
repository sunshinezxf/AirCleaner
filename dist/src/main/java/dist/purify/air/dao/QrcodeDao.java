package dist.purify.air.dao;

import dist.purify.air.utils.ResultData;
import dist.purify.air.model.qrcode.QRCode;

import java.util.Map;

/**
 * Created by sunshine on 16/9/17.
 */
public interface QRCodeDao {
    ResultData insertQRCode(QRCode code);

    ResultData queryQRCode(Map<String, Object> condition);
}
