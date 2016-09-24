package dist.purify.air.model.bill;

import dist.purify.air.model.Entity;
import dist.purify.air.model.bill.ext.BillStatus;

/**
 * Created by sunshine on 16/9/21.
 */
public abstract class Bill extends Entity {
    private String billId;

    private String clientIp;

    private String channel;

    private String payAccount;

    private double billAmount;

    private BillStatus status;

    public Bill() {
        super();
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }
}
