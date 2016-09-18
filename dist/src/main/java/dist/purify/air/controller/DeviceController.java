package dist.purify.air.controller;

import dist.purify.air.service.QRCodeService;
import dist.purify.air.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunshine on 8/30/16.
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    private Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private QRCodeService qrCodeService;

    @RequestMapping(method = RequestMethod.GET, value = "/{serial}/purchase")
    public ModelAndView detail(@PathVariable("serial") String serial) {
        ModelAndView view = new ModelAndView();

        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{serial}/bind")
    public ModelAndView bind(@PathVariable("serial") String serial) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> condition = new HashMap<>();
        condition.put("value", serial);
        ResultData response;
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{serial}/bind")
    public ModelAndView bind() {
        ModelAndView view = new ModelAndView();

        return view;
    }
}
