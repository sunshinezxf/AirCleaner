import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sunshine on 2016/10/16.
 */
public class MenuConfig {
    private final static String APP_ID = "wxf7a601d5b5b6a11e";
    private final static String APP_SECRET = "299b5c91196d5ba7337d167aad772c58";

    public static String queryAccessToken(String appId, String secret) {
        String result = "";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + secret;
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static String createMenu() {
        JSONObject menu = new JSONObject();
        //存放订阅号菜单按钮的数组
        JSONArray buttons = new JSONArray();

        JSONArray airburg_operation = new JSONArray();

        JSONObject product = new JSONObject();
        product.put("name", "空气堡");
        product.put("type", "view");
        try {
            product.put("url", "http://g.eqxiu.com/s/4qnnMpnS");
        } catch (Exception e) {
            e.printStackTrace();
        }
        airburg_operation.add(product);

        JSONObject effect = new JSONObject();
        effect.put("name", "净化效果");
        effect.put("type", "view");
        try {
            effect.put("url", "http://mp.weixin.qq.com/s?__biz=MzA5MjIzMzE4Mg==&mid=509670072&idx=1&sn=2bddcbf597b6132feae31e9118a8a0ef");
        } catch (Exception e) {
            e.printStackTrace();
        }
        airburg_operation.add(effect);

        JSONObject cases = new JSONObject();
        cases.put("name", "用户案例");
        cases.put("type", "view");
        try {
            cases.put("url", "http://u.liveapp.cn/1044893");
        } catch (Exception e) {
            e.printStackTrace();
        }
        airburg_operation.add(cases);

        JSONObject comment = new JSONObject();
        comment.put("name", "用户评价");
        comment.put("type", "view");
        try {
            comment.put("url", "http://mp.weixin.qq.com/s?__biz=MzA5MjIzMzE4Mg==&mid=509670073&idx=1&sn=f51b6905a2a21231de9a0f762ef7805a");
        } catch (Exception e) {
            e.printStackTrace();
        }
        airburg_operation.add(comment);

        JSONObject airburg = new JSONObject();
        airburg.put("name", "了解");
        airburg.put("sub_button", airburg_operation);
        buttons.add(airburg);

//        JSONArray mine = new JSONArray();
//
//        JSONObject purchase_query = new JSONObject();
//        purchase_query.put("name", "订单查询");
//        purchase_query.put("type", "view");
//        try {
//            purchase_query.put("url", "http://www.qingair.net/");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mine.add(purchase_query);

        JSONObject order = new JSONObject();
//        order.put("name", "我的");
//        order.put("sub_button", mine);
        order.put("name", "订单查询");
        order.put("type", "view");
        try {
            order.put("url", "http://www.qingair.net/order/search");
        } catch (Exception e) {
            e.printStackTrace();
        }
        buttons.add(order);

        JSONObject market = new JSONObject();
//        market.put("name", "积分商城");
//        market.put("type", "view");
//        try {
//            market.put("url", "http://www.qingair.net");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        market.put("name", "产品购买");
        market.put("type", "view");
        try {
            market.put("url", "https://h5.koudaitong.com/v2/goods/3ez3rz5oxuipl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        buttons.add(market);

        menu.put("button", buttons);

        String token = queryAccessToken(APP_ID, APP_SECRET);
        String link = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token;

        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            connection.connect();
            OutputStream os = connection.getOutputStream();
            os.write(menu.toString().getBytes());
            os.flush();
            os.close();

            InputStream is = connection.getInputStream();
            int size = is.available();
            byte[] bytes = new byte[size];
            is.read(bytes);
            String message = new String(bytes, "UTF-8");
            return "返回信息" + message;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "创建菜单失败";
    }

    public static String deleteMenu() {
        String token = queryAccessToken(APP_ID, APP_SECRET);
        String link = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + token;
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            connection.connect();
            OutputStream os = connection.getOutputStream();
            os.flush();
            os.close();
            InputStream is = connection.getInputStream();
            int size = is.available();
            byte[] bytes = new byte[size];
            is.read(bytes);
            String message = new String(bytes, "UTF-8");
            return "返回信息:" + message;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "删除菜单失败";
    }

    public static void main(String[] args) {
        String deleteMsg = MenuConfig.deleteMenu();
        System.out.println("删除菜单操作: " + deleteMsg);
        String createMsg = MenuConfig.createMenu();
        System.out.println("创建菜单操作: " + createMsg);
    }
}
