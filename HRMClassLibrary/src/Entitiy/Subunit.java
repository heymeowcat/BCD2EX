/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "subunit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subunit.findAll", query = "SELECT s FROM Subunit s")
    , @NamedQuery(name = "Subunit.findByIdSubUnit", query = "SELECT s FROM Subunit s WHERE s.idSubUnit = :idSubUnit")
    , @NamedQuery(name = "Subunit.findBySubUnitName", query = "SELECT s FROM Subunit s WHERE s.subUnitName = :subUnitName")})
public class Subunit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSubUnit")
    private Integer idSubUnit;
    @Column(name = "subUnitName")
    private String subUnitName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subUnitId")
    private List<Jobcategories> jobcategoriesList;
    @JoinColumn(name = "locationId", referencedColumnName = "idLocations")
    @ManyToOne(optional = false)
    private Locations locationId;

    public Subunit() {
    }

    public Subunit(Integer idSubUnit) {
        this.idSubUnit = idSubUnit;
    }

    public Integer getIdSubUnit() {
        return idSubUnit;
    }

    public void setIdSubUnit(Integer idSubUnit) {
        this.idSubUnit = idSubUnit;
    }

    public String getSubUnitName() {
        return subUnitName;
    }

    public void setSubUnitName(String subUnitName) {
        this.subUnitName = subUnitName;
    }

    @XmlTransient
    public List<Jobcategories> getJobcategoriesList() {
        return jobcategoriesList;
    }

    public void setJobcategoriesList(List<Jobcategories> jobcategoriesList) {
        this.jobcategoriesList = jobcategoriesList;
    }

    public Locations getLocationId() {
        return locationId;
    }

    public void setLocationId(Locations locationId) {
        this.locationId = locationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubUnit != null ? idSubUnit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subunit)) {
            return false;
        }
        Subunit other = (Subunit) object;
        if ((this.idSubUnit == null && other.idSubUnit != null) || (this.idSubUnit != null && !this.idSubUnit.equals(other.idSubUnit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Subunit[ idSubUnit=" + idSubUnit + " ]";
    }
    
}
