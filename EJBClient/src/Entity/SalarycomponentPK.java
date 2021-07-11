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
public class SalarycomponentPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idSalary")
    private int idSalary;
    @Basic(optional = false)
    @NotNull
    @Column(name = "employeeId")
    private int employeeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "depositDetails")
    private int depositDetails;

    public SalarycomponentPK() {
    }

    public SalarycomponentPK(int idSalary, int employeeId, int depositDetails) {
        this.idSalary = idSalary;
        this.employeeId = employeeId;
        this.depositDetails = depositDetails;
    }

    public int getIdSalary() {
        return idSalary;
    }

    public void setIdSalary(int idSalary) {
        this.idSalary = idSalary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getDepositDetails() {
        return depositDetails;
    }

    public void setDepositDetails(int depositDetails) {
        this.depositDetails = depositDetails;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idSalary;
        hash += (int) employeeId;
        hash += (int) depositDetails;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalarycomponentPK)) {
            return false;
        }
        SalarycomponentPK other = (SalarycomponentPK) object;
        if (this.idSalary != other.idSalary) {
            return false;
        }
        if (this.employeeId != other.employeeId) {
            return false;
        }
        if (this.depositDetails != other.depositDetails) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.SalarycomponentPK[ idSalary=" + idSalary + ", employeeId=" + employeeId + ", depositDetails=" + depositDetails + " ]";
    }
    
}
