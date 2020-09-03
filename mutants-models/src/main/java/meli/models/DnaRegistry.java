package meli.models;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dnaregistry")
public class DnaRegistry {

    @Id
    @Column(length = 10485760, name = "code")  //Allows up to N=3238
    private String code;

    @Column(name = "ismutant")
    private Boolean isMutant;

    public DnaRegistry(){} // For Hibernate

    public DnaRegistry(String[] code, Boolean isMutant){
        this.isMutant = isMutant;
        this.code = String.join("", code);
    }
}
