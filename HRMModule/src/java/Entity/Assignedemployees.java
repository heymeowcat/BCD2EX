/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "assignedemployees")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assignedemployees.findAll", query = "SELECT a FROM Assignedemployees a")
    , @NamedQuery(name = "Assignedemployees.findByIdAssignedEmployees", query = "SELECT a FROM Assignedemployees a WHERE a.idAssignedEmployees = :idAssignedEmployees")
    , @NamedQuery(name = "Assignedemployees.findByTime", query = "SELECT a FROM Assignedemployees a WHERE a.time = :time")
    , @NamedQuery(name = "Assignedemployees.findByStatus", query = "SELECT a FROM Assignedemployees a WHERE a.status = :status")})
public class Assignedemployees implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAssignedEmployees")
    private Integer idAssignedEmployees;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "time")
    private Double time;
    @Size(max = 150)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "activityId", referencedColumnName = "idActivities")
    @ManyToOne(optional = false)
    private Activities activityId;
    @JoinColumn(name = "employeeId", referencedColumnName = "idEmployees")
    @ManyToOne(optional = false)
    private Employees employeeId;

    public Assignedemployees() {
    }

    public Assignedemployees(Integer idAssignedEmployees) {
        this.idAssignedEmployees = idAssignedEmployees;
    }

    public Integer getIdAssignedEmployees() {
        return idAssignedEmployees;
    }

    public void setIdAssignedEmployees(Integer idAssignedEmployees) {
        this.idAssignedEmployees = idAssignedEmployees;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Activities getActivityId() {
        return activityId;
    }

    public void setActivityId(Activities activityId) {
        this.activityId = activityId;
    }

    public Employees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employees employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAssignedEmployees != null ? idAssignedEmployees.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assignedemployees)) {
            return false;
        }
        Assignedemployees other = (Assignedemployees) object;
        if ((this.idAssignedEmployees == null && other.idAssignedEmployees != null) || (this.idAssignedEmployees != null && !this.idAssignedEmployees.equals(other.idAssignedEmployees))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Assignedemployees[ idAssignedEmployees=" + idAssignedEmployees + " ]";
    }
    
}
