package dist.purify.air.model.order;

import dist.purify.air.model.Entity;
import dist.purify.air.model.order.ext.OrderStatus;

/**
 * Created by sunshine on 16/9/22.
 */
public abstract class AbstractOrder extends Entity {
    private String orderId;

    private double purchasePrice;

    private int goodsQuantity;

    private double totalFee;

    private OrderStatus status;

    public AbstractOrder() {
        super();
        this.status = OrderStatus.NOT_PAYED;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
