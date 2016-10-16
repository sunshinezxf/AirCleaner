package dist.purify.air.vo.prompt;

/**
 * Created by sunshine on 2016/10/13.
 */
public class Prompt {
    private PromptCode code;

    private String message;

    public Prompt() {
        code = PromptCode.SUCCESS;
    }

    public Prompt(String message) {
        this();
        this.message = message;
    }

    public Prompt(PromptCode code, String message) {
        this(message);
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
}
