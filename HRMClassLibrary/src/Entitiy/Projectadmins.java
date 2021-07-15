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
@Table(name = "projectadmins")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projectadmins.findAll", query = "SELECT p FROM Projectadmins p")
    , @NamedQuery(name = "Projectadmins.findByIdProjectAdmins", query = "SELECT p FROM Projectadmins p WHERE p.idProjectAdmins = :idProjectAdmins")})
public class Projectadmins implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProjectAdmins")
    private Integer idProjectAdmins;
    @JoinColumn(name = "projectAdmin", referencedColumnName = "idEmployees")
    @ManyToOne(optional = false)
    private Employees projectAdmin;
    @JoinColumn(name = "porjectId", referencedColumnName = "idProjects")
    @ManyToOne(optional = false)
    private Projects porjectId;

    public Projectadmins() {
    }

    public Projectadmins(Integer idProjectAdmins) {
        this.idProjectAdmins = idProjectAdmins;
    }

    public Integer getIdProjectAdmins() {
        return idProjectAdmins;
    }

    public void setIdProjectAdmins(Integer idProjectAdmins) {
        this.idProjectAdmins = idProjectAdmins;
    }

    public Employees getProjectAdmin() {
        return projectAdmin;
    }

    public void setProjectAdmin(Employees projectAdmin) {
        this.projectAdmin = projectAdmin;
    }

    public Projects getPorjectId() {
        return porjectId;
    }

    public void setPorjectId(Projects porjectId) {
        this.porjectId = porjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProjectAdmins != null ? idProjectAdmins.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projectadmins)) {
            return false;
        }
        Projectadmins other = (Projectadmins) object;
        if ((this.idProjectAdmins == null && other.idProjectAdmins != null) || (this.idProjectAdmins != null && !this.idProjectAdmins.equals(other.idProjectAdmins))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Projectadmins[ idProjectAdmins=" + idProjectAdmins + " ]";
    }
    
}
