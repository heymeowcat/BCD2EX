/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "salarycomponent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salarycomponent.findAll", query = "SELECT s FROM Salarycomponent s")
    , @NamedQuery(name = "Salarycomponent.findByIdSalary", query = "SELECT s FROM Salarycomponent s WHERE s.salarycomponentPK.idSalary = :idSalary")
    , @NamedQuery(name = "Salarycomponent.findByEmployeeId", query = "SELECT s FROM Salarycomponent s WHERE s.salarycomponentPK.employeeId = :employeeId")
    , @NamedQuery(name = "Salarycomponent.findByPayGrade", query = "SELECT s FROM Salarycomponent s WHERE s.payGrade = :payGrade")
    , @NamedQuery(name = "Salarycomponent.findByPayFrequency", query = "SELECT s FROM Salarycomponent s WHERE s.payFrequency = :payFrequency")
    , @NamedQuery(name = "Salarycomponent.findByCurrency", query = "SELECT s FROM Salarycomponent s WHERE s.currency = :currency")
    , @NamedQuery(name = "Salarycomponent.findByAmount", query = "SELECT s FROM Salarycomponent s WHERE s.amount = :amount")
    , @NamedQuery(name = "Salarycomponent.findByDepositDetails", query = "SELECT s FROM Salarycomponent s WHERE s.salarycomponentPK.depositDetails = :depositDetails")
    , @NamedQuery(name = "Salarycomponent.findByStatus", query = "SELECT s FROM Salarycomponent s WHERE s.status = :status")})
public class Salarycomponent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SalarycomponentPK salarycomponentPK;
    @Size(max = 45)
    @Column(name = "payGrade")
    private String payGrade;
    @Size(max = 45)
    @Column(name = "payFrequency")
    private String payFrequency;
    @Size(max = 100)
    @Column(name = "currency")
    private String currency;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Lob
    @Size(max = 65535)
    @Column(name = "comment")
    private String comment;
    @Size(max = 45)
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salarycomponent")
    private List<SalaryHistory> salaryHistoryList;
    @JoinColumn(name = "depositDetails", referencedColumnName = "idBankingTaxDetails", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bankingtaxdetails bankingtaxdetails;
    @JoinColumn(name = "employeeId", referencedColumnName = "idEmployees", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employees employees;

    public Salarycomponent() {
    }

    public Salarycomponent(SalarycomponentPK salarycomponentPK) {
        this.salarycomponentPK = salarycomponentPK;
    }

    public Salarycomponent(int idSalary, int employeeId, int depositDetails) {
        this.salarycomponentPK = new SalarycomponentPK(idSalary, employeeId, depositDetails);
    }

    public SalarycomponentPK getSalarycomponentPK() {
        return salarycomponentPK;
    }

    public void setSalarycomponentPK(SalarycomponentPK salarycomponentPK) {
        this.salarycomponentPK = salarycomponentPK;
    }

    public String getPayGrade() {
        return payGrade;
    }

    public void setPayGrade(String payGrade) {
        this.payGrade = payGrade;
    }

    public String getPayFrequency() {
        return payFrequency;
    }

    public void setPayFrequency(String payFrequency) {
        this.payFrequency = payFrequency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<SalaryHistory> getSalaryHistoryList() {
        return salaryHistoryList;
    }

    public void setSalaryHistoryList(List<SalaryHistory> salaryHistoryList) {
        this.salaryHistoryList = salaryHistoryList;
    }

    public Bankingtaxdetails getBankingtaxdetails() {
        return bankingtaxdetails;
    }

    public void setBankingtaxdetails(Bankingtaxdetails bankingtaxdetails) {
        this.bankingtaxdetails = bankingtaxdetails;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salarycomponentPK != null ? salarycomponentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salarycomponent)) {
            return false;
        }
        Salarycomponent other = (Salarycomponent) object;
        if ((this.salarycomponentPK == null && other.salarycomponentPK != null) || (this.salarycomponentPK != null && !this.salarycomponentPK.equals(other.salarycomponentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Salarycomponent[ salarycomponentPK=" + salarycomponentPK + " ]";
    }
    
}
