/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "locations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locations.findAll", query = "SELECT l FROM Locations l")
    , @NamedQuery(name = "Locations.findByIdLocations", query = "SELECT l FROM Locations l WHERE l.idLocations = :idLocations")
    , @NamedQuery(name = "Locations.findByLocationName", query = "SELECT l FROM Locations l WHERE l.locationName = :locationName")
    , @NamedQuery(name = "Locations.findByLocationsCity", query = "SELECT l FROM Locations l WHERE l.locationsCity = :locationsCity")})
public class Locations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLocations")
    private Integer idLocations;
    @Size(max = 100)
    @Column(name = "locationName")
    private String locationName;
    @Size(max = 100)
    @Column(name = "locationsCity")
    private String locationsCity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
    private List<Subunit> subunitList;
    @JoinColumn(name = "organizationId", referencedColumnName = "idOrganization")
    @ManyToOne(optional = false)
    private Organization organizationId;

    public Locations() {
    }

    public Locations(Integer idLocations) {
        this.idLocations = idLocations;
    }

    public Integer getIdLocations() {
        return idLocations;
    }

    public void setIdLocations(Integer idLocations) {
        this.idLocations = idLocations;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationsCity() {
        return locationsCity;
    }

    public void setLocationsCity(String locationsCity) {
        this.locationsCity = locationsCity;
    }

    @XmlTransient
    public List<Subunit> getSubunitList() {
        return subunitList;
    }

    public void setSubunitList(List<Subunit> subunitList) {
        this.subunitList = subunitList;
    }

    public Organization getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Organization organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLocations != null ? idLocations.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locations)) {
            return false;
        }
        Locations other = (Locations) object;
        if ((this.idLocations == null && other.idLocations != null) || (this.idLocations != null && !this.idLocations.equals(other.idLocations))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Locations[ idLocations=" + idLocations + " ]";
    }
    
}
