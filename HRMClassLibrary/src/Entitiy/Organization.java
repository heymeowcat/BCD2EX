/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "organization")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Organization.findAll", query = "SELECT o FROM Organization o")
    , @NamedQuery(name = "Organization.findByIdOrganization", query = "SELECT o FROM Organization o WHERE o.idOrganization = :idOrganization")
    , @NamedQuery(name = "Organization.findByOrganizationName", query = "SELECT o FROM Organization o WHERE o.organizationName = :organizationName")
    , @NamedQuery(name = "Organization.findByStatus", query = "SELECT o FROM Organization o WHERE o.status = :status")})
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrganization")
    private Integer idOrganization;
    @Column(name = "organizationName")
    private String organizationName;
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationId")
    private List<Leavetypes> leavetypesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationId")
    private List<Projects> projectsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationId")
    private List<Leaveactions> leaveactionsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
    private List<Structure> structureList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationId")
    private List<Leavestatus> leavestatusList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationId")
    private List<Locations> locationsList;

    public Organization() {
    }

    public Organization(Integer idOrganization) {
        this.idOrganization = idOrganization;
    }

    public Integer getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(Integer idOrganization) {
        this.idOrganization = idOrganization;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<Leavetypes> getLeavetypesList() {
        return leavetypesList;
    }

    public void setLeavetypesList(List<Leavetypes> leavetypesList) {
        this.leavetypesList = leavetypesList;
    }

    @XmlTransient
    public List<Projects> getProjectsList() {
        return projectsList;
    }

    public void setProjectsList(List<Projects> projectsList) {
        this.projectsList = projectsList;
    }

    @XmlTransient
    public List<Leaveactions> getLeaveactionsList() {
        return leaveactionsList;
    }

    public void setLeaveactionsList(List<Leaveactions> leaveactionsList) {
        this.leaveactionsList = leaveactionsList;
    }

    @XmlTransient
    public List<Structure> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<Structure> structureList) {
        this.structureList = structureList;
    }

    @XmlTransient
    public List<Leavestatus> getLeavestatusList() {
        return leavestatusList;
    }

    public void setLeavestatusList(List<Leavestatus> leavestatusList) {
        this.leavestatusList = leavestatusList;
    }

    @XmlTransient
    public List<Locations> getLocationsList() {
        return locationsList;
    }

    public void setLocationsList(List<Locations> locationsList) {
        this.locationsList = locationsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrganization != null ? idOrganization.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organization)) {
            return false;
        }
        Organization other = (Organization) object;
        if ((this.idOrganization == null && other.idOrganization != null) || (this.idOrganization != null && !this.idOrganization.equals(other.idOrganization))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Organization[ idOrganization=" + idOrganization + " ]";
    }
    
}
