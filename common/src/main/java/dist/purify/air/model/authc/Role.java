package dist.purify.air.model.authc;

import dist.purify.air.model.Entity;

/**
 * Created by sunshine on 2016/10/2.
 */
public class Role extends Entity {
    private String roleId;

    private String roleName;

    private String roleDescription;

    public Role() {
        super();
    }

    public Role(String roleName, String roleDescription) {
        this();
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
