package model.goods;

/**
 * Created by sunshine on 8/27/16.
 */
public class Goods4Customer extends AbstractGoods {
    private int primePrice;

    private int sharePrice;

    public Goods4Customer() {
        super();
    }

    public Goods4Customer(String goodsName, String goodsDescription) {
        this();
        this.setGoodsName(goodsName);
        this.setGoodsDescription(goodsDescription);
    }

    public Goods4Customer(String goodsName, int primePrice, int sharePrice, String goodsDescription) {
        this(goodsName, goodsDescription);
        this.primePrice = primePrice;
        this.sharePrice = sharePrice;
    }

    public int getPrimePrice() {
        return primePrice;
    }

    public void setPrimePrice(int primePrice) {
        this.primePrice = primePrice;
    }

    public int getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(int sharePrice) {
        this.sharePrice = sharePrice;
    }
}
