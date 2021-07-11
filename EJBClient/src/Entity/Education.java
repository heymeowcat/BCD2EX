/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "education")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Education.findAll", query = "SELECT e FROM Education e")
    , @NamedQuery(name = "Education.findByIdEducation", query = "SELECT e FROM Education e WHERE e.educationPK.idEducation = :idEducation")
    , @NamedQuery(name = "Education.findByEmployeeId", query = "SELECT e FROM Education e WHERE e.educationPK.employeeId = :employeeId")
    , @NamedQuery(name = "Education.findByLevel", query = "SELECT e FROM Education e WHERE e.level = :level")
    , @NamedQuery(name = "Education.findByYear", query = "SELECT e FROM Education e WHERE e.year = :year")
    , @NamedQuery(name = "Education.findByGPAscrore", query = "SELECT e FROM Education e WHERE e.gPAscrore = :gPAscrore")
    , @NamedQuery(name = "Education.findByStartDate", query = "SELECT e FROM Education e WHERE e.startDate = :startDate")
    , @NamedQuery(name = "Education.findByEndDate", query = "SELECT e FROM Education e WHERE e.endDate = :endDate")})
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EducationPK educationPK;
    @Size(max = 45)
    @Column(name = "level")
    private String level;
    @Column(name = "Year")
    @Temporal(TemporalType.DATE)
    private Date year;
    @Size(max = 45)
    @Column(name = "GPA_scrore")
    private String gPAscrore;
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @JoinColumn(name = "employeeId", referencedColumnName = "idEmployees", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employees employees;

    public Education() {
    }

    public Education(EducationPK educationPK) {
        this.educationPK = educationPK;
    }

    public Education(int idEducation, int employeeId) {
        this.educationPK = new EducationPK(idEducation, employeeId);
    }

    public EducationPK getEducationPK() {
        return educationPK;
    }

    public void setEducationPK(EducationPK educationPK) {
        this.educationPK = educationPK;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getGPAscrore() {
        return gPAscrore;
    }

    public void setGPAscrore(String gPAscrore) {
        this.gPAscrore = gPAscrore;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        hash += (educationPK != null ? educationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Education)) {
            return false;
        }
        Education other = (Education) object;
        if ((this.educationPK == null && other.educationPK != null) || (this.educationPK != null && !this.educationPK.equals(other.educationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Education[ educationPK=" + educationPK + " ]";
    }
    
}
