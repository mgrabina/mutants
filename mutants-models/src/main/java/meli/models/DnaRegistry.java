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

    @Column(name = "sequence_length")
    private int length;

    public DnaRegistry(){} // For Hibernate

    public DnaRegistry(String[] code, Boolean isMutant, int length){
        this.isMutant = isMutant;
        this.code = String.join("", code);
        this.length = length;
    }

    public String getCode() {
        return code;
    }

    public Boolean getMutant() {
        return isMutant;
    }

    public int getLength() {
        return length;
    }
}
