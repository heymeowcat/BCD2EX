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
@Table(name = "jobcategories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jobcategories.findAll", query = "SELECT j FROM Jobcategories j")
    , @NamedQuery(name = "Jobcategories.findByIdJobCategories", query = "SELECT j FROM Jobcategories j WHERE j.idJobCategories = :idJobCategories")
    , @NamedQuery(name = "Jobcategories.findByCategoryName", query = "SELECT j FROM Jobcategories j WHERE j.categoryName = :categoryName")})
public class Jobcategories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJobCategories")
    private Integer idJobCategories;
    @Size(max = 100)
    @Column(name = "CategoryName")
    private String categoryName;
    @JoinColumn(name = "subUnitId", referencedColumnName = "idSubUnit")
    @ManyToOne(optional = false)
    private Subunit subUnitId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobCategoryId")
    private List<Jobs> jobsList;

    public Jobcategories() {
    }

    public Jobcategories(Integer idJobCategories) {
        this.idJobCategories = idJobCategories;
    }

    public Integer getIdJobCategories() {
        return idJobCategories;
    }

    public void setIdJobCategories(Integer idJobCategories) {
        this.idJobCategories = idJobCategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Subunit getSubUnitId() {
        return subUnitId;
    }

    public void setSubUnitId(Subunit subUnitId) {
        this.subUnitId = subUnitId;
    }

    @XmlTransient
    public List<Jobs> getJobsList() {
        return jobsList;
    }

    public void setJobsList(List<Jobs> jobsList) {
        this.jobsList = jobsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJobCategories != null ? idJobCategories.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jobcategories)) {
            return false;
        }
        Jobcategories other = (Jobcategories) object;
        if ((this.idJobCategories == null && other.idJobCategories != null) || (this.idJobCategories != null && !this.idJobCategories.equals(other.idJobCategories))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Jobcategories[ idJobCategories=" + idJobCategories + " ]";
    }
    
}
