package pas.au.pivotal.pcf.demo.pcfspringbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.Utils;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.domain.Instance;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.repository.InstanceRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private RestTemplate restTemplate = new RestTemplate();

    private InstanceRepository instanceRepository;

    @Autowired
    public IndexController(InstanceRepository instanceRepository) {
        this.instanceRepository = instanceRepository;
    }

    @RequestMapping ("/")
    public String indexPage (Model model) throws Exception
    {
        Map appMap = Utils.getVcapApplicationMap();
        Map servicesMap = Utils.getVcapServicesMap();

        model.addAttribute("appMap", appMap);

        // get application URI
        List appURIList = (ArrayList) appMap.get("application_uris");

        String appURI = "Unknown";

        if (appURIList != null) {
            if (appURIList.size() > 0) {
                appURI = (String) appURIList.get(0);
            }
        }

        logger.info("appURI = " + appURI);

        model.addAttribute("appURI", appURI);
        model.addAttribute("instances", instanceRepository.findAll());

        logger.info("App Instances count = " + instanceRepository.count());
        logger.info("Returning index.html page");

        return "index";
    }

    @RequestMapping ("/killme")
    public String killInstance (Model model) throws Exception
    {
        model.addAttribute("killed", true);
        logger.warn("*** The system is shutting down. ***");
        Runnable killTask = () -> {
            try {
                String name = Thread.currentThread().getName();
                logger.warn("killing shortly " + name);
                TimeUnit.SECONDS.sleep(5);
                logger.warn("killed " + name);
                System.exit(0);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(killTask).start();

        model.addAttribute("appMap", Utils.getVcapApplicationMap());
        model.addAttribute("instances", instanceRepository.findAll());

        logger.info("App Instances count = " + instanceRepository.count());
        logger.info("Returning index.html page");

        return "index";
    }

    @RequestMapping ("/destroyInstance")
    public String killInstance (Model model, HttpServletRequest request) throws Exception
    {

        String url = "http://%s/api/killMeRestCall";
        String appIndex = request.getParameter("appIndex");
        String appGUID = request.getParameter("appGUID");
        String appURI = request.getParameter("appURI");

        String reqHeader = appGUID + ":" + appIndex;

        //TODO: fix this
        String formattedURL = String.format(url, appURI);

        // delete instance from REPO
        Instance instance = instanceRepository.findByAppindex(request.getParameter("appIndex"));

        // Make RestTemplate Call setting app index as Header
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CF-APP-INSTANCE", reqHeader);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(formattedURL, HttpMethod.GET, entity, String.class);

        logger.info("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());

        instanceRepository.delete(instance.getId());
        logger.info("Removed instance form instanceRepository with ID: " + instance.getId());

        model.addAttribute("appMap", Utils.getVcapApplicationMap());
        model.addAttribute("instances", instanceRepository.findAll());

        logger.info("App Instances count = " + instanceRepository.count());
        logger.info("Returning index.html page");

        return "index";
    }
}
