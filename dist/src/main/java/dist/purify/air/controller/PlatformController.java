package dist.purify.air.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by sunshine on 8/27/16.
 */
@RestController
@RequestMapping("/")
public class PlatformController {
    private Logger logger = LoggerFactory.getLogger(PlatformController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/backend/index");
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public ModelAndView login() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/backend/login");
        return view;
    }

}
