/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "structure")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Structure.findAll", query = "SELECT s FROM Structure s")
    , @NamedQuery(name = "Structure.findByIdStructure", query = "SELECT s FROM Structure s WHERE s.structurePK.idStructure = :idStructure")
    , @NamedQuery(name = "Structure.findByStructureName", query = "SELECT s FROM Structure s WHERE s.structureName = :structureName")
    , @NamedQuery(name = "Structure.findByOrganizationId", query = "SELECT s FROM Structure s WHERE s.structurePK.organizationId = :organizationId")})
public class Structure implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StructurePK structurePK;
    @Column(name = "structureName")
    private String structureName;
    @JoinColumn(name = "organizationId", referencedColumnName = "idOrganization", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Organization organization;
    @OneToMany(mappedBy = "subStructureId")
    private List<Structure> structureList;
    @JoinColumn(name = "subStructureId", referencedColumnName = "idStructure")
    @ManyToOne
    private Structure subStructureId;

    public Structure() {
    }

    public Structure(StructurePK structurePK) {
        this.structurePK = structurePK;
    }

    public Structure(int idStructure, int organizationId) {
        this.structurePK = new StructurePK(idStructure, organizationId);
    }

    public StructurePK getStructurePK() {
        return structurePK;
    }

    public void setStructurePK(StructurePK structurePK) {
        this.structurePK = structurePK;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @XmlTransient
    public List<Structure> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<Structure> structureList) {
        this.structureList = structureList;
    }

    public Structure getSubStructureId() {
        return subStructureId;
    }

    public void setSubStructureId(Structure subStructureId) {
        this.subStructureId = subStructureId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (structurePK != null ? structurePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Structure)) {
            return false;
        }
        Structure other = (Structure) object;
        if ((this.structurePK == null && other.structurePK != null) || (this.structurePK != null && !this.structurePK.equals(other.structurePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Structure[ structurePK=" + structurePK + " ]";
    }
    
}
