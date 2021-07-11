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
public class WorkexperiencePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idWorkExperience")
    private int idWorkExperience;
    @Basic(optional = false)
    @NotNull
    @Column(name = "employeeId")
    private int employeeId;

    public WorkexperiencePK() {
    }

    public WorkexperiencePK(int idWorkExperience, int employeeId) {
        this.idWorkExperience = idWorkExperience;
        this.employeeId = employeeId;
    }

    public int getIdWorkExperience() {
        return idWorkExperience;
    }

    public void setIdWorkExperience(int idWorkExperience) {
        this.idWorkExperience = idWorkExperience;
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
        hash += (int) idWorkExperience;
        hash += (int) employeeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkexperiencePK)) {
            return false;
        }
        WorkexperiencePK other = (WorkexperiencePK) object;
        if (this.idWorkExperience != other.idWorkExperience) {
            return false;
        }
        if (this.employeeId != other.employeeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.WorkexperiencePK[ idWorkExperience=" + idWorkExperience + ", employeeId=" + employeeId + " ]";
    }
    
}
