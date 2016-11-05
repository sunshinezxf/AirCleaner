package dist.purify.air.model.goods;

import dist.purify.air.model.Entity;
import dist.purify.air.model.goods.ext.GoodsType;

/**
 * Created by sunshine on 6/16/16.
 */
public abstract class AbstractGoods extends Entity {

    private String goodsId;

    private String goodsName;

    private String goodsDescription;

    private GoodsType goodsType;

    private String suffix;

    public AbstractGoods() {
        this.goodsType = GoodsType.REAL;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
