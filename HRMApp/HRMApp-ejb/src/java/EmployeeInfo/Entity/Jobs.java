/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Entity;


import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "jobs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jobs.findAll", query = "SELECT j FROM Jobs j")
    , @NamedQuery(name = "Jobs.findByIdJob", query = "SELECT j FROM Jobs j WHERE j.idJob = :idJob")
    , @NamedQuery(name = "Jobs.findByJobTitle", query = "SELECT j FROM Jobs j WHERE j.jobTitle = :jobTitle")
    , @NamedQuery(name = "Jobs.findByJobDescription", query = "SELECT j FROM Jobs j WHERE j.jobDescription = :jobDescription")})
public class Jobs implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobId")
    private List<Workshifts> workshiftsList;

    @Column(name = "jobTitle")
    private String jobTitle;
    @Column(name = "jobDescription")
    private String jobDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobs")
    private Collection<Employment> employmentCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJob")
    private Integer idJob;
    @JoinColumn(name = "jobCategoryId", referencedColumnName = "idJobCategories")
    @ManyToOne(optional = false)
    private Jobcategories jobCategoryId;

    public Jobs() {
    }

    public Jobs(Integer idJob) {
        this.idJob = idJob;
    }

    public Integer getIdJob() {
        return idJob;
    }

    public void setIdJob(Integer idJob) {
        this.idJob = idJob;
    }


    public Jobcategories getJobCategoryId() {
        return jobCategoryId;
    }

    public void setJobCategoryId(Jobcategories jobCategoryId) {
        this.jobCategoryId = jobCategoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJob != null ? idJob.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jobs)) {
            return false;
        }
        Jobs other = (Jobs) object;
        if ((this.idJob == null && other.idJob != null) || (this.idJob != null && !this.idJob.equals(other.idJob))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmployeeInfo.Entity.Jobs[ idJob=" + idJob + " ]";
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @XmlTransient
    public Collection<Employment> getEmploymentCollection() {
        return employmentCollection;
    }

    public void setEmploymentCollection(Collection<Employment> employmentCollection) {
        this.employmentCollection = employmentCollection;
    }

    @XmlTransient
    public List<Workshifts> getWorkshiftsList() {
        return workshiftsList;
    }

    public void setWorkshiftsList(List<Workshifts> workshiftsList) {
        this.workshiftsList = workshiftsList;
    }
    
}
