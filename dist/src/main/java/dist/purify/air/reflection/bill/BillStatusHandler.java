package dist.purify.air.reflection.bill;

import dist.purify.air.model.bill.ext.BillStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunshine on 16/9/21.
 */
public class BillStatusHandler extends BaseTypeHandler<BillStatus> {
    private Class<BillStatus> status;

    private final BillStatus[] enums;

    private Logger logger = LoggerFactory.getLogger(BillStatusHandler.class);

    public BillStatusHandler(Class<BillStatus> status) {
        if (status == null) {
            throw new IllegalArgumentException("参数status不能为空");
        }
        this.status = status;
        enums = status.getEnumConstants();
        if (enums == null) {
            throw new IllegalArgumentException(status.getSimpleName() + "不是一个枚举类型");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BillStatus billStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, billStatus.getCode());
    }

    @Override
    public BillStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int i = resultSet.getInt(s);
        if (resultSet.wasNull()) {
            return null;
        } else {
            return locateEnumStatus(i);
        }
    }

    @Override
    public BillStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int index = resultSet.getInt(i);
        if (resultSet.wasNull()) {
            return null;
        } else {
            return locateEnumStatus(index);
        }
    }

    @Override
    public BillStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int index = callableStatement.getInt(i);
        if (callableStatement.wasNull()) {
            return null;
        } else {
            return locateEnumStatus(index);
        }
    }

    private BillStatus locateEnumStatus(int code) {
        for (BillStatus status : enums) {
            if (status.getCode() == (Integer.valueOf(code))) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的枚举类型: " + code + ", 请核对" + this.status.getSimpleName());
    }
}
