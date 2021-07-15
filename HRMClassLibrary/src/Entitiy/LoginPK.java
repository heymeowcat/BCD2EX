/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author heymeowcat
 */
@Embeddable
public class LoginPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idLogs")
    private int idLogs;
    @Basic(optional = false)
    @Column(name = "employeeId")
    private int employeeId;

    public LoginPK() {
    }

    public LoginPK(int idLogs, int employeeId) {
        this.idLogs = idLogs;
        this.employeeId = employeeId;
    }

    public int getIdLogs() {
        return idLogs;
    }

    public void setIdLogs(int idLogs) {
        this.idLogs = idLogs;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLogs;
        hash += (int) employeeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginPK)) {
            return false;
        }
        LoginPK other = (LoginPK) object;
        if (this.idLogs != other.idLogs) {
            return false;
        }
        if (this.employeeId != other.employeeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.LoginPK[ idLogs=" + idLogs + ", employeeId=" + employeeId + " ]";
    }
    
}
