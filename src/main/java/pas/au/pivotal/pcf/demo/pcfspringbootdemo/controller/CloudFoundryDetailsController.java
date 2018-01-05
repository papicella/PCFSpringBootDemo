package pas.au.pivotal.pcf.demo.pcfspringbootdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.Utils;

import java.util.Map;

@Controller
public class CloudFoundryDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(CloudFoundryDetailsController.class);

    @RequestMapping("/cfdetails")
    public String showCfDetails (Model model) throws Exception
    {
        Map appMap = Utils.getVcapApplicationMap();
        Map servicesMap = Utils.getVcapServicesMap();

        logger.info("Services : " + servicesMap);
        model.addAttribute("appMap", appMap);
        model.addAttribute("servicesMap", servicesMap);
        model.addAttribute("propertyMap", Utils.jvmPropertyMap());

        logger.info("Returning cfdetails.html page");

        return "cfdetails";
    }
}
