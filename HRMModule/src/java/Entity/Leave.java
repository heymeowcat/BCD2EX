/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "leave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Leave.findAll", query = "SELECT l FROM Leave l")
    , @NamedQuery(name = "Leave.findByIdLeave", query = "SELECT l FROM Leave l WHERE l.idLeave = :idLeave")
    , @NamedQuery(name = "Leave.findByFromDate", query = "SELECT l FROM Leave l WHERE l.fromDate = :fromDate")
    , @NamedQuery(name = "Leave.findByToDate", query = "SELECT l FROM Leave l WHERE l.toDate = :toDate")
    , @NamedQuery(name = "Leave.findByLeaveBalance", query = "SELECT l FROM Leave l WHERE l.leaveBalance = :leaveBalance")
    , @NamedQuery(name = "Leave.findByNumberOfDays", query = "SELECT l FROM Leave l WHERE l.numberOfDays = :numberOfDays")
    , @NamedQuery(name = "Leave.findByStatus", query = "SELECT l FROM Leave l WHERE l.status = :status")})
public class Leave implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLeave")
    private Integer idLeave;
    @Column(name = "fromDate")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "toDate")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @Column(name = "leaveBalance")
    private Integer leaveBalance;
    @Column(name = "numberOfDays")
    private Integer numberOfDays;
    @Size(max = 150)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "employeeId", referencedColumnName = "idEmployees")
    @ManyToOne(optional = false)
    private Employees employeeId;
    @JoinColumn(name = "assignedBy", referencedColumnName = "idEmployees")
    @ManyToOne(optional = false)
    private Employees assignedBy;
    @JoinColumn(name = "LeaveAction", referencedColumnName = "idLeaveActions")
    @ManyToOne(optional = false)
    private Leaveactions leaveAction;
    @JoinColumn(name = "leavetypeId", referencedColumnName = "idLeaveTypes")
    @ManyToOne(optional = false)
    private Leavetypes leavetypeId;

    public Leave() {
    }

    public Leave(Integer idLeave) {
        this.idLeave = idLeave;
    }

    public Integer getIdLeave() {
        return idLeave;
    }

    public void setIdLeave(Integer idLeave) {
        this.idLeave = idLeave;
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

    public Integer getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(Integer leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employees employeeId) {
        this.employeeId = employeeId;
    }

    public Employees getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Employees assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Leaveactions getLeaveAction() {
        return leaveAction;
    }

    public void setLeaveAction(Leaveactions leaveAction) {
        this.leaveAction = leaveAction;
    }

    public Leavetypes getLeavetypeId() {
        return leavetypeId;
    }

    public void setLeavetypeId(Leavetypes leavetypeId) {
        this.leavetypeId = leavetypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLeave != null ? idLeave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Leave)) {
            return false;
        }
        Leave other = (Leave) object;
        if ((this.idLeave == null && other.idLeave != null) || (this.idLeave != null && !this.idLeave.equals(other.idLeave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Leave[ idLeave=" + idLeave + " ]";
    }
    
}
