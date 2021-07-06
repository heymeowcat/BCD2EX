/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Gender;
import Entity.Userrole;
import Entity.Education;
import java.util.ArrayList;
import java.util.List;
import Entity.Login;
import Entity.Contactdetails;
import Entity.Leave;
import Entity.Bankingtaxdetails;
import Entity.Assignedemployees;
import Entity.Insuranceplans;
import Entity.Employment;
import Entity.Vacancies;
import Entity.Workexperience;
import Entity.Attendance;
import Entity.Employees;
import Entity.Salarycomponent;
import Entity.Projectadmins;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class EmployeesJpaController implements Serializable {

    public EmployeesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employees employees) throws RollbackFailureException, Exception {
        if (employees.getEducationList() == null) {
            employees.setEducationList(new ArrayList<Education>());
        }
        if (employees.getLoginList() == null) {
            employees.setLoginList(new ArrayList<Login>());
        }
        if (employees.getContactdetailsList() == null) {
            employees.setContactdetailsList(new ArrayList<Contactdetails>());
        }
        if (employees.getLeaveList() == null) {
            employees.setLeaveList(new ArrayList<Leave>());
        }
        if (employees.getLeaveList1() == null) {
            employees.setLeaveList1(new ArrayList<Leave>());
        }
        if (employees.getBankingtaxdetailsList() == null) {
            employees.setBankingtaxdetailsList(new ArrayList<Bankingtaxdetails>());
        }
        if (employees.getAssignedemployeesList() == null) {
            employees.setAssignedemployeesList(new ArrayList<Assignedemployees>());
        }
        if (employees.getInsuranceplansList() == null) {
            employees.setInsuranceplansList(new ArrayList<Insuranceplans>());
        }
        if (employees.getEmploymentList() == null) {
            employees.setEmploymentList(new ArrayList<Employment>());
        }
        if (employees.getVacanciesList() == null) {
            employees.setVacanciesList(new ArrayList<Vacancies>());
        }
        if (employees.getWorkexperienceList() == null) {
            employees.setWorkexperienceList(new ArrayList<Workexperience>());
        }
        if (employees.getAttendanceList() == null) {
            employees.setAttendanceList(new ArrayList<Attendance>());
        }
        if (employees.getSalarycomponentList() == null) {
            employees.setSalarycomponentList(new ArrayList<Salarycomponent>());
        }
        if (employees.getProjectadminsList() == null) {
            employees.setProjectadminsList(new ArrayList<Projectadmins>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Gender gender = employees.getGender();
            if (gender != null) {
                gender = em.getReference(gender.getClass(), gender.getIdGender());
                employees.setGender(gender);
            }
            Userrole role = employees.getRole();
            if (role != null) {
                role = em.getReference(role.getClass(), role.getIdUserRole());
                employees.setRole(role);
            }
            List<Education> attachedEducationList = new ArrayList<Education>();
            for (Education educationListEducationToAttach : employees.getEducationList()) {
                educationListEducationToAttach = em.getReference(educationListEducationToAttach.getClass(), educationListEducationToAttach.getEducationPK());
                attachedEducationList.add(educationListEducationToAttach);
            }
            employees.setEducationList(attachedEducationList);
            List<Login> attachedLoginList = new ArrayList<Login>();
            for (Login loginListLoginToAttach : employees.getLoginList()) {
                loginListLoginToAttach = em.getReference(loginListLoginToAttach.getClass(), loginListLoginToAttach.getLoginPK());
                attachedLoginList.add(loginListLoginToAttach);
            }
            employees.setLoginList(attachedLoginList);
            List<Contactdetails> attachedContactdetailsList = new ArrayList<Contactdetails>();
            for (Contactdetails contactdetailsListContactdetailsToAttach : employees.getContactdetailsList()) {
                contactdetailsListContactdetailsToAttach = em.getReference(contactdetailsListContactdetailsToAttach.getClass(), contactdetailsListContactdetailsToAttach.getIdContactDetails());
                attachedContactdetailsList.add(contactdetailsListContactdetailsToAttach);
            }
            employees.setContactdetailsList(attachedContactdetailsList);
            List<Leave> attachedLeaveList = new ArrayList<Leave>();
            for (Leave leaveListLeaveToAttach : employees.getLeaveList()) {
                leaveListLeaveToAttach = em.getReference(leaveListLeaveToAttach.getClass(), leaveListLeaveToAttach.getIdLeave());
                attachedLeaveList.add(leaveListLeaveToAttach);
            }
            employees.setLeaveList(attachedLeaveList);
            List<Leave> attachedLeaveList1 = new ArrayList<Leave>();
            for (Leave leaveList1LeaveToAttach : employees.getLeaveList1()) {
                leaveList1LeaveToAttach = em.getReference(leaveList1LeaveToAttach.getClass(), leaveList1LeaveToAttach.getIdLeave());
                attachedLeaveList1.add(leaveList1LeaveToAttach);
            }
            employees.setLeaveList1(attachedLeaveList1);
            List<Bankingtaxdetails> attachedBankingtaxdetailsList = new ArrayList<Bankingtaxdetails>();
            for (Bankingtaxdetails bankingtaxdetailsListBankingtaxdetailsToAttach : employees.getBankingtaxdetailsList()) {
                bankingtaxdetailsListBankingtaxdetailsToAttach = em.getReference(bankingtaxdetailsListBankingtaxdetailsToAttach.getClass(), bankingtaxdetailsListBankingtaxdetailsToAttach.getIdBankingTaxDetails());
                attachedBankingtaxdetailsList.add(bankingtaxdetailsListBankingtaxdetailsToAttach);
            }
            employees.setBankingtaxdetailsList(attachedBankingtaxdetailsList);
            List<Assignedemployees> attachedAssignedemployeesList = new ArrayList<Assignedemployees>();
            for (Assignedemployees assignedemployeesListAssignedemployeesToAttach : employees.getAssignedemployeesList()) {
                assignedemployeesListAssignedemployeesToAttach = em.getReference(assignedemployeesListAssignedemployeesToAttach.getClass(), assignedemployeesListAssignedemployeesToAttach.getIdAssignedEmployees());
                attachedAssignedemployeesList.add(assignedemployeesListAssignedemployeesToAttach);
            }
            employees.setAssignedemployeesList(attachedAssignedemployeesList);
            List<Insuranceplans> attachedInsuranceplansList = new ArrayList<Insuranceplans>();
            for (Insuranceplans insuranceplansListInsuranceplansToAttach : employees.getInsuranceplansList()) {
                insuranceplansListInsuranceplansToAttach = em.getReference(insuranceplansListInsuranceplansToAttach.getClass(), insuranceplansListInsuranceplansToAttach.getIdInsurancePlans());
                attachedInsuranceplansList.add(insuranceplansListInsuranceplansToAttach);
            }
            employees.setInsuranceplansList(attachedInsuranceplansList);
            List<Employment> attachedEmploymentList = new ArrayList<Employment>();
            for (Employment employmentListEmploymentToAttach : employees.getEmploymentList()) {
                employmentListEmploymentToAttach = em.getReference(employmentListEmploymentToAttach.getClass(), employmentListEmploymentToAttach.getEmploymentPK());
                attachedEmploymentList.add(employmentListEmploymentToAttach);
            }
            employees.setEmploymentList(attachedEmploymentList);
            List<Vacancies> attachedVacanciesList = new ArrayList<Vacancies>();
            for (Vacancies vacanciesListVacanciesToAttach : employees.getVacanciesList()) {
                vacanciesListVacanciesToAttach = em.getReference(vacanciesListVacanciesToAttach.getClass(), vacanciesListVacanciesToAttach.getIdVacancies());
                attachedVacanciesList.add(vacanciesListVacanciesToAttach);
            }
            employees.setVacanciesList(attachedVacanciesList);
            List<Workexperience> attachedWorkexperienceList = new ArrayList<Workexperience>();
            for (Workexperience workexperienceListWorkexperienceToAttach : employees.getWorkexperienceList()) {
                workexperienceListWorkexperienceToAttach = em.getReference(workexperienceListWorkexperienceToAttach.getClass(), workexperienceListWorkexperienceToAttach.getWorkexperiencePK());
                attachedWorkexperienceList.add(workexperienceListWorkexperienceToAttach);
            }
            employees.setWorkexperienceList(attachedWorkexperienceList);
            List<Attendance> attachedAttendanceList = new ArrayList<Attendance>();
            for (Attendance attendanceListAttendanceToAttach : employees.getAttendanceList()) {
                attendanceListAttendanceToAttach = em.getReference(attendanceListAttendanceToAttach.getClass(), attendanceListAttendanceToAttach.getIdAttendance());
                attachedAttendanceList.add(attendanceListAttendanceToAttach);
            }
            employees.setAttendanceList(attachedAttendanceList);
            List<Salarycomponent> attachedSalarycomponentList = new ArrayList<Salarycomponent>();
            for (Salarycomponent salarycomponentListSalarycomponentToAttach : employees.getSalarycomponentList()) {
                salarycomponentListSalarycomponentToAttach = em.getReference(salarycomponentListSalarycomponentToAttach.getClass(), salarycomponentListSalarycomponentToAttach.getSalarycomponentPK());
                attachedSalarycomponentList.add(salarycomponentListSalarycomponentToAttach);
            }
            employees.setSalarycomponentList(attachedSalarycomponentList);
            List<Projectadmins> attachedProjectadminsList = new ArrayList<Projectadmins>();
            for (Projectadmins projectadminsListProjectadminsToAttach : employees.getProjectadminsList()) {
                projectadminsListProjectadminsToAttach = em.getReference(projectadminsListProjectadminsToAttach.getClass(), projectadminsListProjectadminsToAttach.getIdProjectAdmins());
                attachedProjectadminsList.add(projectadminsListProjectadminsToAttach);
            }
            employees.setProjectadminsList(attachedProjectadminsList);
            em.persist(employees);
            if (gender != null) {
                gender.getEmployeesList().add(employees);
                gender = em.merge(gender);
            }
            if (role != null) {
                role.getEmployeesList().add(employees);
                role = em.merge(role);
            }
            for (Education educationListEducation : employees.getEducationList()) {
                Employees oldEmployeesOfEducationListEducation = educationListEducation.getEmployees();
                educationListEducation.setEmployees(employees);
                educationListEducation = em.merge(educationListEducation);
                if (oldEmployeesOfEducationListEducation != null) {
                    oldEmployeesOfEducationListEducation.getEducationList().remove(educationListEducation);
                    oldEmployeesOfEducationListEducation = em.merge(oldEmployeesOfEducationListEducation);
                }
            }
            for (Login loginListLogin : employees.getLoginList()) {
                Employees oldEmployeesOfLoginListLogin = loginListLogin.getEmployees();
                loginListLogin.setEmployees(employees);
                loginListLogin = em.merge(loginListLogin);
                if (oldEmployeesOfLoginListLogin != null) {
                    oldEmployeesOfLoginListLogin.getLoginList().remove(loginListLogin);
                    oldEmployeesOfLoginListLogin = em.merge(oldEmployeesOfLoginListLogin);
                }
            }
            for (Contactdetails contactdetailsListContactdetails : employees.getContactdetailsList()) {
                Employees oldEmployeeIdOfContactdetailsListContactdetails = contactdetailsListContactdetails.getEmployeeId();
                contactdetailsListContactdetails.setEmployeeId(employees);
                contactdetailsListContactdetails = em.merge(contactdetailsListContactdetails);
                if (oldEmployeeIdOfContactdetailsListContactdetails != null) {
                    oldEmployeeIdOfContactdetailsListContactdetails.getContactdetailsList().remove(contactdetailsListContactdetails);
                    oldEmployeeIdOfContactdetailsListContactdetails = em.merge(oldEmployeeIdOfContactdetailsListContactdetails);
                }
            }
            for (Leave leaveListLeave : employees.getLeaveList()) {
                Employees oldEmployeeIdOfLeaveListLeave = leaveListLeave.getEmployeeId();
                leaveListLeave.setEmployeeId(employees);
                leaveListLeave = em.merge(leaveListLeave);
                if (oldEmployeeIdOfLeaveListLeave != null) {
                    oldEmployeeIdOfLeaveListLeave.getLeaveList().remove(leaveListLeave);
                    oldEmployeeIdOfLeaveListLeave = em.merge(oldEmployeeIdOfLeaveListLeave);
                }
            }
            for (Leave leaveList1Leave : employees.getLeaveList1()) {
                Employees oldAssignedByOfLeaveList1Leave = leaveList1Leave.getAssignedBy();
                leaveList1Leave.setAssignedBy(employees);
                leaveList1Leave = em.merge(leaveList1Leave);
                if (oldAssignedByOfLeaveList1Leave != null) {
                    oldAssignedByOfLeaveList1Leave.getLeaveList1().remove(leaveList1Leave);
                    oldAssignedByOfLeaveList1Leave = em.merge(oldAssignedByOfLeaveList1Leave);
                }
            }
            for (Bankingtaxdetails bankingtaxdetailsListBankingtaxdetails : employees.getBankingtaxdetailsList()) {
                Employees oldEmployeeidOfBankingtaxdetailsListBankingtaxdetails = bankingtaxdetailsListBankingtaxdetails.getEmployeeid();
                bankingtaxdetailsListBankingtaxdetails.setEmployeeid(employees);
                bankingtaxdetailsListBankingtaxdetails = em.merge(bankingtaxdetailsListBankingtaxdetails);
                if (oldEmployeeidOfBankingtaxdetailsListBankingtaxdetails != null) {
                    oldEmployeeidOfBankingtaxdetailsListBankingtaxdetails.getBankingtaxdetailsList().remove(bankingtaxdetailsListBankingtaxdetails);
                    oldEmployeeidOfBankingtaxdetailsListBankingtaxdetails = em.merge(oldEmployeeidOfBankingtaxdetailsListBankingtaxdetails);
                }
            }
            for (Assignedemployees assignedemployeesListAssignedemployees : employees.getAssignedemployeesList()) {
                Employees oldEmployeeIdOfAssignedemployeesListAssignedemployees = assignedemployeesListAssignedemployees.getEmployeeId();
                assignedemployeesListAssignedemployees.setEmployeeId(employees);
                assignedemployeesListAssignedemployees = em.merge(assignedemployeesListAssignedemployees);
                if (oldEmployeeIdOfAssignedemployeesListAssignedemployees != null) {
                    oldEmployeeIdOfAssignedemployeesListAssignedemployees.getAssignedemployeesList().remove(assignedemployeesListAssignedemployees);
                    oldEmployeeIdOfAssignedemployeesListAssignedemployees = em.merge(oldEmployeeIdOfAssignedemployeesListAssignedemployees);
                }
            }
            for (Insuranceplans insuranceplansListInsuranceplans : employees.getInsuranceplansList()) {
                Employees oldEmployeeidOfInsuranceplansListInsuranceplans = insuranceplansListInsuranceplans.getEmployeeid();
                insuranceplansListInsuranceplans.setEmployeeid(employees);
                insuranceplansListInsuranceplans = em.merge(insuranceplansListInsuranceplans);
                if (oldEmployeeidOfInsuranceplansListInsuranceplans != null) {
                    oldEmployeeidOfInsuranceplansListInsuranceplans.getInsuranceplansList().remove(insuranceplansListInsuranceplans);
                    oldEmployeeidOfInsuranceplansListInsuranceplans = em.merge(oldEmployeeidOfInsuranceplansListInsuranceplans);
                }
            }
            for (Employment employmentListEmployment : employees.getEmploymentList()) {
                Employees oldEmployeesOfEmploymentListEmployment = employmentListEmployment.getEmployees();
                employmentListEmployment.setEmployees(employees);
                employmentListEmployment = em.merge(employmentListEmployment);
                if (oldEmployeesOfEmploymentListEmployment != null) {
                    oldEmployeesOfEmploymentListEmployment.getEmploymentList().remove(employmentListEmployment);
                    oldEmployeesOfEmploymentListEmployment = em.merge(oldEmployeesOfEmploymentListEmployment);
                }
            }
            for (Vacancies vacanciesListVacancies : employees.getVacanciesList()) {
                Employees oldHiringManagerOfVacanciesListVacancies = vacanciesListVacancies.getHiringManager();
                vacanciesListVacancies.setHiringManager(employees);
                vacanciesListVacancies = em.merge(vacanciesListVacancies);
                if (oldHiringManagerOfVacanciesListVacancies != null) {
                    oldHiringManagerOfVacanciesListVacancies.getVacanciesList().remove(vacanciesListVacancies);
                    oldHiringManagerOfVacanciesListVacancies = em.merge(oldHiringManagerOfVacanciesListVacancies);
                }
            }
            for (Workexperience workexperienceListWorkexperience : employees.getWorkexperienceList()) {
                Employees oldEmployeesOfWorkexperienceListWorkexperience = workexperienceListWorkexperience.getEmployees();
                workexperienceListWorkexperience.setEmployees(employees);
                workexperienceListWorkexperience = em.merge(workexperienceListWorkexperience);
                if (oldEmployeesOfWorkexperienceListWorkexperience != null) {
                    oldEmployeesOfWorkexperienceListWorkexperience.getWorkexperienceList().remove(workexperienceListWorkexperience);
                    oldEmployeesOfWorkexperienceListWorkexperience = em.merge(oldEmployeesOfWorkexperienceListWorkexperience);
                }
            }
            for (Attendance attendanceListAttendance : employees.getAttendanceList()) {
                Employees oldEmployeeIdOfAttendanceListAttendance = attendanceListAttendance.getEmployeeId();
                attendanceListAttendance.setEmployeeId(employees);
                attendanceListAttendance = em.merge(attendanceListAttendance);
                if (oldEmployeeIdOfAttendanceListAttendance != null) {
                    oldEmployeeIdOfAttendanceListAttendance.getAttendanceList().remove(attendanceListAttendance);
                    oldEmployeeIdOfAttendanceListAttendance = em.merge(oldEmployeeIdOfAttendanceListAttendance);
                }
            }
            for (Salarycomponent salarycomponentListSalarycomponent : employees.getSalarycomponentList()) {
                Employees oldEmployeesOfSalarycomponentListSalarycomponent = salarycomponentListSalarycomponent.getEmployees();
                salarycomponentListSalarycomponent.setEmployees(employees);
                salarycomponentListSalarycomponent = em.merge(salarycomponentListSalarycomponent);
                if (oldEmployeesOfSalarycomponentListSalarycomponent != null) {
                    oldEmployeesOfSalarycomponentListSalarycomponent.getSalarycomponentList().remove(salarycomponentListSalarycomponent);
                    oldEmployeesOfSalarycomponentListSalarycomponent = em.merge(oldEmployeesOfSalarycomponentListSalarycomponent);
                }
            }
            for (Projectadmins projectadminsListProjectadmins : employees.getProjectadminsList()) {
                Employees oldProjectAdminOfProjectadminsListProjectadmins = projectadminsListProjectadmins.getProjectAdmin();
                projectadminsListProjectadmins.setProjectAdmin(employees);
                projectadminsListProjectadmins = em.merge(projectadminsListProjectadmins);
                if (oldProjectAdminOfProjectadminsListProjectadmins != null) {
                    oldProjectAdminOfProjectadminsListProjectadmins.getProjectadminsList().remove(projectadminsListProjectadmins);
                    oldProjectAdminOfProjectadminsListProjectadmins = em.merge(oldProjectAdminOfProjectadminsListProjectadmins);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employees employees) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees persistentEmployees = em.find(Employees.class, employees.getIdEmployees());
            Gender genderOld = persistentEmployees.getGender();
            Gender genderNew = employees.getGender();
            Userrole roleOld = persistentEmployees.getRole();
            Userrole roleNew = employees.getRole();
            List<Education> educationListOld = persistentEmployees.getEducationList();
            List<Education> educationListNew = employees.getEducationList();
            List<Login> loginListOld = persistentEmployees.getLoginList();
            List<Login> loginListNew = employees.getLoginList();
            List<Contactdetails> contactdetailsListOld = persistentEmployees.getContactdetailsList();
            List<Contactdetails> contactdetailsListNew = employees.getContactdetailsList();
            List<Leave> leaveListOld = persistentEmployees.getLeaveList();
            List<Leave> leaveListNew = employees.getLeaveList();
            List<Leave> leaveList1Old = persistentEmployees.getLeaveList1();
            List<Leave> leaveList1New = employees.getLeaveList1();
            List<Bankingtaxdetails> bankingtaxdetailsListOld = persistentEmployees.getBankingtaxdetailsList();
            List<Bankingtaxdetails> bankingtaxdetailsListNew = employees.getBankingtaxdetailsList();
            List<Assignedemployees> assignedemployeesListOld = persistentEmployees.getAssignedemployeesList();
            List<Assignedemployees> assignedemployeesListNew = employees.getAssignedemployeesList();
            List<Insuranceplans> insuranceplansListOld = persistentEmployees.getInsuranceplansList();
            List<Insuranceplans> insuranceplansListNew = employees.getInsuranceplansList();
            List<Employment> employmentListOld = persistentEmployees.getEmploymentList();
            List<Employment> employmentListNew = employees.getEmploymentList();
            List<Vacancies> vacanciesListOld = persistentEmployees.getVacanciesList();
            List<Vacancies> vacanciesListNew = employees.getVacanciesList();
            List<Workexperience> workexperienceListOld = persistentEmployees.getWorkexperienceList();
            List<Workexperience> workexperienceListNew = employees.getWorkexperienceList();
            List<Attendance> attendanceListOld = persistentEmployees.getAttendanceList();
            List<Attendance> attendanceListNew = employees.getAttendanceList();
            List<Salarycomponent> salarycomponentListOld = persistentEmployees.getSalarycomponentList();
            List<Salarycomponent> salarycomponentListNew = employees.getSalarycomponentList();
            List<Projectadmins> projectadminsListOld = persistentEmployees.getProjectadminsList();
            List<Projectadmins> projectadminsListNew = employees.getProjectadminsList();
            List<String> illegalOrphanMessages = null;
            for (Education educationListOldEducation : educationListOld) {
                if (!educationListNew.contains(educationListOldEducation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Education " + educationListOldEducation + " since its employees field is not nullable.");
                }
            }
            for (Login loginListOldLogin : loginListOld) {
                if (!loginListNew.contains(loginListOldLogin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Login " + loginListOldLogin + " since its employees field is not nullable.");
                }
            }
            for (Contactdetails contactdetailsListOldContactdetails : contactdetailsListOld) {
                if (!contactdetailsListNew.contains(contactdetailsListOldContactdetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contactdetails " + contactdetailsListOldContactdetails + " since its employeeId field is not nullable.");
                }
            }
            for (Leave leaveListOldLeave : leaveListOld) {
                if (!leaveListNew.contains(leaveListOldLeave)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Leave " + leaveListOldLeave + " since its employeeId field is not nullable.");
                }
            }
            for (Leave leaveList1OldLeave : leaveList1Old) {
                if (!leaveList1New.contains(leaveList1OldLeave)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Leave " + leaveList1OldLeave + " since its assignedBy field is not nullable.");
                }
            }
            for (Bankingtaxdetails bankingtaxdetailsListOldBankingtaxdetails : bankingtaxdetailsListOld) {
                if (!bankingtaxdetailsListNew.contains(bankingtaxdetailsListOldBankingtaxdetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bankingtaxdetails " + bankingtaxdetailsListOldBankingtaxdetails + " since its employeeid field is not nullable.");
                }
            }
            for (Assignedemployees assignedemployeesListOldAssignedemployees : assignedemployeesListOld) {
                if (!assignedemployeesListNew.contains(assignedemployeesListOldAssignedemployees)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Assignedemployees " + assignedemployeesListOldAssignedemployees + " since its employeeId field is not nullable.");
                }
            }
            for (Insuranceplans insuranceplansListOldInsuranceplans : insuranceplansListOld) {
                if (!insuranceplansListNew.contains(insuranceplansListOldInsuranceplans)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Insuranceplans " + insuranceplansListOldInsuranceplans + " since its employeeid field is not nullable.");
                }
            }
            for (Employment employmentListOldEmployment : employmentListOld) {
                if (!employmentListNew.contains(employmentListOldEmployment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employment " + employmentListOldEmployment + " since its employees field is not nullable.");
                }
            }
            for (Vacancies vacanciesListOldVacancies : vacanciesListOld) {
                if (!vacanciesListNew.contains(vacanciesListOldVacancies)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vacancies " + vacanciesListOldVacancies + " since its hiringManager field is not nullable.");
                }
            }
            for (Workexperience workexperienceListOldWorkexperience : workexperienceListOld) {
                if (!workexperienceListNew.contains(workexperienceListOldWorkexperience)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Workexperience " + workexperienceListOldWorkexperience + " since its employees field is not nullable.");
                }
            }
            for (Attendance attendanceListOldAttendance : attendanceListOld) {
                if (!attendanceListNew.contains(attendanceListOldAttendance)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Attendance " + attendanceListOldAttendance + " since its employeeId field is not nullable.");
                }
            }
            for (Salarycomponent salarycomponentListOldSalarycomponent : salarycomponentListOld) {
                if (!salarycomponentListNew.contains(salarycomponentListOldSalarycomponent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Salarycomponent " + salarycomponentListOldSalarycomponent + " since its employees field is not nullable.");
                }
            }
            for (Projectadmins projectadminsListOldProjectadmins : projectadminsListOld) {
                if (!projectadminsListNew.contains(projectadminsListOldProjectadmins)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Projectadmins " + projectadminsListOldProjectadmins + " since its projectAdmin field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (genderNew != null) {
                genderNew = em.getReference(genderNew.getClass(), genderNew.getIdGender());
                employees.setGender(genderNew);
            }
            if (roleNew != null) {
                roleNew = em.getReference(roleNew.getClass(), roleNew.getIdUserRole());
                employees.setRole(roleNew);
            }
            List<Education> attachedEducationListNew = new ArrayList<Education>();
            for (Education educationListNewEducationToAttach : educationListNew) {
                educationListNewEducationToAttach = em.getReference(educationListNewEducationToAttach.getClass(), educationListNewEducationToAttach.getEducationPK());
                attachedEducationListNew.add(educationListNewEducationToAttach);
            }
            educationListNew = attachedEducationListNew;
            employees.setEducationList(educationListNew);
            List<Login> attachedLoginListNew = new ArrayList<Login>();
            for (Login loginListNewLoginToAttach : loginListNew) {
                loginListNewLoginToAttach = em.getReference(loginListNewLoginToAttach.getClass(), loginListNewLoginToAttach.getLoginPK());
                attachedLoginListNew.add(loginListNewLoginToAttach);
            }
            loginListNew = attachedLoginListNew;
            employees.setLoginList(loginListNew);
            List<Contactdetails> attachedContactdetailsListNew = new ArrayList<Contactdetails>();
            for (Contactdetails contactdetailsListNewContactdetailsToAttach : contactdetailsListNew) {
                contactdetailsListNewContactdetailsToAttach = em.getReference(contactdetailsListNewContactdetailsToAttach.getClass(), contactdetailsListNewContactdetailsToAttach.getIdContactDetails());
                attachedContactdetailsListNew.add(contactdetailsListNewContactdetailsToAttach);
            }
            contactdetailsListNew = attachedContactdetailsListNew;
            employees.setContactdetailsList(contactdetailsListNew);
            List<Leave> attachedLeaveListNew = new ArrayList<Leave>();
            for (Leave leaveListNewLeaveToAttach : leaveListNew) {
                leaveListNewLeaveToAttach = em.getReference(leaveListNewLeaveToAttach.getClass(), leaveListNewLeaveToAttach.getIdLeave());
                attachedLeaveListNew.add(leaveListNewLeaveToAttach);
            }
            leaveListNew = attachedLeaveListNew;
            employees.setLeaveList(leaveListNew);
            List<Leave> attachedLeaveList1New = new ArrayList<Leave>();
            for (Leave leaveList1NewLeaveToAttach : leaveList1New) {
                leaveList1NewLeaveToAttach = em.getReference(leaveList1NewLeaveToAttach.getClass(), leaveList1NewLeaveToAttach.getIdLeave());
                attachedLeaveList1New.add(leaveList1NewLeaveToAttach);
            }
            leaveList1New = attachedLeaveList1New;
            employees.setLeaveList1(leaveList1New);
            List<Bankingtaxdetails> attachedBankingtaxdetailsListNew = new ArrayList<Bankingtaxdetails>();
            for (Bankingtaxdetails bankingtaxdetailsListNewBankingtaxdetailsToAttach : bankingtaxdetailsListNew) {
                bankingtaxdetailsListNewBankingtaxdetailsToAttach = em.getReference(bankingtaxdetailsListNewBankingtaxdetailsToAttach.getClass(), bankingtaxdetailsListNewBankingtaxdetailsToAttach.getIdBankingTaxDetails());
                attachedBankingtaxdetailsListNew.add(bankingtaxdetailsListNewBankingtaxdetailsToAttach);
            }
            bankingtaxdetailsListNew = attachedBankingtaxdetailsListNew;
            employees.setBankingtaxdetailsList(bankingtaxdetailsListNew);
            List<Assignedemployees> attachedAssignedemployeesListNew = new ArrayList<Assignedemployees>();
            for (Assignedemployees assignedemployeesListNewAssignedemployeesToAttach : assignedemployeesListNew) {
                assignedemployeesListNewAssignedemployeesToAttach = em.getReference(assignedemployeesListNewAssignedemployeesToAttach.getClass(), assignedemployeesListNewAssignedemployeesToAttach.getIdAssignedEmployees());
                attachedAssignedemployeesListNew.add(assignedemployeesListNewAssignedemployeesToAttach);
            }
            assignedemployeesListNew = attachedAssignedemployeesListNew;
            employees.setAssignedemployeesList(assignedemployeesListNew);
            List<Insuranceplans> attachedInsuranceplansListNew = new ArrayList<Insuranceplans>();
            for (Insuranceplans insuranceplansListNewInsuranceplansToAttach : insuranceplansListNew) {
                insuranceplansListNewInsuranceplansToAttach = em.getReference(insuranceplansListNewInsuranceplansToAttach.getClass(), insuranceplansListNewInsuranceplansToAttach.getIdInsurancePlans());
                attachedInsuranceplansListNew.add(insuranceplansListNewInsuranceplansToAttach);
            }
            insuranceplansListNew = attachedInsuranceplansListNew;
            employees.setInsuranceplansList(insuranceplansListNew);
            List<Employment> attachedEmploymentListNew = new ArrayList<Employment>();
            for (Employment employmentListNewEmploymentToAttach : employmentListNew) {
                employmentListNewEmploymentToAttach = em.getReference(employmentListNewEmploymentToAttach.getClass(), employmentListNewEmploymentToAttach.getEmploymentPK());
                attachedEmploymentListNew.add(employmentListNewEmploymentToAttach);
            }
            employmentListNew = attachedEmploymentListNew;
            employees.setEmploymentList(employmentListNew);
            List<Vacancies> attachedVacanciesListNew = new ArrayList<Vacancies>();
            for (Vacancies vacanciesListNewVacanciesToAttach : vacanciesListNew) {
                vacanciesListNewVacanciesToAttach = em.getReference(vacanciesListNewVacanciesToAttach.getClass(), vacanciesListNewVacanciesToAttach.getIdVacancies());
                attachedVacanciesListNew.add(vacanciesListNewVacanciesToAttach);
            }
            vacanciesListNew = attachedVacanciesListNew;
            employees.setVacanciesList(vacanciesListNew);
            List<Workexperience> attachedWorkexperienceListNew = new ArrayList<Workexperience>();
            for (Workexperience workexperienceListNewWorkexperienceToAttach : workexperienceListNew) {
                workexperienceListNewWorkexperienceToAttach = em.getReference(workexperienceListNewWorkexperienceToAttach.getClass(), workexperienceListNewWorkexperienceToAttach.getWorkexperiencePK());
                attachedWorkexperienceListNew.add(workexperienceListNewWorkexperienceToAttach);
            }
            workexperienceListNew = attachedWorkexperienceListNew;
            employees.setWorkexperienceList(workexperienceListNew);
            List<Attendance> attachedAttendanceListNew = new ArrayList<Attendance>();
            for (Attendance attendanceListNewAttendanceToAttach : attendanceListNew) {
                attendanceListNewAttendanceToAttach = em.getReference(attendanceListNewAttendanceToAttach.getClass(), attendanceListNewAttendanceToAttach.getIdAttendance());
                attachedAttendanceListNew.add(attendanceListNewAttendanceToAttach);
            }
            attendanceListNew = attachedAttendanceListNew;
            employees.setAttendanceList(attendanceListNew);
            List<Salarycomponent> attachedSalarycomponentListNew = new ArrayList<Salarycomponent>();
            for (Salarycomponent salarycomponentListNewSalarycomponentToAttach : salarycomponentListNew) {
                salarycomponentListNewSalarycomponentToAttach = em.getReference(salarycomponentListNewSalarycomponentToAttach.getClass(), salarycomponentListNewSalarycomponentToAttach.getSalarycomponentPK());
                attachedSalarycomponentListNew.add(salarycomponentListNewSalarycomponentToAttach);
            }
            salarycomponentListNew = attachedSalarycomponentListNew;
            employees.setSalarycomponentList(salarycomponentListNew);
            List<Projectadmins> attachedProjectadminsListNew = new ArrayList<Projectadmins>();
            for (Projectadmins projectadminsListNewProjectadminsToAttach : projectadminsListNew) {
                projectadminsListNewProjectadminsToAttach = em.getReference(projectadminsListNewProjectadminsToAttach.getClass(), projectadminsListNewProjectadminsToAttach.getIdProjectAdmins());
                attachedProjectadminsListNew.add(projectadminsListNewProjectadminsToAttach);
            }
            projectadminsListNew = attachedProjectadminsListNew;
            employees.setProjectadminsList(projectadminsListNew);
            employees = em.merge(employees);
            if (genderOld != null && !genderOld.equals(genderNew)) {
                genderOld.getEmployeesList().remove(employees);
                genderOld = em.merge(genderOld);
            }
            if (genderNew != null && !genderNew.equals(genderOld)) {
                genderNew.getEmployeesList().add(employees);
                genderNew = em.merge(genderNew);
            }
            if (roleOld != null && !roleOld.equals(roleNew)) {
                roleOld.getEmployeesList().remove(employees);
                roleOld = em.merge(roleOld);
            }
            if (roleNew != null && !roleNew.equals(roleOld)) {
                roleNew.getEmployeesList().add(employees);
                roleNew = em.merge(roleNew);
            }
            for (Education educationListNewEducation : educationListNew) {
                if (!educationListOld.contains(educationListNewEducation)) {
                    Employees oldEmployeesOfEducationListNewEducation = educationListNewEducation.getEmployees();
                    educationListNewEducation.setEmployees(employees);
                    educationListNewEducation = em.merge(educationListNewEducation);
                    if (oldEmployeesOfEducationListNewEducation != null && !oldEmployeesOfEducationListNewEducation.equals(employees)) {
                        oldEmployeesOfEducationListNewEducation.getEducationList().remove(educationListNewEducation);
                        oldEmployeesOfEducationListNewEducation = em.merge(oldEmployeesOfEducationListNewEducation);
                    }
                }
            }
            for (Login loginListNewLogin : loginListNew) {
                if (!loginListOld.contains(loginListNewLogin)) {
                    Employees oldEmployeesOfLoginListNewLogin = loginListNewLogin.getEmployees();
                    loginListNewLogin.setEmployees(employees);
                    loginListNewLogin = em.merge(loginListNewLogin);
                    if (oldEmployeesOfLoginListNewLogin != null && !oldEmployeesOfLoginListNewLogin.equals(employees)) {
                        oldEmployeesOfLoginListNewLogin.getLoginList().remove(loginListNewLogin);
                        oldEmployeesOfLoginListNewLogin = em.merge(oldEmployeesOfLoginListNewLogin);
                    }
                }
            }
            for (Contactdetails contactdetailsListNewContactdetails : contactdetailsListNew) {
                if (!contactdetailsListOld.contains(contactdetailsListNewContactdetails)) {
                    Employees oldEmployeeIdOfContactdetailsListNewContactdetails = contactdetailsListNewContactdetails.getEmployeeId();
                    contactdetailsListNewContactdetails.setEmployeeId(employees);
                    contactdetailsListNewContactdetails = em.merge(contactdetailsListNewContactdetails);
                    if (oldEmployeeIdOfContactdetailsListNewContactdetails != null && !oldEmployeeIdOfContactdetailsListNewContactdetails.equals(employees)) {
                        oldEmployeeIdOfContactdetailsListNewContactdetails.getContactdetailsList().remove(contactdetailsListNewContactdetails);
                        oldEmployeeIdOfContactdetailsListNewContactdetails = em.merge(oldEmployeeIdOfContactdetailsListNewContactdetails);
                    }
                }
            }
            for (Leave leaveListNewLeave : leaveListNew) {
                if (!leaveListOld.contains(leaveListNewLeave)) {
                    Employees oldEmployeeIdOfLeaveListNewLeave = leaveListNewLeave.getEmployeeId();
                    leaveListNewLeave.setEmployeeId(employees);
                    leaveListNewLeave = em.merge(leaveListNewLeave);
                    if (oldEmployeeIdOfLeaveListNewLeave != null && !oldEmployeeIdOfLeaveListNewLeave.equals(employees)) {
                        oldEmployeeIdOfLeaveListNewLeave.getLeaveList().remove(leaveListNewLeave);
                        oldEmployeeIdOfLeaveListNewLeave = em.merge(oldEmployeeIdOfLeaveListNewLeave);
                    }
                }
            }
            for (Leave leaveList1NewLeave : leaveList1New) {
                if (!leaveList1Old.contains(leaveList1NewLeave)) {
                    Employees oldAssignedByOfLeaveList1NewLeave = leaveList1NewLeave.getAssignedBy();
                    leaveList1NewLeave.setAssignedBy(employees);
                    leaveList1NewLeave = em.merge(leaveList1NewLeave);
                    if (oldAssignedByOfLeaveList1NewLeave != null && !oldAssignedByOfLeaveList1NewLeave.equals(employees)) {
                        oldAssignedByOfLeaveList1NewLeave.getLeaveList1().remove(leaveList1NewLeave);
                        oldAssignedByOfLeaveList1NewLeave = em.merge(oldAssignedByOfLeaveList1NewLeave);
                    }
                }
            }
            for (Bankingtaxdetails bankingtaxdetailsListNewBankingtaxdetails : bankingtaxdetailsListNew) {
                if (!bankingtaxdetailsListOld.contains(bankingtaxdetailsListNewBankingtaxdetails)) {
                    Employees oldEmployeeidOfBankingtaxdetailsListNewBankingtaxdetails = bankingtaxdetailsListNewBankingtaxdetails.getEmployeeid();
                    bankingtaxdetailsListNewBankingtaxdetails.setEmployeeid(employees);
                    bankingtaxdetailsListNewBankingtaxdetails = em.merge(bankingtaxdetailsListNewBankingtaxdetails);
                    if (oldEmployeeidOfBankingtaxdetailsListNewBankingtaxdetails != null && !oldEmployeeidOfBankingtaxdetailsListNewBankingtaxdetails.equals(employees)) {
                        oldEmployeeidOfBankingtaxdetailsListNewBankingtaxdetails.getBankingtaxdetailsList().remove(bankingtaxdetailsListNewBankingtaxdetails);
                        oldEmployeeidOfBankingtaxdetailsListNewBankingtaxdetails = em.merge(oldEmployeeidOfBankingtaxdetailsListNewBankingtaxdetails);
                    }
                }
            }
            for (Assignedemployees assignedemployeesListNewAssignedemployees : assignedemployeesListNew) {
                if (!assignedemployeesListOld.contains(assignedemployeesListNewAssignedemployees)) {
                    Employees oldEmployeeIdOfAssignedemployeesListNewAssignedemployees = assignedemployeesListNewAssignedemployees.getEmployeeId();
                    assignedemployeesListNewAssignedemployees.setEmployeeId(employees);
                    assignedemployeesListNewAssignedemployees = em.merge(assignedemployeesListNewAssignedemployees);
                    if (oldEmployeeIdOfAssignedemployeesListNewAssignedemployees != null && !oldEmployeeIdOfAssignedemployeesListNewAssignedemployees.equals(employees)) {
                        oldEmployeeIdOfAssignedemployeesListNewAssignedemployees.getAssignedemployeesList().remove(assignedemployeesListNewAssignedemployees);
                        oldEmployeeIdOfAssignedemployeesListNewAssignedemployees = em.merge(oldEmployeeIdOfAssignedemployeesListNewAssignedemployees);
                    }
                }
            }
            for (Insuranceplans insuranceplansListNewInsuranceplans : insuranceplansListNew) {
                if (!insuranceplansListOld.contains(insuranceplansListNewInsuranceplans)) {
                    Employees oldEmployeeidOfInsuranceplansListNewInsuranceplans = insuranceplansListNewInsuranceplans.getEmployeeid();
                    insuranceplansListNewInsuranceplans.setEmployeeid(employees);
                    insuranceplansListNewInsuranceplans = em.merge(insuranceplansListNewInsuranceplans);
                    if (oldEmployeeidOfInsuranceplansListNewInsuranceplans != null && !oldEmployeeidOfInsuranceplansListNewInsuranceplans.equals(employees)) {
                        oldEmployeeidOfInsuranceplansListNewInsuranceplans.getInsuranceplansList().remove(insuranceplansListNewInsuranceplans);
                        oldEmployeeidOfInsuranceplansListNewInsuranceplans = em.merge(oldEmployeeidOfInsuranceplansListNewInsuranceplans);
                    }
                }
            }
            for (Employment employmentListNewEmployment : employmentListNew) {
                if (!employmentListOld.contains(employmentListNewEmployment)) {
                    Employees oldEmployeesOfEmploymentListNewEmployment = employmentListNewEmployment.getEmployees();
                    employmentListNewEmployment.setEmployees(employees);
                    employmentListNewEmployment = em.merge(employmentListNewEmployment);
                    if (oldEmployeesOfEmploymentListNewEmployment != null && !oldEmployeesOfEmploymentListNewEmployment.equals(employees)) {
                        oldEmployeesOfEmploymentListNewEmployment.getEmploymentList().remove(employmentListNewEmployment);
                        oldEmployeesOfEmploymentListNewEmployment = em.merge(oldEmployeesOfEmploymentListNewEmployment);
                    }
                }
            }
            for (Vacancies vacanciesListNewVacancies : vacanciesListNew) {
                if (!vacanciesListOld.contains(vacanciesListNewVacancies)) {
                    Employees oldHiringManagerOfVacanciesListNewVacancies = vacanciesListNewVacancies.getHiringManager();
                    vacanciesListNewVacancies.setHiringManager(employees);
                    vacanciesListNewVacancies = em.merge(vacanciesListNewVacancies);
                    if (oldHiringManagerOfVacanciesListNewVacancies != null && !oldHiringManagerOfVacanciesListNewVacancies.equals(employees)) {
                        oldHiringManagerOfVacanciesListNewVacancies.getVacanciesList().remove(vacanciesListNewVacancies);
                        oldHiringManagerOfVacanciesListNewVacancies = em.merge(oldHiringManagerOfVacanciesListNewVacancies);
                    }
                }
            }
            for (Workexperience workexperienceListNewWorkexperience : workexperienceListNew) {
                if (!workexperienceListOld.contains(workexperienceListNewWorkexperience)) {
                    Employees oldEmployeesOfWorkexperienceListNewWorkexperience = workexperienceListNewWorkexperience.getEmployees();
                    workexperienceListNewWorkexperience.setEmployees(employees);
                    workexperienceListNewWorkexperience = em.merge(workexperienceListNewWorkexperience);
                    if (oldEmployeesOfWorkexperienceListNewWorkexperience != null && !oldEmployeesOfWorkexperienceListNewWorkexperience.equals(employees)) {
                        oldEmployeesOfWorkexperienceListNewWorkexperience.getWorkexperienceList().remove(workexperienceListNewWorkexperience);
                        oldEmployeesOfWorkexperienceListNewWorkexperience = em.merge(oldEmployeesOfWorkexperienceListNewWorkexperience);
                    }
                }
            }
            for (Attendance attendanceListNewAttendance : attendanceListNew) {
                if (!attendanceListOld.contains(attendanceListNewAttendance)) {
                    Employees oldEmployeeIdOfAttendanceListNewAttendance = attendanceListNewAttendance.getEmployeeId();
                    attendanceListNewAttendance.setEmployeeId(employees);
                    attendanceListNewAttendance = em.merge(attendanceListNewAttendance);
                    if (oldEmployeeIdOfAttendanceListNewAttendance != null && !oldEmployeeIdOfAttendanceListNewAttendance.equals(employees)) {
                        oldEmployeeIdOfAttendanceListNewAttendance.getAttendanceList().remove(attendanceListNewAttendance);
                        oldEmployeeIdOfAttendanceListNewAttendance = em.merge(oldEmployeeIdOfAttendanceListNewAttendance);
                    }
                }
            }
            for (Salarycomponent salarycomponentListNewSalarycomponent : salarycomponentListNew) {
                if (!salarycomponentListOld.contains(salarycomponentListNewSalarycomponent)) {
                    Employees oldEmployeesOfSalarycomponentListNewSalarycomponent = salarycomponentListNewSalarycomponent.getEmployees();
                    salarycomponentListNewSalarycomponent.setEmployees(employees);
                    salarycomponentListNewSalarycomponent = em.merge(salarycomponentListNewSalarycomponent);
                    if (oldEmployeesOfSalarycomponentListNewSalarycomponent != null && !oldEmployeesOfSalarycomponentListNewSalarycomponent.equals(employees)) {
                        oldEmployeesOfSalarycomponentListNewSalarycomponent.getSalarycomponentList().remove(salarycomponentListNewSalarycomponent);
                        oldEmployeesOfSalarycomponentListNewSalarycomponent = em.merge(oldEmployeesOfSalarycomponentListNewSalarycomponent);
                    }
                }
            }
            for (Projectadmins projectadminsListNewProjectadmins : projectadminsListNew) {
                if (!projectadminsListOld.contains(projectadminsListNewProjectadmins)) {
                    Employees oldProjectAdminOfProjectadminsListNewProjectadmins = projectadminsListNewProjectadmins.getProjectAdmin();
                    projectadminsListNewProjectadmins.setProjectAdmin(employees);
                    projectadminsListNewProjectadmins = em.merge(projectadminsListNewProjectadmins);
                    if (oldProjectAdminOfProjectadminsListNewProjectadmins != null && !oldProjectAdminOfProjectadminsListNewProjectadmins.equals(employees)) {
                        oldProjectAdminOfProjectadminsListNewProjectadmins.getProjectadminsList().remove(projectadminsListNewProjectadmins);
                        oldProjectAdminOfProjectadminsListNewProjectadmins = em.merge(oldProjectAdminOfProjectadminsListNewProjectadmins);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = employees.getIdEmployees();
                if (findEmployees(id) == null) {
                    throw new NonexistentEntityException("The employees with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees employees;
            try {
                employees = em.getReference(Employees.class, id);
                employees.getIdEmployees();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employees with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Education> educationListOrphanCheck = employees.getEducationList();
            for (Education educationListOrphanCheckEducation : educationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Education " + educationListOrphanCheckEducation + " in its educationList field has a non-nullable employees field.");
            }
            List<Login> loginListOrphanCheck = employees.getLoginList();
            for (Login loginListOrphanCheckLogin : loginListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Login " + loginListOrphanCheckLogin + " in its loginList field has a non-nullable employees field.");
            }
            List<Contactdetails> contactdetailsListOrphanCheck = employees.getContactdetailsList();
            for (Contactdetails contactdetailsListOrphanCheckContactdetails : contactdetailsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Contactdetails " + contactdetailsListOrphanCheckContactdetails + " in its contactdetailsList field has a non-nullable employeeId field.");
            }
            List<Leave> leaveListOrphanCheck = employees.getLeaveList();
            for (Leave leaveListOrphanCheckLeave : leaveListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Leave " + leaveListOrphanCheckLeave + " in its leaveList field has a non-nullable employeeId field.");
            }
            List<Leave> leaveList1OrphanCheck = employees.getLeaveList1();
            for (Leave leaveList1OrphanCheckLeave : leaveList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Leave " + leaveList1OrphanCheckLeave + " in its leaveList1 field has a non-nullable assignedBy field.");
            }
            List<Bankingtaxdetails> bankingtaxdetailsListOrphanCheck = employees.getBankingtaxdetailsList();
            for (Bankingtaxdetails bankingtaxdetailsListOrphanCheckBankingtaxdetails : bankingtaxdetailsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Bankingtaxdetails " + bankingtaxdetailsListOrphanCheckBankingtaxdetails + " in its bankingtaxdetailsList field has a non-nullable employeeid field.");
            }
            List<Assignedemployees> assignedemployeesListOrphanCheck = employees.getAssignedemployeesList();
            for (Assignedemployees assignedemployeesListOrphanCheckAssignedemployees : assignedemployeesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Assignedemployees " + assignedemployeesListOrphanCheckAssignedemployees + " in its assignedemployeesList field has a non-nullable employeeId field.");
            }
            List<Insuranceplans> insuranceplansListOrphanCheck = employees.getInsuranceplansList();
            for (Insuranceplans insuranceplansListOrphanCheckInsuranceplans : insuranceplansListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Insuranceplans " + insuranceplansListOrphanCheckInsuranceplans + " in its insuranceplansList field has a non-nullable employeeid field.");
            }
            List<Employment> employmentListOrphanCheck = employees.getEmploymentList();
            for (Employment employmentListOrphanCheckEmployment : employmentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Employment " + employmentListOrphanCheckEmployment + " in its employmentList field has a non-nullable employees field.");
            }
            List<Vacancies> vacanciesListOrphanCheck = employees.getVacanciesList();
            for (Vacancies vacanciesListOrphanCheckVacancies : vacanciesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Vacancies " + vacanciesListOrphanCheckVacancies + " in its vacanciesList field has a non-nullable hiringManager field.");
            }
            List<Workexperience> workexperienceListOrphanCheck = employees.getWorkexperienceList();
            for (Workexperience workexperienceListOrphanCheckWorkexperience : workexperienceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Workexperience " + workexperienceListOrphanCheckWorkexperience + " in its workexperienceList field has a non-nullable employees field.");
            }
            List<Attendance> attendanceListOrphanCheck = employees.getAttendanceList();
            for (Attendance attendanceListOrphanCheckAttendance : attendanceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Attendance " + attendanceListOrphanCheckAttendance + " in its attendanceList field has a non-nullable employeeId field.");
            }
            List<Salarycomponent> salarycomponentListOrphanCheck = employees.getSalarycomponentList();
            for (Salarycomponent salarycomponentListOrphanCheckSalarycomponent : salarycomponentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Salarycomponent " + salarycomponentListOrphanCheckSalarycomponent + " in its salarycomponentList field has a non-nullable employees field.");
            }
            List<Projectadmins> projectadminsListOrphanCheck = employees.getProjectadminsList();
            for (Projectadmins projectadminsListOrphanCheckProjectadmins : projectadminsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Projectadmins " + projectadminsListOrphanCheckProjectadmins + " in its projectadminsList field has a non-nullable projectAdmin field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Gender gender = employees.getGender();
            if (gender != null) {
                gender.getEmployeesList().remove(employees);
                gender = em.merge(gender);
            }
            Userrole role = employees.getRole();
            if (role != null) {
                role.getEmployeesList().remove(employees);
                role = em.merge(role);
            }
            em.remove(employees);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employees> findEmployeesEntities() {
        return findEmployeesEntities(true, -1, -1);
    }

    public List<Employees> findEmployeesEntities(int maxResults, int firstResult) {
        return findEmployeesEntities(false, maxResults, firstResult);
    }

    private List<Employees> findEmployeesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employees.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Employees findEmployees(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employees.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employees> rt = cq.from(Employees.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
