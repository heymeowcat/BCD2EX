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
@Table(name = "leavestatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Leavestatus.findAll", query = "SELECT l FROM Leavestatus l")
    , @NamedQuery(name = "Leavestatus.findByIdLeaveStatus", query = "SELECT l FROM Leavestatus l WHERE l.idLeaveStatus = :idLeaveStatus")
    , @NamedQuery(name = "Leavestatus.findByStasusName", query = "SELECT l FROM Leavestatus l WHERE l.stasusName = :stasusName")})
public class Leavestatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLeaveStatus")
    private Integer idLeaveStatus;
    @Size(max = 100)
    @Column(name = "stasusName")
    private String stasusName;
    @JoinColumn(name = "organizationId", referencedColumnName = "idOrganization")
    @ManyToOne(optional = false)
    private Organization organizationId;

    public Leavestatus() {
    }

    public Leavestatus(Integer idLeaveStatus) {
        this.idLeaveStatus = idLeaveStatus;
    }

    public Integer getIdLeaveStatus() {
        return idLeaveStatus;
    }

    public void setIdLeaveStatus(Integer idLeaveStatus) {
        this.idLeaveStatus = idLeaveStatus;
    }

    public String getStasusName() {
        return stasusName;
    }

    public void setStasusName(String stasusName) {
        this.stasusName = stasusName;
    }

    public Organization getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Organization organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLeaveStatus != null ? idLeaveStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Leavestatus)) {
            return false;
        }
        Leavestatus other = (Leavestatus) object;
        if ((this.idLeaveStatus == null && other.idLeaveStatus != null) || (this.idLeaveStatus != null && !this.idLeaveStatus.equals(other.idLeaveStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Leavestatus[ idLeaveStatus=" + idLeaveStatus + " ]";
    }
    
}
