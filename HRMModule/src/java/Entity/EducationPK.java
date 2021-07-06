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
public class EducationPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idEducation")
    private int idEducation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "employeeId")
    private int employeeId;

    public EducationPK() {
    }

    public EducationPK(int idEducation, int employeeId) {
        this.idEducation = idEducation;
        this.employeeId = employeeId;
    }

    public int getIdEducation() {
        return idEducation;
    }

    public void setIdEducation(int idEducation) {
        this.idEducation = idEducation;
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
        hash += (int) idEducation;
        hash += (int) employeeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EducationPK)) {
            return false;
        }
        EducationPK other = (EducationPK) object;
        if (this.idEducation != other.idEducation) {
            return false;
        }
        if (this.employeeId != other.employeeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.EducationPK[ idEducation=" + idEducation + ", employeeId=" + employeeId + " ]";
    }
    
}
