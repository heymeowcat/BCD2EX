/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitiy;

import java.io.Serializable;
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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEmployees")
    private Integer idEmployees;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Lob
    @Column(name = "profilePicURL")
    private String profilePicURL;
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "NIC")
    private String nic;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private List<Education> educationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private List<Login> loginList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private List<Contactdetails> contactdetailsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private List<Leave> leaveList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignedBy")
    private List<Leave> leaveList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeid")
    private List<Bankingtaxdetails> bankingtaxdetailsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private List<Assignedemployees> assignedemployeesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeid")
    private List<Insuranceplans> insuranceplansList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private List<Employment> employmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hiringManager")
    private List<Vacancies> vacanciesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private List<Workexperience> workexperienceList;
    @JoinColumn(name = "gender", referencedColumnName = "idGender")
    @ManyToOne(optional = false)
    private Gender gender;
    @JoinColumn(name = "role", referencedColumnName = "idUserRole")
    @ManyToOne(optional = false)
    private Userrole role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private List<Attendance> attendanceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private List<Salarycomponent> salarycomponentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectAdmin")
    private List<Projectadmins> projectadminsList;

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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @XmlTransient
    public List<Education> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList;
    }

    @XmlTransient
    public List<Login> getLoginList() {
        return loginList;
    }

    public void setLoginList(List<Login> loginList) {
        this.loginList = loginList;
    }

    @XmlTransient
    public List<Contactdetails> getContactdetailsList() {
        return contactdetailsList;
    }

    public void setContactdetailsList(List<Contactdetails> contactdetailsList) {
        this.contactdetailsList = contactdetailsList;
    }

    @XmlTransient
    public List<Leave> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<Leave> leaveList) {
        this.leaveList = leaveList;
    }

    @XmlTransient
    public List<Leave> getLeaveList1() {
        return leaveList1;
    }

    public void setLeaveList1(List<Leave> leaveList1) {
        this.leaveList1 = leaveList1;
    }

    @XmlTransient
    public List<Bankingtaxdetails> getBankingtaxdetailsList() {
        return bankingtaxdetailsList;
    }

    public void setBankingtaxdetailsList(List<Bankingtaxdetails> bankingtaxdetailsList) {
        this.bankingtaxdetailsList = bankingtaxdetailsList;
    }

    @XmlTransient
    public List<Assignedemployees> getAssignedemployeesList() {
        return assignedemployeesList;
    }

    public void setAssignedemployeesList(List<Assignedemployees> assignedemployeesList) {
        this.assignedemployeesList = assignedemployeesList;
    }

    @XmlTransient
    public List<Insuranceplans> getInsuranceplansList() {
        return insuranceplansList;
    }

    public void setInsuranceplansList(List<Insuranceplans> insuranceplansList) {
        this.insuranceplansList = insuranceplansList;
    }

    @XmlTransient
    public List<Employment> getEmploymentList() {
        return employmentList;
    }

    public void setEmploymentList(List<Employment> employmentList) {
        this.employmentList = employmentList;
    }

    @XmlTransient
    public List<Vacancies> getVacanciesList() {
        return vacanciesList;
    }

    public void setVacanciesList(List<Vacancies> vacanciesList) {
        this.vacanciesList = vacanciesList;
    }

    @XmlTransient
    public List<Workexperience> getWorkexperienceList() {
        return workexperienceList;
    }

    public void setWorkexperienceList(List<Workexperience> workexperienceList) {
        this.workexperienceList = workexperienceList;
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
    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @XmlTransient
    public List<Salarycomponent> getSalarycomponentList() {
        return salarycomponentList;
    }

    public void setSalarycomponentList(List<Salarycomponent> salarycomponentList) {
        this.salarycomponentList = salarycomponentList;
    }

    @XmlTransient
    public List<Projectadmins> getProjectadminsList() {
        return projectadminsList;
    }

    public void setProjectadminsList(List<Projectadmins> projectadminsList) {
        this.projectadminsList = projectadminsList;
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
        return "Entitiy.Employees[ idEmployees=" + idEmployees + " ]";
    }
    
}
