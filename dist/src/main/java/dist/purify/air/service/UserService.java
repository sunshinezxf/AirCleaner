package dist.purify.air.service;

import dist.purify.air.model.authc.Admin;
import dist.purify.air.model.authc.Customer;
import dist.purify.air.utils.ResultData;

import java.util.Map;

/**
 * Created by sunshine on 2016/10/14.
 */
public interface UserService {
    ResultData createUser(Admin admin);

    ResultData createUser(Customer customer);

    ResultData fetchUser(Map<String, Object> condition);
}
