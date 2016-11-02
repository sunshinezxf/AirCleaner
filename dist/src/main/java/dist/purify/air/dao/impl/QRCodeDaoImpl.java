package dist.purify.air.dao.impl;

import dist.purify.air.dao.BaseDao;
import dist.purify.air.dao.QRCodeDao;
import dist.purify.air.model.qrcode.CodeKind;
import dist.purify.air.model.qrcode.QRCode;
import dist.purify.air.pagination.DataTablePage;
import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.utils.IDGenerator;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sunshine on 16/9/17.
 */
@Repository
public class QRCodeDaoImpl extends BaseDao implements QRCodeDao {
    private Logger logger = LoggerFactory.getLogger(QRCodeDaoImpl.class);

    private Object lock = new Object();

    @Override
    public ResultData insertQRCode(QRCode code) {
        ResultData result = new ResultData();
        code.setCodeId(IDGenerator.generate("QRC"));
        synchronized (lock) {
            try {
                sqlSession.insert("purify.air.qrcode.insert", code);
                result.setData(code);
            } catch (Exception e) {
                logger.error(e.getMessage());
                result.setResponseCode(ResponseCode.RESPONSE_OK);
                result.setDescription(e.getMessage());
            } finally {
                return result;
            }
        }
    }

    @Override
    public ResultData queryQRCode(Map<String, Object> condition) {
        ResultData result = new ResultData();
        try {
            List<QRCode> list = sqlSession.selectList("purify.air.qrcode.query", condition);
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

    @Override
    public ResultData queryQRCode(Map<String, Object> condition, DataTableParam param) {
        ResultData result = new ResultData();
        DataTablePage<QRCode> page = new DataTablePage<>(param);
        ResultData total = queryQRCode(condition);
        if (total.getResponseCode() == ResponseCode.RESPONSE_ERROR) {
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
        List<QRCode> current = queryQRCode(condition, param.getiDisplayStart(), param.getiDisplayLength());
        if (current.isEmpty()) {
            result.setResponseCode(ResponseCode.RESPONSE_NULL);
        }
        page.setData(current);
        result.setData(page);
        return result;
    }

    @Override
    public ResultData queryQRCodeKind(Map<String, Object> condition) {
        ResultData result = new ResultData();
        try {
            List<CodeKind> list = sqlSession.selectList("purify.air.code.kind.query", condition);
            if (list.isEmpty()) {
                result.setResponseCode(ResponseCode.RESPONSE_NULL);
            }
            result.setData(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResponseCode(ResponseCode.RESPONSE_ERROR);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    private List<QRCode> queryQRCode(Map<String, Object> condition, int start, int length) {
        List<QRCode> list = new ArrayList<>();
        try {
            list = sqlSession.selectList("purify.air.qrcode.query", condition, new RowBounds(start, length));
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            return list;
        }
    }
}
