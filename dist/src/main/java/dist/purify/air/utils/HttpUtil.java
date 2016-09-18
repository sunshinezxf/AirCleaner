package dist.purify.air.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    /**
     * 发送POST 请求
     *
     * @param url     请求地址
     * @param charset 编码格式
     * @param params  请求参数
     * @return 响应
     * @throws IOException
     */
    public static String post(String url, String charset, JSONObject params) throws Exception {
        OutputStreamWriter out = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/json");
            out = new OutputStreamWriter(conn.getOutputStream(), charset);
            out.write(params.toJSONString());
            out.flush();
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String temp;
            while ((temp = reader.readLine()) != null) {
                result.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return result.toString();
    }
}