package dist.purify.air.model.order.ext;

/**
 * Created by sunshine on 16/9/22.
 */
public enum OrderStatus {
    SAVED(0), NOT_PAYED(1), PAYED(2), DELIVERED(3), RECEIVED(4);

    private int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
