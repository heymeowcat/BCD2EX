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
import javax.persistence.Lob;
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
@Table(name = "candidates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Candidates.findAll", query = "SELECT c FROM Candidates c")
    , @NamedQuery(name = "Candidates.findByIdCandidates", query = "SELECT c FROM Candidates c WHERE c.idCandidates = :idCandidates")
    , @NamedQuery(name = "Candidates.findByDateofApplication", query = "SELECT c FROM Candidates c WHERE c.dateofApplication = :dateofApplication")
    , @NamedQuery(name = "Candidates.findByCandidateName", query = "SELECT c FROM Candidates c WHERE c.candidateName = :candidateName")
    , @NamedQuery(name = "Candidates.findByCandidateEmail", query = "SELECT c FROM Candidates c WHERE c.candidateEmail = :candidateEmail")
    , @NamedQuery(name = "Candidates.findByCandidateContactNo", query = "SELECT c FROM Candidates c WHERE c.candidateContactNo = :candidateContactNo")
    , @NamedQuery(name = "Candidates.findByStasus", query = "SELECT c FROM Candidates c WHERE c.stasus = :stasus")})
public class Candidates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idCandidates")
    private Integer idCandidates;
    @Lob
    @Column(name = "resumeUrl")
    private String resumeUrl;
    @Column(name = "dateofApplication")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateofApplication;
    @Column(name = "candidateName")
    private String candidateName;
    @Column(name = "candidateEmail")
    private String candidateEmail;
    @Column(name = "candidateContactNo")
    private String candidateContactNo;
    @Column(name = "stasus")
    private String stasus;
    @JoinColumn(name = "vacancyId", referencedColumnName = "idVacancies")
    @ManyToOne(optional = false)
    private Vacancies vacancyId;

    public Candidates() {
    }

    public Candidates(Integer idCandidates) {
        this.idCandidates = idCandidates;
    }

    public Integer getIdCandidates() {
        return idCandidates;
    }

    public void setIdCandidates(Integer idCandidates) {
        this.idCandidates = idCandidates;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public Date getDateofApplication() {
        return dateofApplication;
    }

    public void setDateofApplication(Date dateofApplication) {
        this.dateofApplication = dateofApplication;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getCandidateContactNo() {
        return candidateContactNo;
    }

    public void setCandidateContactNo(String candidateContactNo) {
        this.candidateContactNo = candidateContactNo;
    }

    public String getStasus() {
        return stasus;
    }

    public void setStasus(String stasus) {
        this.stasus = stasus;
    }

    public Vacancies getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Vacancies vacancyId) {
        this.vacancyId = vacancyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCandidates != null ? idCandidates.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Candidates)) {
            return false;
        }
        Candidates other = (Candidates) object;
        if ((this.idCandidates == null && other.idCandidates != null) || (this.idCandidates != null && !this.idCandidates.equals(other.idCandidates))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Candidates[ idCandidates=" + idCandidates + " ]";
    }
    
}
