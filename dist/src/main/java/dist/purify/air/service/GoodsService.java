package dist.purify.air.service;

import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.utils.ResultData;
import model.goods.Goods4Customer;

import java.util.Map;

/**
 * Created by sunshine on 16/9/17.
 */
public interface GoodsService {
    ResultData createGoods(Goods4Customer goods);

    ResultData queryGoods(Map<String, Object> condition);

    ResultData queryGoods(Map<String, Object> condition, DataTableParam param);
}
