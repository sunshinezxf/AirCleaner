package dist.purify.air.utils;

import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sunshine on 8/30/16.
 */
public class PlatformConfig {
    private static String accessToken;

    private static String jsapiTicket;

    private Properties props = new Properties();

    private PlatformConfig(String filename) {
        InputStream input = PlatformConfig.class.getClassLoader().getResourceAsStream(filename);
        try {
            props.load(input);
        } catch (Exception e) {

        }
    }

    public String getValue(String key) {
        return props.getProperty(key);
    }

    private static class InnerConfig {
        private static PlatformConfig instance = new PlatformConfig("aircleaner.properties");
    }

    public static PlatformConfig instance() {
        return InnerConfig.instance;
    }

    public static String getAccessToken() {
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = WechatUtil.queryAccessToken();
        }
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        PlatformConfig.accessToken = accessToken;
    }

    public static String getJsapiTicket() {
        return jsapiTicket;
    }

    public static void setJsapiTicket(String jsapiTicket) {
        PlatformConfig.jsapiTicket = jsapiTicket;
    }
}
