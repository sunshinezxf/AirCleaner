package dist.purify.air.controller;

import dist.purify.air.form.QRConfigForm;
import dist.purify.air.model.qrcode.QRCode;
import dist.purify.air.pagination.DataTablePage;
import dist.purify.air.pagination.DataTableParam;
import dist.purify.air.service.GoodsService;
import dist.purify.air.service.QRCodeService;
import dist.purify.air.utils.ResponseCode;
import dist.purify.air.utils.ResultData;
import dist.purify.air.utils.ZipUtil;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
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
        condition.clear();
        condition.put("blockFlag", false);
        response = qrCodeService.fetchQRCodeKind(condition);
        if (response.getResponseCode() == ResponseCode.RESPONSE_OK) {
            view.addObject("kinds", response.getData());
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
        ResultData response = qrCodeService.createQRCode(form.getPrefix(), Integer.parseInt(form.getQuantity()), form.getGoodsId());
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
        view.addObject("file", file);
        view.setViewName("/backend/qrcode/download");
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{codeId}/download")
    public String download(@PathVariable("codeId") String codeId, HttpServletResponse response) {
        if (StringUtils.isEmpty(codeId)) {
            return "";
        }
        Map<String, Object> condition = new HashMap<>();
        condition.put("codeId", codeId);
        ResultData r = qrCodeService.fetchQRCode(condition);
        if (r.getResponseCode() != ResponseCode.RESPONSE_OK) {
            logger.error("获取code_id为: " + codeId + "的二维码不存在或者发生异常");
            return "";
        }
        QRCode code = ((List<QRCode>) r.getData()).get(0);
        r = qrCodeService.fetchQRCodeFile(code);
        if (r.getResponseCode() == ResponseCode.RESPONSE_OK) {
            try {
                ImageIO.write((BufferedImage) r.getData(), "image/png", response.getOutputStream());
                response.flushBuffer();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/overview")
    public ModelAndView overview() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/backend/qrcode/overview");
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/overview")
    public DataTablePage<QRCode> overview(DataTableParam param) {
        DataTablePage<QRCode> result = new DataTablePage<>(param);
        if (StringUtils.isEmpty(param)) {
            return result;
        }
        Map<String, Object> condition = new HashMap<>();
        ResultData response = qrCodeService.fetchQRCode(condition, param);
        if (response.getResponseCode() != ResponseCode.RESPONSE_OK) {
            logger.debug("获取二维码列表为空或发生错误");
        }
        result = (DataTablePage) response.getData();
        return result;
    }
}
