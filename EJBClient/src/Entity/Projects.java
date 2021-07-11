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
@Table(name = "projects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projects.findAll", query = "SELECT p FROM Projects p")
    , @NamedQuery(name = "Projects.findByIdProjects", query = "SELECT p FROM Projects p WHERE p.idProjects = :idProjects")
    , @NamedQuery(name = "Projects.findByCustomerName", query = "SELECT p FROM Projects p WHERE p.customerName = :customerName")
    , @NamedQuery(name = "Projects.findByProjectName", query = "SELECT p FROM Projects p WHERE p.projectName = :projectName")
    , @NamedQuery(name = "Projects.findByProjectDescription", query = "SELECT p FROM Projects p WHERE p.projectDescription = :projectDescription")})
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProjects")
    private Integer idProjects;
    @Size(max = 150)
    @Column(name = "customerName")
    private String customerName;
    @Size(max = 100)
    @Column(name = "projectName")
    private String projectName;
    @Size(max = 45)
    @Column(name = "projectDescription")
    private String projectDescription;
    @JoinColumn(name = "organizationId", referencedColumnName = "idOrganization")
    @ManyToOne(optional = false)
    private Organization organizationId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private List<Timesheet> timesheetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private List<Activities> activitiesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "porjectId")
    private List<Projectadmins> projectadminsList;

    public Projects() {
    }

    public Projects(Integer idProjects) {
        this.idProjects = idProjects;
    }

    public Integer getIdProjects() {
        return idProjects;
    }

    public void setIdProjects(Integer idProjects) {
        this.idProjects = idProjects;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Organization getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Organization organizationId) {
        this.organizationId = organizationId;
    }

    @XmlTransient
    public List<Timesheet> getTimesheetList() {
        return timesheetList;
    }

    public void setTimesheetList(List<Timesheet> timesheetList) {
        this.timesheetList = timesheetList;
    }

    @XmlTransient
    public List<Activities> getActivitiesList() {
        return activitiesList;
    }

    public void setActivitiesList(List<Activities> activitiesList) {
        this.activitiesList = activitiesList;
    }

    @XmlTransient
    public List<Projectadmins> getProjectadminsList() {
        return projectadminsList;
    }

    public void setProjectadminsList(List<Projectadmins> projectadminsList) {
        this.projectadminsList = projectadminsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProjects != null ? idProjects.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projects)) {
            return false;
        }
        Projects other = (Projects) object;
        if ((this.idProjects == null && other.idProjects != null) || (this.idProjects != null && !this.idProjects.equals(other.idProjects))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Projects[ idProjects=" + idProjects + " ]";
    }
    
}
