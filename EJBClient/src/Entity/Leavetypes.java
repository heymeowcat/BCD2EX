/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "leavetypes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Leavetypes.findAll", query = "SELECT l FROM Leavetypes l")
    , @NamedQuery(name = "Leavetypes.findByIdLeaveTypes", query = "SELECT l FROM Leavetypes l WHERE l.idLeaveTypes = :idLeaveTypes")
    , @NamedQuery(name = "Leavetypes.findByLeaveTypeName", query = "SELECT l FROM Leavetypes l WHERE l.leaveTypeName = :leaveTypeName")
    , @NamedQuery(name = "Leavetypes.findByStatus", query = "SELECT l FROM Leavetypes l WHERE l.status = :status")})
public class Leavetypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLeaveTypes")
    private Integer idLeaveTypes;
    @Size(max = 100)
    @Column(name = "LeaveTypeName")
    private String leaveTypeName;
    @Size(max = 100)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "organizationId", referencedColumnName = "idOrganization")
    @ManyToOne(optional = false)
    private Organization organizationId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leavetypeId")
    private List<Leave> leaveList;

    public Leavetypes() {
    }

    public Leavetypes(Integer idLeaveTypes) {
        this.idLeaveTypes = idLeaveTypes;
    }

    public Integer getIdLeaveTypes() {
        return idLeaveTypes;
    }

    public void setIdLeaveTypes(Integer idLeaveTypes) {
        this.idLeaveTypes = idLeaveTypes;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Organization getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Organization organizationId) {
        this.organizationId = organizationId;
    }

    @XmlTransient
    public List<Leave> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<Leave> leaveList) {
        this.leaveList = leaveList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLeaveTypes != null ? idLeaveTypes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Leavetypes)) {
            return false;
        }
        Leavetypes other = (Leavetypes) object;
        if ((this.idLeaveTypes == null && other.idLeaveTypes != null) || (this.idLeaveTypes != null && !this.idLeaveTypes.equals(other.idLeaveTypes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Leavetypes[ idLeaveTypes=" + idLeaveTypes + " ]";
    }
    
}
