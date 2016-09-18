package dist.purify.air.dao;

import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.utils.ResultData;
import model.goods.Goods4Customer;

import java.util.Map;

/**
 * Created by sunshine on 16/9/13.
 */
public interface GoodsDao {
    ResultData queryGoods(Map<String, Object> condition);

    ResultData queryGoods(Map<String, Object> condition, DataTableParam param);

    ResultData insertGoods(Goods4Customer goods);
}
