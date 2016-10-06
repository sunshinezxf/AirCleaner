package dist.purify.air.model.authc;

import java.util.List;

/**
 * Created by sunshine on 2016/10/2.
 */
public class User extends AbstractUser {

    private List<UserRole> userRoles;

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
