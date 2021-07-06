/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Bankingtaxdetails;
import Entity.Employees;
import Entity.SalaryHistory;
import Entity.Salarycomponent;
import Entity.SalarycomponentPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class SalarycomponentJpaController implements Serializable {

    public SalarycomponentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Salarycomponent salarycomponent) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (salarycomponent.getSalarycomponentPK() == null) {
            salarycomponent.setSalarycomponentPK(new SalarycomponentPK());
        }
        if (salarycomponent.getSalaryHistoryList() == null) {
            salarycomponent.setSalaryHistoryList(new ArrayList<SalaryHistory>());
        }
        salarycomponent.getSalarycomponentPK().setEmployeeId(salarycomponent.getEmployees().getIdEmployees());
        salarycomponent.getSalarycomponentPK().setDepositDetails(salarycomponent.getBankingtaxdetails().getIdBankingTaxDetails());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Bankingtaxdetails bankingtaxdetails = salarycomponent.getBankingtaxdetails();
            if (bankingtaxdetails != null) {
                bankingtaxdetails = em.getReference(bankingtaxdetails.getClass(), bankingtaxdetails.getIdBankingTaxDetails());
                salarycomponent.setBankingtaxdetails(bankingtaxdetails);
            }
            Employees employees = salarycomponent.getEmployees();
            if (employees != null) {
                employees = em.getReference(employees.getClass(), employees.getIdEmployees());
                salarycomponent.setEmployees(employees);
            }
            List<SalaryHistory> attachedSalaryHistoryList = new ArrayList<SalaryHistory>();
            for (SalaryHistory salaryHistoryListSalaryHistoryToAttach : salarycomponent.getSalaryHistoryList()) {
                salaryHistoryListSalaryHistoryToAttach = em.getReference(salaryHistoryListSalaryHistoryToAttach.getClass(), salaryHistoryListSalaryHistoryToAttach.getIdSalaryHistory());
                attachedSalaryHistoryList.add(salaryHistoryListSalaryHistoryToAttach);
            }
            salarycomponent.setSalaryHistoryList(attachedSalaryHistoryList);
            em.persist(salarycomponent);
            if (bankingtaxdetails != null) {
                bankingtaxdetails.getSalarycomponentList().add(salarycomponent);
                bankingtaxdetails = em.merge(bankingtaxdetails);
            }
            if (employees != null) {
                employees.getSalarycomponentList().add(salarycomponent);
                employees = em.merge(employees);
            }
            for (SalaryHistory salaryHistoryListSalaryHistory : salarycomponent.getSalaryHistoryList()) {
                Salarycomponent oldSalarycomponentOfSalaryHistoryListSalaryHistory = salaryHistoryListSalaryHistory.getSalarycomponent();
                salaryHistoryListSalaryHistory.setSalarycomponent(salarycomponent);
                salaryHistoryListSalaryHistory = em.merge(salaryHistoryListSalaryHistory);
                if (oldSalarycomponentOfSalaryHistoryListSalaryHistory != null) {
                    oldSalarycomponentOfSalaryHistoryListSalaryHistory.getSalaryHistoryList().remove(salaryHistoryListSalaryHistory);
                    oldSalarycomponentOfSalaryHistoryListSalaryHistory = em.merge(oldSalarycomponentOfSalaryHistoryListSalaryHistory);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSalarycomponent(salarycomponent.getSalarycomponentPK()) != null) {
                throw new PreexistingEntityException("Salarycomponent " + salarycomponent + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Salarycomponent salarycomponent) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        salarycomponent.getSalarycomponentPK().setEmployeeId(salarycomponent.getEmployees().getIdEmployees());
        salarycomponent.getSalarycomponentPK().setDepositDetails(salarycomponent.getBankingtaxdetails().getIdBankingTaxDetails());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Salarycomponent persistentSalarycomponent = em.find(Salarycomponent.class, salarycomponent.getSalarycomponentPK());
            Bankingtaxdetails bankingtaxdetailsOld = persistentSalarycomponent.getBankingtaxdetails();
            Bankingtaxdetails bankingtaxdetailsNew = salarycomponent.getBankingtaxdetails();
            Employees employeesOld = persistentSalarycomponent.getEmployees();
            Employees employeesNew = salarycomponent.getEmployees();
            List<SalaryHistory> salaryHistoryListOld = persistentSalarycomponent.getSalaryHistoryList();
            List<SalaryHistory> salaryHistoryListNew = salarycomponent.getSalaryHistoryList();
            List<String> illegalOrphanMessages = null;
            for (SalaryHistory salaryHistoryListOldSalaryHistory : salaryHistoryListOld) {
                if (!salaryHistoryListNew.contains(salaryHistoryListOldSalaryHistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SalaryHistory " + salaryHistoryListOldSalaryHistory + " since its salarycomponent field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (bankingtaxdetailsNew != null) {
                bankingtaxdetailsNew = em.getReference(bankingtaxdetailsNew.getClass(), bankingtaxdetailsNew.getIdBankingTaxDetails());
                salarycomponent.setBankingtaxdetails(bankingtaxdetailsNew);
            }
            if (employeesNew != null) {
                employeesNew = em.getReference(employeesNew.getClass(), employeesNew.getIdEmployees());
                salarycomponent.setEmployees(employeesNew);
            }
            List<SalaryHistory> attachedSalaryHistoryListNew = new ArrayList<SalaryHistory>();
            for (SalaryHistory salaryHistoryListNewSalaryHistoryToAttach : salaryHistoryListNew) {
                salaryHistoryListNewSalaryHistoryToAttach = em.getReference(salaryHistoryListNewSalaryHistoryToAttach.getClass(), salaryHistoryListNewSalaryHistoryToAttach.getIdSalaryHistory());
                attachedSalaryHistoryListNew.add(salaryHistoryListNewSalaryHistoryToAttach);
            }
            salaryHistoryListNew = attachedSalaryHistoryListNew;
            salarycomponent.setSalaryHistoryList(salaryHistoryListNew);
            salarycomponent = em.merge(salarycomponent);
            if (bankingtaxdetailsOld != null && !bankingtaxdetailsOld.equals(bankingtaxdetailsNew)) {
                bankingtaxdetailsOld.getSalarycomponentList().remove(salarycomponent);
                bankingtaxdetailsOld = em.merge(bankingtaxdetailsOld);
            }
            if (bankingtaxdetailsNew != null && !bankingtaxdetailsNew.equals(bankingtaxdetailsOld)) {
                bankingtaxdetailsNew.getSalarycomponentList().add(salarycomponent);
                bankingtaxdetailsNew = em.merge(bankingtaxdetailsNew);
            }
            if (employeesOld != null && !employeesOld.equals(employeesNew)) {
                employeesOld.getSalarycomponentList().remove(salarycomponent);
                employeesOld = em.merge(employeesOld);
            }
            if (employeesNew != null && !employeesNew.equals(employeesOld)) {
                employeesNew.getSalarycomponentList().add(salarycomponent);
                employeesNew = em.merge(employeesNew);
            }
            for (SalaryHistory salaryHistoryListNewSalaryHistory : salaryHistoryListNew) {
                if (!salaryHistoryListOld.contains(salaryHistoryListNewSalaryHistory)) {
                    Salarycomponent oldSalarycomponentOfSalaryHistoryListNewSalaryHistory = salaryHistoryListNewSalaryHistory.getSalarycomponent();
                    salaryHistoryListNewSalaryHistory.setSalarycomponent(salarycomponent);
                    salaryHistoryListNewSalaryHistory = em.merge(salaryHistoryListNewSalaryHistory);
                    if (oldSalarycomponentOfSalaryHistoryListNewSalaryHistory != null && !oldSalarycomponentOfSalaryHistoryListNewSalaryHistory.equals(salarycomponent)) {
                        oldSalarycomponentOfSalaryHistoryListNewSalaryHistory.getSalaryHistoryList().remove(salaryHistoryListNewSalaryHistory);
                        oldSalarycomponentOfSalaryHistoryListNewSalaryHistory = em.merge(oldSalarycomponentOfSalaryHistoryListNewSalaryHistory);
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
                SalarycomponentPK id = salarycomponent.getSalarycomponentPK();
                if (findSalarycomponent(id) == null) {
                    throw new NonexistentEntityException("The salarycomponent with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SalarycomponentPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Salarycomponent salarycomponent;
            try {
                salarycomponent = em.getReference(Salarycomponent.class, id);
                salarycomponent.getSalarycomponentPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salarycomponent with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SalaryHistory> salaryHistoryListOrphanCheck = salarycomponent.getSalaryHistoryList();
            for (SalaryHistory salaryHistoryListOrphanCheckSalaryHistory : salaryHistoryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Salarycomponent (" + salarycomponent + ") cannot be destroyed since the SalaryHistory " + salaryHistoryListOrphanCheckSalaryHistory + " in its salaryHistoryList field has a non-nullable salarycomponent field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Bankingtaxdetails bankingtaxdetails = salarycomponent.getBankingtaxdetails();
            if (bankingtaxdetails != null) {
                bankingtaxdetails.getSalarycomponentList().remove(salarycomponent);
                bankingtaxdetails = em.merge(bankingtaxdetails);
            }
            Employees employees = salarycomponent.getEmployees();
            if (employees != null) {
                employees.getSalarycomponentList().remove(salarycomponent);
                employees = em.merge(employees);
            }
            em.remove(salarycomponent);
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

    public List<Salarycomponent> findSalarycomponentEntities() {
        return findSalarycomponentEntities(true, -1, -1);
    }

    public List<Salarycomponent> findSalarycomponentEntities(int maxResults, int firstResult) {
        return findSalarycomponentEntities(false, maxResults, firstResult);
    }

    private List<Salarycomponent> findSalarycomponentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Salarycomponent.class));
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

    public Salarycomponent findSalarycomponent(SalarycomponentPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salarycomponent.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalarycomponentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Salarycomponent> rt = cq.from(Salarycomponent.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
