package dist.purify.air.reflection.order;

import dist.purify.air.model.order.ext.OrderStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunshine on 16/9/22.
 */
public class OrderStatusHandler extends BaseTypeHandler<OrderStatus> {
    private Class<OrderStatus> status;

    private final OrderStatus[] enums;

    private Logger logger = LoggerFactory.getLogger(OrderStatusHandler.class);

    public OrderStatusHandler(Class<OrderStatus> status) {
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
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, OrderStatus orderStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, orderStatus.getCode());
    }

    @Override
    public OrderStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int i = resultSet.getInt(s);
        if (resultSet.wasNull()) {
            return null;
        } else {
            return locateEnumStatus(i);
        }
    }

    @Override
    public OrderStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int index = resultSet.getInt(i);
        if (resultSet.wasNull()) {
            return null;
        } else {
            return locateEnumStatus(index);
        }
    }

    @Override
    public OrderStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int index = callableStatement.getInt(i);
        if (callableStatement.wasNull()) {
            return null;
        } else {
            return locateEnumStatus(index);
        }
    }

    private OrderStatus locateEnumStatus(int code) {
        for (OrderStatus status : enums) {
            if (status.getCode() == (Integer.valueOf(code))) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的枚举类型: " + code + ", 请核对" + this.status.getSimpleName());
    }
}
