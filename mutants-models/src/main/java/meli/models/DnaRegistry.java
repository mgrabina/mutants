package meli.models;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class DnaRegistry {

    @Id
    private String code;
    private Boolean isMutant;

    public DnaRegistry(){} // For Hibernate

    public DnaRegistry(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Boolean isMutant(){
        return isMutant;
    }

    public void setIfMutant(Boolean isMutant){
        this.isMutant = isMutant;
    }
}
