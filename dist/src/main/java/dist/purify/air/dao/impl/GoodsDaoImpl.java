package dist.purify.air.dao.impl;

import dist.purify.air.dao.BaseDao;
import dist.purify.air.dao.GoodsDao;
import dist.purify.air.pagination.DataTablePage;
import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.utils.IDGenerator;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import model.goods.Goods4Customer;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sunshine on 16/9/13.
 */
@Repository
public class GoodsDaoImpl extends BaseDao implements GoodsDao {
    private Logger logger = LoggerFactory.getLogger(GoodsDaoImpl.class);

    private Object lock = new Object();

    /**
     * 插入商品记录
     *
     * @param goods
     * @return
     */
    @Override
    public ResultData insertGoods(Goods4Customer goods) {
        ResultData result = new ResultData();
        goods.setGoodsId(IDGenerator.generate("GOD"));
        synchronized (lock) {
            try {
                sqlSession.insert("purify.air.goods.insert", goods);
                result.setData(goods);
            } catch (Exception e) {
                logger.error(e.getMessage());
                result.setResponseCode(ResponseCode.RESPONSE_ERROR);
                result.setDescription(e.getMessage());
            } finally {
                return result;
            }
        }
    }

    /**
     * 查询商品记录
     *
     * @param condition
     * @return
     */
    @Override
    public ResultData queryGoods(Map<String, Object> condition) {
        ResultData result = new ResultData();
        try {
            List list = sqlSession.selectList("purify.air.goods.query", condition);
            if (list.isEmpty()) {
                result.setResponseCode(ResponseCode.RESPONSE_NULL);
            }
            result.setData(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResponseCode(ResponseCode.RESPONSE_ERROR);
            result.setDescription(e.getMessage());
        } finally {
            return result;
        }
    }

    /**
     * 后台分页查询商品记录
     *
     * @param condition
     * @param param
     * @return
     */
    @Override
    public ResultData queryGoods(Map<String, Object> condition, DataTableParam param) {
        ResultData result = new ResultData();
        DataTablePage<Goods4Customer> page = new DataTablePage<>(param);
        ResultData total = queryGoods(condition);
        if (total.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result.setResponseCode(ResponseCode.RESPONSE_ERROR);
            result.setDescription(total.getDescription());
            return result;
        } else if (total.getResponseCode() == ResponseCode.RESPONSE_NULL) {
            result.setResponseCode(ResponseCode.RESPONSE_NULL);
            result.setDescription(total.getDescription());
            return result;
        }
        page.setiTotalRecords(((List) total.getData()).size());
        page.setiTotalDisplayRecords(((List) total.getData()).size());
        List<Goods4Customer> current = queryGoods(condition, param.getiDisplayStart(), param.getiDisplayLength());
        if (current.isEmpty()) {
            result.setResponseCode(ResponseCode.RESPONSE_NULL);
        }
        page.setData(current);
        result.setData(page);
        return result;
    }

    private List<Goods4Customer> queryGoods(Map<String, Object> condition, int start, int length) {
        List<Goods4Customer> list = new ArrayList<>();
        try {
            list = sqlSession.selectList("purify.air.goods.query", condition, new RowBounds(start, length));
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            return list;
        }
    }
}
