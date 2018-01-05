package pas.au.pivotal.pcf.demo.pcfspringbootdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Utils
{
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    @SuppressWarnings("rawtypes")
    public static Map<String,Object> parseServices(Map<String, ?> services) {
        Map<String,Object> servicesMap  = Collections.synchronizedMap(new LinkedHashMap<String,Object>());
        for (Map.Entry<String,?> entry : services.entrySet()) {
            List list = (List)entry.getValue();

            for (Object object : list) {
                logger.debug("list: {}", object.getClass());
                Map map = (Map)object;
                //weird delimiter and UUID is to deal with multiple services of the same type
                servicesMap.put(entry.getKey() + "~~~" + UUID.randomUUID().toString(), map.get("name"));
            }
        }

        return servicesMap;

    }

    @SuppressWarnings("rawtypes")
    public static Map getVcapApplicationMap() throws Exception {
        return getEnvMap("VCAP_APPLICATION");
    }

    @SuppressWarnings("rawtypes")
    public static Map getVcapServicesMap() throws Exception {
        return getEnvMap("VCAP_SERVICES");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map getEnvMap(String vcap) throws Exception {
        String vcapEnv = System.getenv(vcap);
        ObjectMapper mapper = new ObjectMapper();

        if (vcapEnv != null) {
            Map<String, ?> vcapMap = mapper.readValue(vcapEnv, Map.class);
            return vcapMap;
        }

        logger.warn(vcap + " not defined, returning empty Map");
        return new HashMap<String, String>();
    }

    public static Map<String, String> jvmPropertyMap ()
    {
        Properties props = System.getProperties();
        Map<String, String> map = new HashMap<String, String>((Map) props);

        return map;
    }
}
