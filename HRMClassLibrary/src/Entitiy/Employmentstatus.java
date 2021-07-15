/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "employmentstatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employmentstatus.findAll", query = "SELECT e FROM Employmentstatus e")
    , @NamedQuery(name = "Employmentstatus.findByIdEmploymentStatus", query = "SELECT e FROM Employmentstatus e WHERE e.idEmploymentStatus = :idEmploymentStatus")
    , @NamedQuery(name = "Employmentstatus.findByStatus", query = "SELECT e FROM Employmentstatus e WHERE e.status = :status")})
public class Employmentstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEmploymentStatus")
    private Integer idEmploymentStatus;
    @Column(name = "status")
    private String status;

    public Employmentstatus() {
    }

    public Employmentstatus(Integer idEmploymentStatus) {
        this.idEmploymentStatus = idEmploymentStatus;
    }

    public Integer getIdEmploymentStatus() {
        return idEmploymentStatus;
    }

    public void setIdEmploymentStatus(Integer idEmploymentStatus) {
        this.idEmploymentStatus = idEmploymentStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmploymentStatus != null ? idEmploymentStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employmentstatus)) {
            return false;
        }
        Employmentstatus other = (Employmentstatus) object;
        if ((this.idEmploymentStatus == null && other.idEmploymentStatus != null) || (this.idEmploymentStatus != null && !this.idEmploymentStatus.equals(other.idEmploymentStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Employmentstatus[ idEmploymentStatus=" + idEmploymentStatus + " ]";
    }
    
}
