package dist.purify.air.service;

import dist.purify.air.utils.ResultData;

import java.util.Map;

/**
 * Created by sunshine on 8/28/16.
 */
public interface QRCodeService {
    ResultData create(String prefix, int num, String goodsId);

    ResultData query(Map<String, Object> condition);
}
