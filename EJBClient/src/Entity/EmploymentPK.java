/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author heymeowcat
 */
@Embeddable
public class EmploymentPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idEmployment")
    private int idEmployment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "employeeId")
    private int employeeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Jobs_idJob")
    private int jobsidJob;

    public EmploymentPK() {
    }

    public EmploymentPK(int idEmployment, int employeeId, int jobsidJob) {
        this.idEmployment = idEmployment;
        this.employeeId = employeeId;
        this.jobsidJob = jobsidJob;
    }

    public int getIdEmployment() {
        return idEmployment;
    }

    public void setIdEmployment(int idEmployment) {
        this.idEmployment = idEmployment;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getJobsidJob() {
        return jobsidJob;
    }

    public void setJobsidJob(int jobsidJob) {
        this.jobsidJob = jobsidJob;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEmployment;
        hash += (int) employeeId;
        hash += (int) jobsidJob;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmploymentPK)) {
            return false;
        }
        EmploymentPK other = (EmploymentPK) object;
        if (this.idEmployment != other.idEmployment) {
            return false;
        }
        if (this.employeeId != other.employeeId) {
            return false;
        }
        if (this.jobsidJob != other.jobsidJob) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.EmploymentPK[ idEmployment=" + idEmployment + ", employeeId=" + employeeId + ", jobsidJob=" + jobsidJob + " ]";
    }
    
}
