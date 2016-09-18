package dist.purify.air.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by sunshine on 16/9/12.
 */
public class WechatUtil {
    private static Logger logger = LoggerFactory.getLogger(WechatUtil.class);

    public static String queryAccessToken() {
        PlatformConfig config = PlatformConfig.instance();
        String result = "";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + config.getValue("wechat_appid") + "&secret=" + config.getValue("wechat_secret");
        try {
            URL address = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) address.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            connection.connect();
            InputStream is = connection.getInputStream();
            int size = is.available();
            byte[] bytes = new byte[size];
            is.read(bytes);
            String message = new String(bytes, "UTF-8");
            JSONObject object = JSON.parseObject(message);
            result = object.getString("access_token");
            PlatformConfig.setJsapiTicket(queryJsApiTicket(result));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static String queryOauthOpenId(String code) {
        String result = "";
        PlatformConfig config = PlatformConfig.instance();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + config.getValue("wechat_appid") + "&secret=" + config.getValue("wechat_secret") + "&code=" + code + "&grant_type=authorization_code";
        try {
            URL address = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) address.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            connection.connect();
            InputStream is = connection.getInputStream();
            int size = is.available();
            byte[] bytes = new byte[size];
            is.read(bytes);
            String message = new String(bytes, "UTF-8");
            JSONObject object = JSON.parseObject(message);
            result = object.getString("openid");
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            return result;
        }
    }

    public static String queryShortURL(String previous) {
        PlatformConfig config = PlatformConfig.instance();
        String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=" + PlatformConfig.getAccessToken();
        try {
            JSONObject param = new JSONObject();
            param.put("action", "long2short");
            param.put("long_url", url);
            String result = HttpUtil.post(url, "UTF-8", param);
            JSONObject response = JSON.parseObject(result);
            if (response.getIntValue("errcode") == 0) {
                return response.getString("short_url");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static String createOauthURL(String url) {
        StringBuffer sb = new StringBuffer();
        try {
            PlatformConfig config = PlatformConfig.instance();
            sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
            sb.append("appid=").append(config.getValue("wechat_appid"));
            sb.append("&redirect_url=").append(URLEncoder.encode(url, "UTF-8"));
            sb.append("&response_type=code");
            sb.append("&scope=snsapi_base");
            sb.append("&state=view");
            sb.append("#wechat_redirect");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return sb.toString();
    }

    private static String queryJsApiTicket(String token) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
        String result = "";
        try {
            URL address = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) address.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            connection.connect();
            InputStream is = connection.getInputStream();
            int size = is.available();
            byte[] bytes = new byte[size];
            is.read(bytes);
            String message = new String(bytes, "UTF-8");
            JSONObject object = JSON.parseObject(message);
            result = object.getString("ticket");
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            return result;
        }
    }

    public static final String inputStream2String(InputStream in) throws IOException {
        if (in == null) {
            return "";
        } else {
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];

            int n;
            while ((n = in.read(b)) != -1) {
                out.append(new String(b, 0, n, "UTF-8"));
            }
            return out.toString();
        }
    }
}
