package dist.purify.air.service.impl;

import dist.purify.air.dao.GoodsDao;
import dist.purify.air.model.goods.Goods4Customer;
import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.service.GoodsService;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by sunshine on 16/9/17.
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    private Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public ResultData createGoods(Goods4Customer goods) {
        ResultData result = new ResultData();
        ResultData response = goodsDao.insertGoods(goods);
        result.setResponseCode(response.getResponseCode());
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            result.setDescription(response.getDescription());
        } else {
            result.setData(response.getData());
        }
        return result;
    }

    @Override
    public ResultData fetchGoods(Map<String, Object> condition) {
        ResultData result = new ResultData();
        ResultData response = goodsDao.queryGoods(condition);
        result.setResponseCode(response.getResponseCode());
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result.setData(response.getData());
        } else {
            result.setDescription(response.getDescription());
        }
        return result;
    }

    @Override
    public ResultData fetchGoods(Map<String, Object> condition, DataTableParam param) {
        ResultData result = new ResultData();
        ResultData response = goodsDao.queryGoods(condition, param);
        result.setResponseCode(response.getResponseCode());
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result.setData(response.getData());
        } else {
            result.setDescription(response.getDescription());
        }
        return result;
    }
}
