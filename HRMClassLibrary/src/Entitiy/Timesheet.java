/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

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
@Table(name = "timesheet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Timesheet.findAll", query = "SELECT t FROM Timesheet t")
    , @NamedQuery(name = "Timesheet.findByIdTimesheet", query = "SELECT t FROM Timesheet t WHERE t.idTimesheet = :idTimesheet")
    , @NamedQuery(name = "Timesheet.findByStatus", query = "SELECT t FROM Timesheet t WHERE t.status = :status")
    , @NamedQuery(name = "Timesheet.findByDateTime", query = "SELECT t FROM Timesheet t WHERE t.dateTime = :dateTime")})
public class Timesheet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTimesheet")
    private Integer idTimesheet;
    @Column(name = "status")
    private String status;
    @Column(name = "dateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @JoinColumn(name = "projectId", referencedColumnName = "idProjects")
    @ManyToOne(optional = false)
    private Projects projectId;

    public Timesheet() {
    }

    public Timesheet(Integer idTimesheet) {
        this.idTimesheet = idTimesheet;
    }

    public Integer getIdTimesheet() {
        return idTimesheet;
    }

    public void setIdTimesheet(Integer idTimesheet) {
        this.idTimesheet = idTimesheet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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
        hash += (idTimesheet != null ? idTimesheet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Timesheet)) {
            return false;
        }
        Timesheet other = (Timesheet) object;
        if ((this.idTimesheet == null && other.idTimesheet != null) || (this.idTimesheet != null && !this.idTimesheet.equals(other.idTimesheet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Timesheet[ idTimesheet=" + idTimesheet + " ]";
    }
    
}
