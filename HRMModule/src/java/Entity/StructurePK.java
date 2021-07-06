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
public class StructurePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idStructure")
    private int idStructure;
    @Basic(optional = false)
    @NotNull
    @Column(name = "organizationId")
    private int organizationId;

    public StructurePK() {
    }

    public StructurePK(int idStructure, int organizationId) {
        this.idStructure = idStructure;
        this.organizationId = organizationId;
    }

    public int getIdStructure() {
        return idStructure;
    }

    public void setIdStructure(int idStructure) {
        this.idStructure = idStructure;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idStructure;
        hash += (int) organizationId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StructurePK)) {
            return false;
        }
        StructurePK other = (StructurePK) object;
        if (this.idStructure != other.idStructure) {
            return false;
        }
        if (this.organizationId != other.organizationId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.StructurePK[ idStructure=" + idStructure + ", organizationId=" + organizationId + " ]";
    }

}
