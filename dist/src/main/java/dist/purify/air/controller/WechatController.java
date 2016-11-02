package dist.purify.air.controller;

import com.thoughtworks.xstream.XStream;
import dist.purify.air.model.wechat.InMessage;
import dist.purify.air.utils.Encryption;
import dist.purify.air.utils.PlatformConfig;
import dist.purify.air.utils.WechatUtil;
import dist.purify.air.utils.XStreamFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by sunshine on 5/24/16.
 */
@RestController
public class WechatController {
    private Logger logger = LoggerFactory.getLogger(WechatController.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/wechat")
    public String check(HttpServletRequest request) {
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");//
        ArrayList params = new ArrayList();
        params.add(PlatformConfig.instance().getValue("wechat_token"));
        params.add(timestamp);
        params.add(nonce);
        Collections.sort(params, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        String temp = params.get(0) + (String) params.get(1) + params.get(2);
        if (Encryption.SHA1(temp).equals(signature)) {
            return echostr;
        }
        return "";
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/wechat", produces = "text/xml;charset=utf-8")
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        try {
            ServletInputStream stream = request.getInputStream();
            String input = WechatUtil.inputStream2String(stream);
            XStream content = XStreamFactory.init(false);
            content.alias("xml", InMessage.class);
            final InMessage message = (InMessage) content.fromXML(input);
            String msgType = message.getMsgType();
            if (msgType.equals("event")) {
                result = "欢迎您关注空气堡在线";
            }
            if (msgType.equals("text")) {
                result = "欢迎您关注空气堡在线";
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }
}
