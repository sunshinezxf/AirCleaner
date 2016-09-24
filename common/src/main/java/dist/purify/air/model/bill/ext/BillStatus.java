package dist.purify.air.model.bill.ext;

/**
 * Created by sunshine on 16/9/21.
 */
public enum BillStatus {
    NOT_PAYED(0), PAYED(1), REFUND(2);

    private int code;

    BillStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
