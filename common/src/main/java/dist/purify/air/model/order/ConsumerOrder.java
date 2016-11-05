package dist.purify.air.model.order;

import dist.purify.air.model.goods.Goods4Customer;
import dist.purify.air.model.goods.GoodsAssign;
import dist.purify.air.utils.GoodsUtil;

/**
 * Created by sunshine on 16/9/19.
 */
public class ConsumerOrder extends AbstractOrder {

    private Goods4Customer goods;

    private String clientId;

    private String consumerWechat;

    private String consumerName;

    private String consumerPhone;

    private String consumerAddress;

    private GoodsAssign assign;


    public ConsumerOrder() {
        super();
    }

    public ConsumerOrder(String consumerWechat) {
        this();
        this.consumerWechat = consumerWechat;
    }

    public ConsumerOrder(String consumerWechat, String consumerName, String consumerPhone, String consumerAddress) {
        this(consumerWechat);
        this.consumerName = consumerName;
        this.consumerPhone = consumerPhone;
        this.consumerAddress = consumerAddress;
    }

    public ConsumerOrder(String clientId, String consumerWechat, String consumerName, String consumerPhone, String consumerAddress, Goods4Customer goods, double purchasePrice, int goodsQuantity) {
        this(consumerWechat, consumerName, consumerPhone, consumerAddress);
        this.clientId = clientId;
        this.goods = goods;
        setPurchasePrice(purchasePrice);
        setGoodsQuantity(goodsQuantity);
        setTotalFee(GoodsUtil.calculateOrderFee(purchasePrice, goodsQuantity));
    }

    public Goods4Customer getGoods() {
        return goods;
    }

    public void setGoods(Goods4Customer goods) {
        this.goods = goods;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getConsumerWechat() {
        return consumerWechat;
    }

    public void setConsumerWechat(String consumerWechat) {
        this.consumerWechat = consumerWechat;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }

    public void setConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }

    public String getConsumerAddress() {
        return consumerAddress;
    }

    public void setConsumerAddress(String consumerAddress) {
        this.consumerAddress = consumerAddress;
    }

    public GoodsAssign getAssign() {
        return assign;
    }

    public void setAssign(GoodsAssign assign) {
        this.assign = assign;
    }
}
