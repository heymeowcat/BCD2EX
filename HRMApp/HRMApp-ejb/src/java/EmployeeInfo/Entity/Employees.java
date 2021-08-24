/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "employees")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employees.findAll", query = "SELECT e FROM Employees e")
    , @NamedQuery(name = "Employees.findByIdEmployees", query = "SELECT e FROM Employees e WHERE e.idEmployees = :idEmployees")
    , @NamedQuery(name = "Employees.findByFirstName", query = "SELECT e FROM Employees e WHERE e.firstName = :firstName")
    , @NamedQuery(name = "Employees.findByLastName", query = "SELECT e FROM Employees e WHERE e.lastName = :lastName")
    , @NamedQuery(name = "Employees.findByDob", query = "SELECT e FROM Employees e WHERE e.dob = :dob")
    , @NamedQuery(name = "Employees.findByNic", query = "SELECT e FROM Employees e WHERE e.nic = :nic")})
public class Employees implements Serializable {


    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "profilePicURL")
    private String profilePicURL;
    @Column(name = "NIC")
    private String nic;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private Collection<Employment> employmentCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEmployees")
    private Integer idEmployees;
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @JoinColumn(name = "gender", referencedColumnName = "idGender")
    @ManyToOne(optional = false)
    private Gender gender;
    @JoinColumn(name = "role", referencedColumnName = "idUserRole")
    @ManyToOne(optional = false)
    private Userrole role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private List<Login> loginList;

    public Employees() {
    }

    public Employees(Integer idEmployees) {
        this.idEmployees = idEmployees;
    }

    public Integer getIdEmployees() {
        return idEmployees;
    }

    public void setIdEmployees(Integer idEmployees) {
        this.idEmployees = idEmployees;
    }


    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Userrole getRole() {
        return role;
    }

    public void setRole(Userrole role) {
        this.role = role;
    }

    @XmlTransient
    public List<Login> getLoginList() {
        return loginList;
    }

    public void setLoginList(List<Login> loginList) {
        this.loginList = loginList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmployees != null ? idEmployees.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.idEmployees == null && other.idEmployees != null) || (this.idEmployees != null && !this.idEmployees.equals(other.idEmployees))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmployeeInfo.Entity.Employees[ idEmployees=" + idEmployees + " ]";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @XmlTransient
    public Collection<Employment> getEmploymentCollection() {
        return employmentCollection;
    }

    public void setEmploymentCollection(Collection<Employment> employmentCollection) {
        this.employmentCollection = employmentCollection;
    }
    
}
