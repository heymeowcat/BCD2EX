/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "login")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Login.findAll", query = "SELECT l FROM Login l")
    , @NamedQuery(name = "Login.findByIdLogs", query = "SELECT l FROM Login l WHERE l.loginPK.idLogs = :idLogs")
    , @NamedQuery(name = "Login.findByUsername", query = "SELECT l FROM Login l WHERE l.username = :username")
    , @NamedQuery(name = "Login.findByPassword", query = "SELECT l FROM Login l WHERE l.password = :password")
    , @NamedQuery(name = "Login.findByLoginRole", query = "SELECT l FROM Login l WHERE l.loginRole = :loginRole")
    , @NamedQuery(name = "Login.findByStatus", query = "SELECT l FROM Login l WHERE l.status = :status")
    , @NamedQuery(name = "Login.findByEmployeeId", query = "SELECT l FROM Login l WHERE l.loginPK.employeeId = :employeeId")})
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LoginPK loginPK;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "loginRole")
    private String loginRole;
    @Column(name = "Status")
    private String status;
    @JoinColumn(name = "employeeId", referencedColumnName = "idEmployees", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employees employees;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loginId")
    private List<Loginlogoutsessions> loginlogoutsessionsList;

    public Login() {
    }

    public Login(LoginPK loginPK) {
        this.loginPK = loginPK;
    }

    public Login(int idLogs, int employeeId) {
        this.loginPK = new LoginPK(idLogs, employeeId);
    }

    public LoginPK getLoginPK() {
        return loginPK;
    }

    public void setLoginPK(LoginPK loginPK) {
        this.loginPK = loginPK;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginRole() {
        return loginRole;
    }

    public void setLoginRole(String loginRole) {
        this.loginRole = loginRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    @XmlTransient
    public List<Loginlogoutsessions> getLoginlogoutsessionsList() {
        return loginlogoutsessionsList;
    }

    public void setLoginlogoutsessionsList(List<Loginlogoutsessions> loginlogoutsessionsList) {
        this.loginlogoutsessionsList = loginlogoutsessionsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginPK != null ? loginPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Login)) {
            return false;
        }
        Login other = (Login) object;
        if ((this.loginPK == null && other.loginPK != null) || (this.loginPK != null && !this.loginPK.equals(other.loginPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entitiy.Login[ loginPK=" + loginPK + " ]";
    }
    
}
