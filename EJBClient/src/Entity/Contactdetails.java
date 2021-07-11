/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "contactdetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contactdetails.findAll", query = "SELECT c FROM Contactdetails c")
    , @NamedQuery(name = "Contactdetails.findByIdContactDetails", query = "SELECT c FROM Contactdetails c WHERE c.idContactDetails = :idContactDetails")
    , @NamedQuery(name = "Contactdetails.findByAddressSt1", query = "SELECT c FROM Contactdetails c WHERE c.addressSt1 = :addressSt1")
    , @NamedQuery(name = "Contactdetails.findByAddressSt2", query = "SELECT c FROM Contactdetails c WHERE c.addressSt2 = :addressSt2")
    , @NamedQuery(name = "Contactdetails.findByCity", query = "SELECT c FROM Contactdetails c WHERE c.city = :city")
    , @NamedQuery(name = "Contactdetails.findByProvice", query = "SELECT c FROM Contactdetails c WHERE c.provice = :provice")
    , @NamedQuery(name = "Contactdetails.findByPostalCode", query = "SELECT c FROM Contactdetails c WHERE c.postalCode = :postalCode")
    , @NamedQuery(name = "Contactdetails.findByCountry", query = "SELECT c FROM Contactdetails c WHERE c.country = :country")
    , @NamedQuery(name = "Contactdetails.findByHometelephone", query = "SELECT c FROM Contactdetails c WHERE c.hometelephone = :hometelephone")
    , @NamedQuery(name = "Contactdetails.findByMobile", query = "SELECT c FROM Contactdetails c WHERE c.mobile = :mobile")
    , @NamedQuery(name = "Contactdetails.findByWorktelephone", query = "SELECT c FROM Contactdetails c WHERE c.worktelephone = :worktelephone")
    , @NamedQuery(name = "Contactdetails.findByWorkEmail", query = "SELECT c FROM Contactdetails c WHERE c.workEmail = :workEmail")
    , @NamedQuery(name = "Contactdetails.findByOtherEmail", query = "SELECT c FROM Contactdetails c WHERE c.otherEmail = :otherEmail")})
public class Contactdetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idContactDetails")
    private Integer idContactDetails;
    @Size(max = 150)
    @Column(name = "AddressSt1")
    private String addressSt1;
    @Size(max = 150)
    @Column(name = "AddressSt2")
    private String addressSt2;
    @Size(max = 100)
    @Column(name = "City")
    private String city;
    @Size(max = 100)
    @Column(name = "Provice")
    private String provice;
    @Size(max = 10)
    @Column(name = "PostalCode")
    private String postalCode;
    @Size(max = 100)
    @Column(name = "Country")
    private String country;
    @Size(max = 15)
    @Column(name = "Hometelephone")
    private String hometelephone;
    @Size(max = 15)
    @Column(name = "Mobile")
    private String mobile;
    @Size(max = 15)
    @Column(name = "Worktelephone")
    private String worktelephone;
    @Size(max = 200)
    @Column(name = "WorkEmail")
    private String workEmail;
    @Size(max = 200)
    @Column(name = "OtherEmail")
    private String otherEmail;
    @JoinColumn(name = "employeeId", referencedColumnName = "idEmployees")
    @ManyToOne(optional = false)
    private Employees employeeId;

    public Contactdetails() {
    }

    public Contactdetails(Integer idContactDetails) {
        this.idContactDetails = idContactDetails;
    }

    public Integer getIdContactDetails() {
        return idContactDetails;
    }

    public void setIdContactDetails(Integer idContactDetails) {
        this.idContactDetails = idContactDetails;
    }

    public String getAddressSt1() {
        return addressSt1;
    }

    public void setAddressSt1(String addressSt1) {
        this.addressSt1 = addressSt1;
    }

    public String getAddressSt2() {
        return addressSt2;
    }

    public void setAddressSt2(String addressSt2) {
        this.addressSt2 = addressSt2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHometelephone() {
        return hometelephone;
    }

    public void setHometelephone(String hometelephone) {
        this.hometelephone = hometelephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWorktelephone() {
        return worktelephone;
    }

    public void setWorktelephone(String worktelephone) {
        this.worktelephone = worktelephone;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getOtherEmail() {
        return otherEmail;
    }

    public void setOtherEmail(String otherEmail) {
        this.otherEmail = otherEmail;
    }

    public Employees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employees employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContactDetails != null ? idContactDetails.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contactdetails)) {
            return false;
        }
        Contactdetails other = (Contactdetails) object;
        if ((this.idContactDetails == null && other.idContactDetails != null) || (this.idContactDetails != null && !this.idContactDetails.equals(other.idContactDetails))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Contactdetails[ idContactDetails=" + idContactDetails + " ]";
    }
    
}
