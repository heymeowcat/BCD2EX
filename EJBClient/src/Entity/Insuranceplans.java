/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "insuranceplans")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Insuranceplans.findAll", query = "SELECT i FROM Insuranceplans i")
    , @NamedQuery(name = "Insuranceplans.findByIdInsurancePlans", query = "SELECT i FROM Insuranceplans i WHERE i.idInsurancePlans = :idInsurancePlans")
    , @NamedQuery(name = "Insuranceplans.findByDetails", query = "SELECT i FROM Insuranceplans i WHERE i.details = :details")})
public class Insuranceplans implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idInsurancePlans")
    private Integer idInsurancePlans;
    @Size(max = 45)
    @Column(name = "details")
    private String details;
    @JoinColumn(name = "employeeid", referencedColumnName = "idEmployees")
    @ManyToOne(optional = false)
    private Employees employeeid;

    public Insuranceplans() {
    }

    public Insuranceplans(Integer idInsurancePlans) {
        this.idInsurancePlans = idInsurancePlans;
    }

    public Integer getIdInsurancePlans() {
        return idInsurancePlans;
    }

    public void setIdInsurancePlans(Integer idInsurancePlans) {
        this.idInsurancePlans = idInsurancePlans;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Employees getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Employees employeeid) {
        this.employeeid = employeeid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInsurancePlans != null ? idInsurancePlans.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Insuranceplans)) {
            return false;
        }
        Insuranceplans other = (Insuranceplans) object;
        if ((this.idInsurancePlans == null && other.idInsurancePlans != null) || (this.idInsurancePlans != null && !this.idInsurancePlans.equals(other.idInsurancePlans))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Insuranceplans[ idInsurancePlans=" + idInsurancePlans + " ]";
    }
    
}
