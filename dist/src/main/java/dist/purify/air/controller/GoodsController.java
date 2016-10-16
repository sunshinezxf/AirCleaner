package dist.purify.air.controller;

import dist.purify.air.form.ConsumerOrderForm;
import dist.purify.air.form.GoodsForm;
import dist.purify.air.model.goods.Goods4Customer;
import dist.purify.air.model.order.ConsumerOrder;
import dist.purify.air.pagination.DataTablePage;
import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.service.GoodsService;
import dist.purify.air.service.OrderService;
import dist.purify.air.utils.*;
import dist.purify.air.vo.prompt.Prompt;
import dist.purify.air.vo.prompt.PromptCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunshine on 16/9/13.
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    private Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    /**
     * 跳转添加商品页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/create")
    public ModelAndView create() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/backend/goods/create");
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ModelAndView create(@Valid GoodsForm form, BindingResult result) {
        ModelAndView view = new ModelAndView();
        if (result.hasErrors()) {
            view.setViewName("/backend/goods/create");
            return view;
        }
        Goods4Customer goods = new Goods4Customer(form.getGoodsName(), Integer.parseInt(form.getPrimePrice()), Integer.parseInt(form.getSharePrice()), form.getDescription());
        ResultData response = goodsService.createGoods(goods);
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {

        }
        view.setViewName("/backend/goods/create");
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/overview")
    public ModelAndView overview() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/backend/goods/overview");
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/overview")
    public DataTablePage<Goods4Customer> overview(DataTableParam param) {
        DataTablePage<Goods4Customer> result = new DataTablePage<>();
        if (StringUtils.isEmpty(param)) {
            return result;
        }
        Map<String, Object> condition = new HashMap<>();
        ResultData response = goodsService.fetchGoods(condition, param);
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            logger.debug("获取商品列表为空或者发生错误");
            return result;
        }
        result = (DataTablePage) response.getData();
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register/{goodsId}")
    public ModelAndView register(@PathVariable("goodsId") String goodsId, String client) {
        ModelAndView view = new ModelAndView();
        //检查goodsId是否存在
        Map<String, Object> condition = new HashMap<>();
        condition.put("goodsId", goodsId);
        ResultData response = goodsService.fetchGoods(condition);
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            //返回错误信息页面
        }
        //检查client是否存在
        view.setViewName("/client/goods/register");
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/purchase/{goodsId}")
    public ModelAndView purchase(@PathVariable("goodsId") String goodsId, String client, String code, String state, HttpServletRequest request) throws IOException {
        ModelAndView view = new ModelAndView();
        Map<String, Object> condition = new HashMap<>();
        condition.put("goodsId", goodsId);
        condition.put("blockFlag", false);
        ResultData response = goodsService.fetchGoods(condition);
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            //该商品不存在或目前已经下架
            logger.error("商品不存在或者读取异常");
            Prompt prompt = new Prompt(PromptCode.ERROR, "您所要访问的商品不存在");
            view.addObject("prompt", prompt);
            view.setViewName("/client/payment/inform");
            return view;
        }
        //获取商品的标识
        view.addObject("goodsId", goodsId);
        PlatformConfig instance = PlatformConfig.instance();
        //获取微信中的URL链接
        StringBuffer url = new StringBuffer("http://").append(instance.getValue("server_url")).append("/goods/purchase/").append(goodsId).append("?client=").append(client);
        String oauthURL = WechatUtil.createOauthURL(url.toString());
        view.addObject("oauthURL", oauthURL);
        Goods4Customer goods = ((List<Goods4Customer>) response.getData()).get(0);
        view.addObject("goods", goods);
        if (!StringUtils.isEmpty(client)) {
            view.addObject("client", client);
        }
        if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(state)) {
            String openId = WechatUtil.queryOauthOpenId(code);
            HttpSession session = request.getSession();
            session.setAttribute("openId", openId);
            view.setViewName("redirect:/goods" + "/purchase/" + goodsId);
            return view;
        }
        WechatConfig.oauthWechat(view, "/goods/purchase/" + goodsId + ((StringUtils.isEmpty(client)) ? "" : "?client=" + client));
        view.setViewName("/client/goods/detail");
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/purchase/{goodsId}")
    public ResultData purchase(@PathVariable("goodsId") String goodsId, ConsumerOrderForm form) {
        ResultData result = new ResultData();
        Map<String, Object> condition = new HashMap<>();
        condition.put("goodsId", goodsId);
        condition.put("blockFlag", false);
        ResultData response = goodsService.fetchGoods(condition);
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            result.setResponseCode(ResponseCode.RESPONSE_ERROR);
            return result;
        }
        Goods4Customer goods = ((List<Goods4Customer>) response.getData()).get(0);
        boolean share = (StringUtils.isEmpty(form.getClientId())) ? false : true;
        ConsumerOrder order = new ConsumerOrder(form.getClientId(), form.getWechat(), form.getConsumerName(), form.getConsumerPhone(), form.getConsumerAddress(), goods, share ? goods.getSharePrice() : goods.getPrimePrice(), Integer.parseInt(form.getGoodsQuantity()));
        response = orderService.createConsumerOrder(order);
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            result.setData(response.getData());
        } else {
            result.setResponseCode(ResponseCode.RESPONSE_ERROR);
            result.setDescription(response.getDescription());
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/purchase/pay.htm")
    public ModelAndView pay() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/client/goods/pay");
        return view;
    }
}
