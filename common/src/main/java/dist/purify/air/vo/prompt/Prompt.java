package dist.purify.air.vo.prompt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunshine on 2016/10/13.
 */
public class Prompt {
    private PromptCode code;

    private String message;

    private List<PromptLink> link;

    public Prompt() {
        code = PromptCode.SUCCESS;
    }

    public Prompt(String message) {
        this();
        this.message = message;
        this.link = new ArrayList<PromptLink>();
    }

    public Prompt(String message, List<PromptLink> link) {
        this();
        this.message = message;
        this.link = link;
    }

    public Prompt(PromptCode code, String message) {
        this(message);
        this.code = code;
    }

    public Prompt(PromptCode code, String message, List<PromptLink> link) {
        this(message, link);
        this.code = code;
    }

    public PromptCode getCode() {
        return code;
    }

    public void setCode(PromptCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PromptLink> getLink() {
        return link;
    }

    public void setLink(List<PromptLink> link) {
        this.link = link;
    }
}
