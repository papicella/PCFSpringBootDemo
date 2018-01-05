package pas.au.pivotal.pcf.demo.pcfspringbootdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pas.au.pivotal.pcf.demo.pcfspringbootdemo.domain.Instance;

@RepositoryRestResource(collectionResourceRel = "instances", path = "instances")
public interface InstanceRepository extends JpaRepository <Instance, Long> {

    Instance findByAppindex (String index);
}
