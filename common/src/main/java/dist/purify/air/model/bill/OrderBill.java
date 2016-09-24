package dist.purify.air.model.bill;

import dist.purify.air.model.bill.ext.BillStatus;
import dist.purify.air.model.order.ConsumerOrder;

/**
 * Created by sunshine on 16/9/21.
 */
public class OrderBill extends Bill {
    private ConsumerOrder order;

    public OrderBill() {
        super();
        setStatus(BillStatus.NOT_PAYED);
    }

    public OrderBill(ConsumerOrder order, double billAmount, String channel, String payAccount, String clientIp) {
        this();
        this.order = order;
        setBillAmount(billAmount);
        setChannel(channel);
        setPayAccount(payAccount);
        setClientIp(clientIp);
    }


    public ConsumerOrder getOrder() {
        return order;
    }

    public void setOrder(ConsumerOrder order) {
        this.order = order;
    }
}
