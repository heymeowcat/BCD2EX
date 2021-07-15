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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "activities")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Activities.findAll", query = "SELECT a FROM Activities a")
    , @NamedQuery(name = "Activities.findByIdActivities", query = "SELECT a FROM Activities a WHERE a.idActivities = :idActivities")
    , @NamedQuery(name = "Activities.findByActivityName", query = "SELECT a FROM Activities a WHERE a.activityName = :activityName")
    , @NamedQuery(name = "Activities.findByTotalTime", query = "SELECT a FROM Activities a WHERE a.totalTime = :totalTime")})
public class Activities implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idActivities")
    private Integer idActivities;
    @Column(name = "activityName")
    private String activityName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totalTime")
    private Double totalTime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityId")
    private List<Assignedemployees> assignedemployeesList;
    @JoinColumn(name = "projectId", referencedColumnName = "idProjects")
    @ManyToOne(optional = false)
    private Projects projectId;

    public Activities() {
    }

    public Activities(Integer idActivities) {
        this.idActivities = idActivities;
    }

    public Integer getIdActivities() {
        return idActivities;
    }

    public void setIdActivities(Integer idActivities) {
        this.idActivities = idActivities;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }

    @XmlTransient
    public List<Assignedemployees> getAssignedemployeesList() {
        return assignedemployeesList;
    }

    public void setAssignedemployeesList(List<Assignedemployees> assignedemployeesList) {
        this.assignedemployeesList = assignedemployeesList;
    }

    public Projects getProjectId() {
        return projectId;
    }

    public void setProjectId(Projects projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActivities != null ? idActivities.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Activities)) {
            return false;
        }
        Activities other = (Activities) object;
        if ((this.idActivities == null && other.idActivities != null) || (this.idActivities != null && !this.idActivities.equals(other.idActivities))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Activities[ idActivities=" + idActivities + " ]";
    }
    
}
