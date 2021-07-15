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
@Table(name = "bankingtaxdetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bankingtaxdetails.findAll", query = "SELECT b FROM Bankingtaxdetails b")
    , @NamedQuery(name = "Bankingtaxdetails.findByIdBankingTaxDetails", query = "SELECT b FROM Bankingtaxdetails b WHERE b.idBankingTaxDetails = :idBankingTaxDetails")
    , @NamedQuery(name = "Bankingtaxdetails.findByAccountNumber", query = "SELECT b FROM Bankingtaxdetails b WHERE b.accountNumber = :accountNumber")
    , @NamedQuery(name = "Bankingtaxdetails.findByAccountType", query = "SELECT b FROM Bankingtaxdetails b WHERE b.accountType = :accountType")
    , @NamedQuery(name = "Bankingtaxdetails.findByRoutingNumber", query = "SELECT b FROM Bankingtaxdetails b WHERE b.routingNumber = :routingNumber")
    , @NamedQuery(name = "Bankingtaxdetails.findByTextDetails", query = "SELECT b FROM Bankingtaxdetails b WHERE b.textDetails = :textDetails")})
public class Bankingtaxdetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBankingTaxDetails")
    private Integer idBankingTaxDetails;
    @Column(name = "accountNumber")
    private String accountNumber;
    @Column(name = "accountType")
    private String accountType;
    @Column(name = "routingNumber")
    private String routingNumber;
    @Column(name = "textDetails")
    private String textDetails;
    @JoinColumn(name = "employeeid", referencedColumnName = "idEmployees")
    @ManyToOne(optional = false)
    private Employees employeeid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankingtaxdetails")
    private List<Salarycomponent> salarycomponentList;

    public Bankingtaxdetails() {
    }

    public Bankingtaxdetails(Integer idBankingTaxDetails) {
        this.idBankingTaxDetails = idBankingTaxDetails;
    }

    public Integer getIdBankingTaxDetails() {
        return idBankingTaxDetails;
    }

    public void setIdBankingTaxDetails(Integer idBankingTaxDetails) {
        this.idBankingTaxDetails = idBankingTaxDetails;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getTextDetails() {
        return textDetails;
    }

    public void setTextDetails(String textDetails) {
        this.textDetails = textDetails;
    }

    public Employees getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Employees employeeid) {
        this.employeeid = employeeid;
    }

    @XmlTransient
    public List<Salarycomponent> getSalarycomponentList() {
        return salarycomponentList;
    }

    public void setSalarycomponentList(List<Salarycomponent> salarycomponentList) {
        this.salarycomponentList = salarycomponentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBankingTaxDetails != null ? idBankingTaxDetails.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bankingtaxdetails)) {
            return false;
        }
        Bankingtaxdetails other = (Bankingtaxdetails) object;
        if ((this.idBankingTaxDetails == null && other.idBankingTaxDetails != null) || (this.idBankingTaxDetails != null && !this.idBankingTaxDetails.equals(other.idBankingTaxDetails))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Bankingtaxdetails[ idBankingTaxDetails=" + idBankingTaxDetails + " ]";
    }
    
}
