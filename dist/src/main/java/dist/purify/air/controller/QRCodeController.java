package dist.purify.air.controller;

import dist.purify.air.form.QRConfigForm;
import dist.purify.air.service.GoodsService;
import dist.purify.air.service.QRCodeService;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import dist.purify.air.utils.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunshine on 8/28/16.
 */
@RestController
@RequestMapping("/qrcode")
public class QRCodeController {
    private Logger logger = LoggerFactory.getLogger(QRCodeController.class);

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(method = RequestMethod.GET, value = "/create")
    public ModelAndView create() {
        ModelAndView view = new ModelAndView();
        Map<String, Object> condition = new HashMap<>();
        ResultData response = goodsService.fetchGoods(condition);
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            view.addObject("goods", response.getData());
        }
        view.setViewName("/backend/qrcode/create");
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ModelAndView create(@Valid QRConfigForm form, BindingResult result) {
        ModelAndView view = new ModelAndView();
        if (result.hasErrors()) {
            view.setViewName("redirect:/qrcode/create");
            return view;
        }
        ResultData response = qrCodeService.create(form.getPrefix(), Integer.parseInt(form.getQuantity()), form.getGoodsId());
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            view.setViewName("redirect:/qrcode/create");
            return view;
        }
        response = ZipUtil.compress((List) response.getData());
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            view.setViewName("redirect:/qrcode/create");
            return view;
        }
        String file = (String) response.getData();
        logger.debug("file: " + file);
        view.addObject("file", file);
        view.setViewName("/backend/qrcode/download");
        return view;
    }
}
