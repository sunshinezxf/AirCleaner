package dist.purify.air.model.goods;

/**
 * Created by sunshine on 8/27/16.
 */
public class Goods4Customer extends AbstractGoods {
    private double primePrice;

    private double sharePrice;

    public Goods4Customer() {
        super();
    }

    public Goods4Customer(String goodsName, String goodsDescription) {
        this();
        this.setGoodsName(goodsName);
        this.setGoodsDescription(goodsDescription);
    }

    public Goods4Customer(String goodsName, double primePrice, double sharePrice, String goodsDescription) {
        this(goodsName, goodsDescription);
        this.primePrice = primePrice;
        this.sharePrice = sharePrice;
    }

    public double getPrimePrice() {
        return primePrice;
    }

    public void setPrimePrice(double primePrice) {
        this.primePrice = primePrice;
    }

    public double getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(double sharePrice) {
        this.sharePrice = sharePrice;
    }
}
