package dist.purify.air.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by sunshine on 16/9/11.
 */
public class QRConfigForm {
    @NotEmpty
    private String prefix;

    @NotEmpty
    private String quantity;

    @NotEmpty
    private String goodsId;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
