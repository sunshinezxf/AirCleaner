package dist.purify.air.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sunshine on 8/30/16.
 */
@RestController
@RequestMapping("/bill")
public class BillController {
    private Logger logger = LoggerFactory.getLogger(BillController.class);
}
