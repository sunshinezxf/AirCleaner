package dist.purify.air.model.goods;

import dist.purify.air.model.Entity;

/**
 * Created by sunshine on 8/27/16.
 */
public class GoodsThumbnail extends Entity {
    private String thumbnailId;
    private String thumbnailPath;
    private Goods4Customer goods;

    public String getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(String thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Goods4Customer getGoods() {
        return goods;
    }

    public void setGoods(Goods4Customer goods) {
        this.goods = goods;
    }
}
