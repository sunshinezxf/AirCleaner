package dist.purify.air.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by sunshine on 16/9/21.
 */
public class JSONUtil {
    public static JSONObject getParams(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = request.getReader();
            char[] buff = new char[1024];
            int length;
            while ((length = reader.read(buff)) != -1) {
                sb.append(buff, 0, length);
            }
            result = JSONObject.parseObject(sb.toString());
        } catch (IOException e) {
            return result;
        }
        return result;
    }
}
