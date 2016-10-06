package dist.purify.air.model.authc;

import dist.purify.air.model.Entity;

/**
 * Created by sunshine on 2016/10/3.
 */
public class UserRole extends Entity {
    private User user;

    private Role role;

    public UserRole() {
        super();
    }

    public UserRole(User user, Role role) {
        this();
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
