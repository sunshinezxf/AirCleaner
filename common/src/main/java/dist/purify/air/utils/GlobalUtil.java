package dist.purify.air.utils;

import java.io.File;

/**
 * Created by sunshine on 16/9/11.
 */
public class GlobalUtil {
    public static String retrivePath() {
        String path = GlobalUtil.class.getResource(File.separator).getPath();
        String temp = new StringBuffer(File.separator).append("WEB-INF").append(File.separator).append("classes").append(File.separator).toString();
        int index = path.lastIndexOf(temp);
        return path.substring(0, index);
    }
}
