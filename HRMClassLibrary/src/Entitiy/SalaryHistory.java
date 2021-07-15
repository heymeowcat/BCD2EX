/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heymeowcat
 */
@Entity
@Table(name = "salary_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SalaryHistory.findAll", query = "SELECT s FROM SalaryHistory s")
    , @NamedQuery(name = "SalaryHistory.findByIdSalaryHistory", query = "SELECT s FROM SalaryHistory s WHERE s.idSalaryHistory = :idSalaryHistory")
    , @NamedQuery(name = "SalaryHistory.findByDate", query = "SELECT s FROM SalaryHistory s WHERE s.date = :date")
    , @NamedQuery(name = "SalaryHistory.findBySalaryAmount", query = "SELECT s FROM SalaryHistory s WHERE s.salaryAmount = :salaryAmount")})
public class SalaryHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idSalary_History")
    private Integer idSalaryHistory;
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salaryAmount")
    private Double salaryAmount;
    @JoinColumns({
        @JoinColumn(name = "salaryComponentId", referencedColumnName = "idSalary")
        , @JoinColumn(name = "salaryEmployeeId", referencedColumnName = "employeeId")})
    @ManyToOne(optional = false)
    private Salarycomponent salarycomponent;

    public SalaryHistory() {
    }

    public SalaryHistory(Integer idSalaryHistory) {
        this.idSalaryHistory = idSalaryHistory;
    }

    public Integer getIdSalaryHistory() {
        return idSalaryHistory;
    }

    public void setIdSalaryHistory(Integer idSalaryHistory) {
        this.idSalaryHistory = idSalaryHistory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(Double salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public Salarycomponent getSalarycomponent() {
        return salarycomponent;
    }

    public void setSalarycomponent(Salarycomponent salarycomponent) {
        this.salarycomponent = salarycomponent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSalaryHistory != null ? idSalaryHistory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalaryHistory)) {
            return false;
        }
        SalaryHistory other = (SalaryHistory) object;
        if ((this.idSalaryHistory == null && other.idSalaryHistory != null) || (this.idSalaryHistory != null && !this.idSalaryHistory.equals(other.idSalaryHistory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.SalaryHistory[ idSalaryHistory=" + idSalaryHistory + " ]";
    }
    
}
