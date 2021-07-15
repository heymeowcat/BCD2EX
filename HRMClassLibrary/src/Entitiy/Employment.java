/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "employment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employment.findAll", query = "SELECT e FROM Employment e")
    , @NamedQuery(name = "Employment.findByIdEmployment", query = "SELECT e FROM Employment e WHERE e.employmentPK.idEmployment = :idEmployment")
    , @NamedQuery(name = "Employment.findByEmployeeId", query = "SELECT e FROM Employment e WHERE e.employmentPK.employeeId = :employeeId")
    , @NamedQuery(name = "Employment.findByJobsidJob", query = "SELECT e FROM Employment e WHERE e.employmentPK.jobsidJob = :jobsidJob")
    , @NamedQuery(name = "Employment.findByJoinedDate", query = "SELECT e FROM Employment e WHERE e.joinedDate = :joinedDate")
    , @NamedQuery(name = "Employment.findByContractStartDate", query = "SELECT e FROM Employment e WHERE e.contractStartDate = :contractStartDate")
    , @NamedQuery(name = "Employment.findByContractEndDate", query = "SELECT e FROM Employment e WHERE e.contractEndDate = :contractEndDate")
    , @NamedQuery(name = "Employment.findByStatus", query = "SELECT e FROM Employment e WHERE e.status = :status")})
public class Employment implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmploymentPK employmentPK;
    @Column(name = "joinedDate")
    @Temporal(TemporalType.DATE)
    private Date joinedDate;
    @Column(name = "contractStartDate")
    @Temporal(TemporalType.DATE)
    private Date contractStartDate;
    @Column(name = "contractEndDate")
    @Temporal(TemporalType.DATE)
    private Date contractEndDate;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "employeeId", referencedColumnName = "idEmployees", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employees employees;
    @JoinColumn(name = "Jobs_idJob", referencedColumnName = "idJob", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jobs jobs;

    public Employment() {
    }

    public Employment(EmploymentPK employmentPK) {
        this.employmentPK = employmentPK;
    }

    public Employment(int idEmployment, int employeeId, int jobsidJob) {
        this.employmentPK = new EmploymentPK(idEmployment, employeeId, jobsidJob);
    }

    public EmploymentPK getEmploymentPK() {
        return employmentPK;
    }

    public void setEmploymentPK(EmploymentPK employmentPK) {
        this.employmentPK = employmentPK;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Jobs getJobs() {
        return jobs;
    }

    public void setJobs(Jobs jobs) {
        this.jobs = jobs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employmentPK != null ? employmentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employment)) {
            return false;
        }
        Employment other = (Employment) object;
        if ((this.employmentPK == null && other.employmentPK != null) || (this.employmentPK != null && !this.employmentPK.equals(other.employmentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Employment[ employmentPK=" + employmentPK + " ]";
    }
    
}
