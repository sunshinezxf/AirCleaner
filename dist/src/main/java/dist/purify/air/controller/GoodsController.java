package dist.purify.air.controller;

import dist.purify.air.form.GoodsForm;
import dist.purify.air.service.GoodsService;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import model.goods.Goods4Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
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

        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{goodsId}/register")
    public ModelAndView register(@PathVariable("goodsId") String goodsId, String client) {
        ModelAndView view = new ModelAndView();
        //检查goodsId是否存在

        //检查client是否村子啊
        view.setViewName("/client/goods/register");
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{goodsId}/purchase")
    public ModelAndView purchase(@PathVariable("goodsId") String goodsId, String client, String code, String state) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> condition = new HashMap<>();
        condition.put("goodsId", goodsId);
        view.setViewName("/client/goods/purchase");
        return view;
    }
}
