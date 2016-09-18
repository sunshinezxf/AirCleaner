package dist.purify.air.form;

import javax.validation.constraints.NotNull;

/**
 * Created by sunshine on 16/9/17.
 */
public class GoodsForm {

    @NotNull
    private String goodsName;

    private String description;

    @NotNull
    private String primePrice;

    @NotNull
    private String sharePrice;


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimePrice() {
        return primePrice;
    }

    public void setPrimePrice(String primePrice) {
        this.primePrice = primePrice;
    }

    public String getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(String sharePrice) {
        this.sharePrice = sharePrice;
    }

}
