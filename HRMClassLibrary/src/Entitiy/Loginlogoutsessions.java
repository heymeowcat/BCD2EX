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
@Table(name = "loginlogoutsessions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loginlogoutsessions.findAll", query = "SELECT l FROM Loginlogoutsessions l")
    , @NamedQuery(name = "Loginlogoutsessions.findByIdLoginSessions", query = "SELECT l FROM Loginlogoutsessions l WHERE l.idLoginSessions = :idLoginSessions")
    , @NamedQuery(name = "Loginlogoutsessions.findByIpAddress", query = "SELECT l FROM Loginlogoutsessions l WHERE l.ipAddress = :ipAddress")
    , @NamedQuery(name = "Loginlogoutsessions.findByIntime", query = "SELECT l FROM Loginlogoutsessions l WHERE l.intime = :intime")
    , @NamedQuery(name = "Loginlogoutsessions.findByOuttime", query = "SELECT l FROM Loginlogoutsessions l WHERE l.outtime = :outtime")})
public class Loginlogoutsessions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLoginSessions")
    private Integer idLoginSessions;
    @Column(name = "ipAddress")
    private String ipAddress;
    @Column(name = "intime")
    private String intime;
    @Column(name = "outtime")
    private String outtime;
    @JoinColumn(name = "loginId", referencedColumnName = "idLogs")
    @ManyToOne(optional = false)
    private Login loginId;

    public Loginlogoutsessions() {
    }

    public Loginlogoutsessions(Integer idLoginSessions) {
        this.idLoginSessions = idLoginSessions;
    }

    public Integer getIdLoginSessions() {
        return idLoginSessions;
    }

    public void setIdLoginSessions(Integer idLoginSessions) {
        this.idLoginSessions = idLoginSessions;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public Login getLoginId() {
        return loginId;
    }

    public void setLoginId(Login loginId) {
        this.loginId = loginId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLoginSessions != null ? idLoginSessions.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loginlogoutsessions)) {
            return false;
        }
        Loginlogoutsessions other = (Loginlogoutsessions) object;
        if ((this.idLoginSessions == null && other.idLoginSessions != null) || (this.idLoginSessions != null && !this.idLoginSessions.equals(other.idLoginSessions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Loginlogoutsessions[ idLoginSessions=" + idLoginSessions + " ]";
    }
    
}
