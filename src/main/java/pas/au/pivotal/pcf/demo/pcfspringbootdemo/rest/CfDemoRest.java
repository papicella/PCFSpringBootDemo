package pas.au.pivotal.pcf.demo.pcfspringbootdemo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.Utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping ("/api")
public class CfDemoRest
{
    private static final Logger logger = LoggerFactory.getLogger(CfDemoRest.class);

    @RequestMapping(value = "/vcap_application", method = RequestMethod.GET)
    public Map getVcapApplicationMap() throws Exception {
        logger.info("REST request to get VCAP_APPLICATION");
        return Utils.getVcapApplicationMap();
    }

    @RequestMapping(value = "/vcap_services", method = RequestMethod.GET)
    public Map getVcapServicesMap() throws Exception {
        logger.info("REST request to get VCAP_SERVICES");
        return Utils.getVcapServicesMap();
    }

    @RequestMapping(value = "/vcap_services_parsed", method = RequestMethod.GET)
    public Map getVcapServicesMapParsed() throws Exception {
        logger.info("REST request to get VCAP_SERVICES Parsed");
        Map<String, ?> services = Utils.getVcapServicesMap();
        services = Utils.parseServices(services);

        return services;
    }

    @RequestMapping(value = "/killMeRestCall", method = RequestMethod.GET)
    public void killMeRestCall() throws Exception
    {
        logger.warn("*** The system is shutting down. ***");
        Runnable killTask = () -> {
            try {
                String name = Thread.currentThread().getName();
                logger.warn("killing shortly " + name);
                TimeUnit.SECONDS.sleep(1);
                logger.warn("killed " + name);
                System.exit(0);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(killTask).start();
    }
}
