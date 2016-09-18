package dist.purify.air.controller;

import dist.purify.air.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ModelAndView login(@Valid LoginForm form, BindingResult result) {
        ModelAndView view = new ModelAndView();
        if (result.hasErrors()) {
            view.setViewName("/backend/login");
            return view;
        }
        String username = form.getUsername();
        String password = form.getPassword();
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            view.setViewName("redirect:/dashboard");
            return view;
        }
        view.setViewName("redirect:/login");
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/backend/dashboard");
        return view;
    }
}
