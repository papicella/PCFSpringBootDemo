package pas.au.pivotal.pcf.demo.pcfspringbootdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.Utils;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.domain.Employee;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.repository.EmployeeRepository;

import java.util.List;

@Controller
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping("/emps")
    public String showEmps (Model model) throws Exception
    {
        List<Employee> emps = employeeRepository.findAll();

        model.addAttribute("employees", emps);
        model.addAttribute("employeesCount", emps.size());
        model.addAttribute("appMap", Utils.getVcapApplicationMap());

        logger.info("Returning employees.html page");

        return "employees";
    }

    @RequestMapping("/addEmps")
    public String addEmps (Model model) throws Exception
    {
        employeeRepository.save(new Employee("pas", "CEO", new Long(100000), "No" ));
        employeeRepository.save(new Employee("lucia", "CIO", new Long(800000), "Yes" ));
        employeeRepository.save(new Employee("lucas", "CEO", new Long(500000), "Yes" ));
        employeeRepository.save(new Employee("siena", "CEO", new Long(500000), "Yes" ));

        employeeRepository.flush();

        List<Employee> emps = employeeRepository.findAll();

        logger.info("Employees " + emps);
        logger.info("Employees size = " + emps.size());

        model.addAttribute("employees", emps);
        model.addAttribute("employeesCount", emps.size());
        model.addAttribute("appMap", Utils.getVcapApplicationMap());

        logger.info("Returning employees.html page");

        return "employees";
    }

    @RequestMapping("/deleteEmps")
    public String deleteEmps (Model model) throws Exception
    {
        employeeRepository.deleteAll();

        List<Employee> emps = employeeRepository.findAll();

        model.addAttribute("employees", emps);
        model.addAttribute("employeesCount", emps.size());

        logger.info("Employees size = " + emps.size());

        model.addAttribute("appMap", Utils.getVcapApplicationMap());

        logger.info("Returning employees.html page");

        return "employees";
    }
}
