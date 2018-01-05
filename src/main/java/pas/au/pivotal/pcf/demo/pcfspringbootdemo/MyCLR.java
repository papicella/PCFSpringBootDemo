package pas.au.pivotal.pcf.demo.pcfspringbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.repository.EmployeeRepository;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.repository.InstanceRepository;

@Component
public class MyCLR implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private InstanceRepository instanceRepository;

    @Override
    public void run(String... strings) throws Exception {
        instanceRepository.findAll().forEach(System.out::println);
    }
}
