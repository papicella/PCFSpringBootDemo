package pas.au.pivotal.pcf.demo.pcfspringbootdemo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.domain.Employee;

@RepositoryRestResource(collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @RestResource(path = "name", rel = "name")
    Page<Employee> findByNameIgnoreCase(@Param("q") String name, Pageable pageable);

}
