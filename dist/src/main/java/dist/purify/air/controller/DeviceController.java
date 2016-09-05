package dist.purify.air.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by sunshine on 8/30/16.
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    private Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/{serial}/purchase")
    public ModelAndView detail(@PathVariable("serial") String serial) {
        ModelAndView view = new ModelAndView();

        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{serial}/bind")
    public ModelAndView bind(@PathVariable("serial") String serial) {
        ModelAndView view = new ModelAndView();

        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{serial}/bind")
    public ModelAndView bind() {
        ModelAndView view = new ModelAndView();

        return view;
    }
}
