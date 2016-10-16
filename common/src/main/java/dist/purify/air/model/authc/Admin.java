package dist.purify.air.model.authc;

/**
 * Created by sunshine on 2016/10/14.
 */
public class Admin extends AbstractUser {

    public Admin() {
        super();
    }

    public Admin(String username, String password) {
        this();
        this.setUsername(username);
        this.setPassword(password);
    }
}
