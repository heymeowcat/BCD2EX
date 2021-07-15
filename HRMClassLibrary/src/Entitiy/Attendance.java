/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "attendance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a")
    , @NamedQuery(name = "Attendance.findByIdAttendance", query = "SELECT a FROM Attendance a WHERE a.idAttendance = :idAttendance")
    , @NamedQuery(name = "Attendance.findByPunchIn", query = "SELECT a FROM Attendance a WHERE a.punchIn = :punchIn")
    , @NamedQuery(name = "Attendance.findByPunchInNote", query = "SELECT a FROM Attendance a WHERE a.punchInNote = :punchInNote")
    , @NamedQuery(name = "Attendance.findByPunchOut", query = "SELECT a FROM Attendance a WHERE a.punchOut = :punchOut")
    , @NamedQuery(name = "Attendance.findByPunchOutNote", query = "SELECT a FROM Attendance a WHERE a.punchOutNote = :punchOutNote")
    , @NamedQuery(name = "Attendance.findByDuration", query = "SELECT a FROM Attendance a WHERE a.duration = :duration")})
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAttendance")
    private Integer idAttendance;
    @Column(name = "punchIn")
    private String punchIn;
    @Column(name = "punchInNote")
    private String punchInNote;
    @Column(name = "punchOut")
    private String punchOut;
    @Column(name = "punchOutNote")
    private String punchOutNote;
    @Column(name = "duration")
    private String duration;
    @JoinColumn(name = "employeeId", referencedColumnName = "idEmployees")
    @ManyToOne(optional = false)
    private Employees employeeId;
    @JoinColumn(name = "workShiftId", referencedColumnName = "idWorkShifts")
    @ManyToOne(optional = false)
    private Workshifts workShiftId;

    public Attendance() {
    }

    public Attendance(Integer idAttendance) {
        this.idAttendance = idAttendance;
    }

    public Integer getIdAttendance() {
        return idAttendance;
    }

    public void setIdAttendance(Integer idAttendance) {
        this.idAttendance = idAttendance;
    }

    public String getPunchIn() {
        return punchIn;
    }

    public void setPunchIn(String punchIn) {
        this.punchIn = punchIn;
    }

    public String getPunchInNote() {
        return punchInNote;
    }

    public void setPunchInNote(String punchInNote) {
        this.punchInNote = punchInNote;
    }

    public String getPunchOut() {
        return punchOut;
    }

    public void setPunchOut(String punchOut) {
        this.punchOut = punchOut;
    }

    public String getPunchOutNote() {
        return punchOutNote;
    }

    public void setPunchOutNote(String punchOutNote) {
        this.punchOutNote = punchOutNote;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Employees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employees employeeId) {
        this.employeeId = employeeId;
    }

    public Workshifts getWorkShiftId() {
        return workShiftId;
    }

    public void setWorkShiftId(Workshifts workShiftId) {
        this.workShiftId = workShiftId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAttendance != null ? idAttendance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendance)) {
            return false;
        }
        Attendance other = (Attendance) object;
        if ((this.idAttendance == null && other.idAttendance != null) || (this.idAttendance != null && !this.idAttendance.equals(other.idAttendance))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Attendance[ idAttendance=" + idAttendance + " ]";
    }
    
}
