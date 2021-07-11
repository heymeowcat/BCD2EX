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
@Table(name = "leaveactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Leaveactions.findAll", query = "SELECT l FROM Leaveactions l")
    , @NamedQuery(name = "Leaveactions.findByIdLeaveActions", query = "SELECT l FROM Leaveactions l WHERE l.idLeaveActions = :idLeaveActions")
    , @NamedQuery(name = "Leaveactions.findByActionName", query = "SELECT l FROM Leaveactions l WHERE l.actionName = :actionName")})
public class Leaveactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLeaveActions")
    private Integer idLeaveActions;
    @Size(max = 100)
    @Column(name = "actionName")
    private String actionName;
    @JoinColumn(name = "organizationId", referencedColumnName = "idOrganization")
    @ManyToOne(optional = false)
    private Organization organizationId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leaveAction")
    private List<Leave> leaveList;

    public Leaveactions() {
    }

    public Leaveactions(Integer idLeaveActions) {
        this.idLeaveActions = idLeaveActions;
    }

    public Integer getIdLeaveActions() {
        return idLeaveActions;
    }

    public void setIdLeaveActions(Integer idLeaveActions) {
        this.idLeaveActions = idLeaveActions;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
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
        hash += (idLeaveActions != null ? idLeaveActions.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Leaveactions)) {
            return false;
        }
        Leaveactions other = (Leaveactions) object;
        if ((this.idLeaveActions == null && other.idLeaveActions != null) || (this.idLeaveActions != null && !this.idLeaveActions.equals(other.idLeaveActions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Leaveactions[ idLeaveActions=" + idLeaveActions + " ]";
    }
    
}
