package dist.purify.air.model.express;

import dist.purify.air.model.order.ConsumerOrder;

/**
 * Created by sunshine on 2016/10/16.
 */
public class Express4CO extends Express {
    private ConsumerOrder order;

    public Express4CO() {
        super();
    }

    public Express4CO(ConsumerOrder order) {
        this();
        this.order = order;
        this.setReceiverName(order.getConsumerName());
        this.setReceiverPhone(order.getConsumerPhone());
        this.setReceiverAddress(order.getConsumerAddress());
    }

    public Express4CO(ConsumerOrder order, String senderName, String senderPhone, String senderAddress) {
        this(order);
        this.setSenderName(senderName);
        this.setSenderPhone(senderPhone);
        this.setSenderAddress(senderAddress);
    }
}
