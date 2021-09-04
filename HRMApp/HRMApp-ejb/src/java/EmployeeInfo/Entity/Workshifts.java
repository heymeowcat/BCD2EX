/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Entity;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "workshifts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workshifts.findAll", query = "SELECT w FROM Workshifts w")
    , @NamedQuery(name = "Workshifts.findByIdWorkShifts", query = "SELECT w FROM Workshifts w WHERE w.idWorkShifts = :idWorkShifts")
    , @NamedQuery(name = "Workshifts.findByShiftName", query = "SELECT w FROM Workshifts w WHERE w.shiftName = :shiftName")
    , @NamedQuery(name = "Workshifts.findByFrom", query = "SELECT w FROM Workshifts w WHERE w.from = :from")
    , @NamedQuery(name = "Workshifts.findByTo", query = "SELECT w FROM Workshifts w WHERE w.to = :to")
    , @NamedQuery(name = "Workshifts.findByHoursperday", query = "SELECT w FROM Workshifts w WHERE w.hoursperday = :hoursperday")})
public class Workshifts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idWorkShifts")
    private Integer idWorkShifts;
    @Column(name = "ShiftName")
    private String shiftName;
    @Column(name="\"from\"")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date from;
    @Column(name="\"to\"")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date to;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "hoursperday")
    private Double hoursperday;
    @JoinColumn(name = "jobId", referencedColumnName = "idJob")
    @ManyToOne(optional = false)
    private Jobs jobId;

    public Workshifts() {
    }

    public Workshifts(Integer idWorkShifts) {
        this.idWorkShifts = idWorkShifts;
    }

    public Integer getIdWorkShifts() {
        return idWorkShifts;
    }

    public void setIdWorkShifts(Integer idWorkShifts) {
        this.idWorkShifts = idWorkShifts;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Double getHoursperday() {
        return hoursperday;
    }

    public void setHoursperday(Double hoursperday) {
        this.hoursperday = hoursperday;
    }

    public Jobs getJobId() {
        return jobId;
    }

    public void setJobId(Jobs jobId) {
        this.jobId = jobId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idWorkShifts != null ? idWorkShifts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workshifts)) {
            return false;
        }
        Workshifts other = (Workshifts) object;
        if ((this.idWorkShifts == null && other.idWorkShifts != null) || (this.idWorkShifts != null && !this.idWorkShifts.equals(other.idWorkShifts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmployeeInfo.Entity.Workshifts[ idWorkShifts=" + idWorkShifts + " ]";
    }
    
}
