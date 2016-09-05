package dist.purify.air.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import dist.purify.air.service.QRCodeService;
import dist.purify.air.utils.ResultData;

/**
 * Created by sunshine on 8/28/16.
 */
@RestController
@RequestMapping("/qrcode")
public class QRCodeController {
    private Logger logger = LoggerFactory.getLogger(QRCodeController.class);

    @Autowired
    private QRCodeService qrCodeService;

    @RequestMapping(method = RequestMethod.GET, value = "/index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();

        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/batch")
    public ResultData batch() {
        ResultData result = new ResultData();

        return result;
    }
}
