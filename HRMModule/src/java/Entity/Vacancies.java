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
@Table(name = "vacancies")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vacancies.findAll", query = "SELECT v FROM Vacancies v")
    , @NamedQuery(name = "Vacancies.findByIdVacancies", query = "SELECT v FROM Vacancies v WHERE v.idVacancies = :idVacancies")
    , @NamedQuery(name = "Vacancies.findByStasus", query = "SELECT v FROM Vacancies v WHERE v.stasus = :stasus")})
public class Vacancies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVacancies")
    private Integer idVacancies;
    @Size(max = 45)
    @Column(name = "stasus")
    private String stasus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vacancyId")
    private List<Candidates> candidatesList;
    @JoinColumn(name = "hiringManager", referencedColumnName = "idEmployees")
    @ManyToOne(optional = false)
    private Employees hiringManager;
    @JoinColumn(name = "jobId", referencedColumnName = "idJob")
    @ManyToOne(optional = false)
    private Jobs jobId;

    public Vacancies() {
    }

    public Vacancies(Integer idVacancies) {
        this.idVacancies = idVacancies;
    }

    public Integer getIdVacancies() {
        return idVacancies;
    }

    public void setIdVacancies(Integer idVacancies) {
        this.idVacancies = idVacancies;
    }

    public String getStasus() {
        return stasus;
    }

    public void setStasus(String stasus) {
        this.stasus = stasus;
    }

    @XmlTransient
    public List<Candidates> getCandidatesList() {
        return candidatesList;
    }

    public void setCandidatesList(List<Candidates> candidatesList) {
        this.candidatesList = candidatesList;
    }

    public Employees getHiringManager() {
        return hiringManager;
    }

    public void setHiringManager(Employees hiringManager) {
        this.hiringManager = hiringManager;
    }

    public Jobs getJobId() {
        return jobId;
    }

    public void setJobId(Jobs jobId) {
        this.jobId = jobId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVacancies != null ? idVacancies.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacancies)) {
            return false;
        }
        Vacancies other = (Vacancies) object;
        if ((this.idVacancies == null && other.idVacancies != null) || (this.idVacancies != null && !this.idVacancies.equals(other.idVacancies))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Vacancies[ idVacancies=" + idVacancies + " ]";
    }
    
}
