package dist.purify.air.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sunshine on 8/30/16.
 */
public class PlatformConfig {
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
}
