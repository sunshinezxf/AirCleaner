package dist.purify.air.model.qrcode;

import dist.purify.air.model.Entity;
import dist.purify.air.model.goods.AbstractGoods;

/**
 * Created by sunshine on 16/9/11.
 */
public class QRCode extends Entity {
    private final static String DEFAULT_PATH = "/material/qrcode/default/";
    public final static String DEFAULT_FORMAT = "png";

    private String codeId;
    private AbstractGoods goods;
    private String value;
    private String path;
    private String url;

    public QRCode() {
        super();
    }

    public QRCode(String value) {
        this();
        this.value = value;
        this.path = new StringBuffer(DEFAULT_PATH).append(codeId).append(DEFAULT_FORMAT).toString();
    }

    public QRCode(String value, AbstractGoods goods) {
        this(value);
        this.goods = goods;
    }

    public QRCode(String value, String path) {
        this(value);
        this.path = path;
    }

    public QRCode(String value, String path, String url) {
        this(value, path);
        this.url = url;
    }

    public QRCode(String value, String path, AbstractGoods goods) {
        this(value, path);
        this.goods = goods;
    }

    public QRCode(String value, String path, String url, AbstractGoods goods) {
        this(value, path, goods);
        this.url = url;
    }


    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AbstractGoods getGoods() {
        return goods;
    }

    public void setGoods(AbstractGoods goods) {
        this.goods = goods;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
