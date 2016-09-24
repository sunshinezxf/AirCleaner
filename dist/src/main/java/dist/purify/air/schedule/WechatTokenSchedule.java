package dist.purify.air.schedule;

import dist.purify.air.utils.PlatformConfig;
import dist.purify.air.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunshine on 16/9/23.
 */
public class WechatTokenSchedule {
    private Logger logger = LoggerFactory.getLogger(WechatTokenSchedule.class);

    public void schedule() {
        String token = WechatUtil.queryAccessToken();
        PlatformConfig.setAccessToken(token);
        logger.info("此次获取到的新的token的值为:" + token);
    }

}
