package dist.purify.air.dao;

import dist.purify.air.model.qrcode.QRCode;
import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.utils.ResultData;

import java.util.Map;

/**
 * Created by sunshine on 16/9/17.
 */
public interface QRCodeDao {
    ResultData insertQRCode(QRCode code);

    ResultData queryQRCode(Map<String, Object> condition);

    ResultData queryQRCode(Map<String, Object> condition, DataTableParam param);
}
