package dist.purify.air.model.user;

/**
 * Created by sunshine on 16/9/13.
 */
public class Customer extends AbstractUser {
    private String phone;

    public Customer() {
        super();
    }

    public Customer(String phone) {
        this();
        this.setPhone(phone);
    }

    public Customer(String username, String password, String phone) {
        this(phone);
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
