package dist.purify.air.model.qrcode;

import dist.purify.air.model.Entity;

/**
 * Created by sunshine on 2016/10/16.
 */
public class CodeKind extends Entity {
    private String kindId;

    private String kindName;

    private String kindDescription;

    public CodeKind() {
        super();
    }

    public CodeKind(String kindName, String kindDescription) {
        this();
        this.kindName = kindName;
        this.kindDescription = kindDescription;
    }

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKindDescription() {
        return kindDescription;
    }

    public void setKindDescription(String kindDescription) {
        this.kindDescription = kindDescription;
    }
}
