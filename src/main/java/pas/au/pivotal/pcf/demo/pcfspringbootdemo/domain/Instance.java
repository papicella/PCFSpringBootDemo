package pas.au.pivotal.pcf.demo.pcfspringbootdemo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(exclude = "id")
public class Instance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String appguid;

    @Column(nullable = false)
    private String appindex;

    @Column(nullable = false)
    private String appname;

    public Instance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppguid() {
        return appguid;
    }

    public void setAppguid(String appguid) {
        this.appguid = appguid;
    }

    public String getAppindex() {
        return appindex;
    }

    public void setAppindex(String appindex) {
        this.appindex = appindex;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "id=" + id +
                ", appguid='" + appguid + '\'' +
                ", appindex='" + appindex + '\'' +
                ", appname='" + appname + '\'' +
                '}';
    }
}
