package pas.au.pivotal.pcf.demo.pcfspringbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.domain.Instance;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.repository.InstanceRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PcfspringbootdemoApplication {

	@Autowired
	private InstanceRepository instanceRepository;

	public static void main(String[] args) {
		SpringApplication.run(PcfspringbootdemoApplication.class, args);
	}

	@PostConstruct
	public void init ()
	{
		String appGUID = "N/A";
		String instanceIndex = "N/A";
		String applicationName = "N/A";

		try
		{
			appGUID = Utils.getVcapApplicationMap().getOrDefault("application_id", "N/A").toString();
			instanceIndex = Utils.getVcapApplicationMap().getOrDefault("instance_index", "N/A").toString();
			applicationName = Utils.getVcapApplicationMap().getOrDefault("application_name", "N/A").toString();
		}
		catch (Exception ex) {
		}

		Instance instance = new Instance();
		instance.setAppguid(appGUID);
		instance.setAppindex(instanceIndex);
		instance.setAppname(applicationName);

		instanceRepository.save(instance);
	}
}
