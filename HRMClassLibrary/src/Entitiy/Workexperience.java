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
@Table(name = "workexperience")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workexperience.findAll", query = "SELECT w FROM Workexperience w")
    , @NamedQuery(name = "Workexperience.findByIdWorkExperience", query = "SELECT w FROM Workexperience w WHERE w.workexperiencePK.idWorkExperience = :idWorkExperience")
    , @NamedQuery(name = "Workexperience.findByEmployeeId", query = "SELECT w FROM Workexperience w WHERE w.workexperiencePK.employeeId = :employeeId")
    , @NamedQuery(name = "Workexperience.findByCompany", query = "SELECT w FROM Workexperience w WHERE w.company = :company")
    , @NamedQuery(name = "Workexperience.findByJobTitle", query = "SELECT w FROM Workexperience w WHERE w.jobTitle = :jobTitle")
    , @NamedQuery(name = "Workexperience.findByFromDate", query = "SELECT w FROM Workexperience w WHERE w.fromDate = :fromDate")
    , @NamedQuery(name = "Workexperience.findByToDate", query = "SELECT w FROM Workexperience w WHERE w.toDate = :toDate")})
public class Workexperience implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkexperiencePK workexperiencePK;
    @Column(name = "company")
    private String company;
    @Column(name = "jobTitle")
    private String jobTitle;
    @Column(name = "fromDate")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "toDate")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @JoinColumn(name = "employeeId", referencedColumnName = "idEmployees", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employees employees;

    public Workexperience() {
    }

    public Workexperience(WorkexperiencePK workexperiencePK) {
        this.workexperiencePK = workexperiencePK;
    }

    public Workexperience(int idWorkExperience, int employeeId) {
        this.workexperiencePK = new WorkexperiencePK(idWorkExperience, employeeId);
    }

    public WorkexperiencePK getWorkexperiencePK() {
        return workexperiencePK;
    }

    public void setWorkexperiencePK(WorkexperiencePK workexperiencePK) {
        this.workexperiencePK = workexperiencePK;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workexperiencePK != null ? workexperiencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workexperience)) {
            return false;
        }
        Workexperience other = (Workexperience) object;
        if ((this.workexperiencePK == null && other.workexperiencePK != null) || (this.workexperiencePK != null && !this.workexperiencePK.equals(other.workexperiencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Workexperience[ workexperiencePK=" + workexperiencePK + " ]";
    }
    
}
