package dist.purify.air.service;

import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.utils.ResultData;

import java.util.Map;

/**
 * Created by sunshine on 8/28/16.
 */
public interface QRCodeService {
    ResultData createQRCode(String prefix, int num, String goodsId);

    ResultData fetchQRCode(Map<String, Object> condition);

    ResultData fetchQRCode(Map<String, Object> condition, DataTableParam param);
}
