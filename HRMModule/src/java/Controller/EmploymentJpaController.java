/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Employees;
import Entity.Employment;
import Entity.EmploymentPK;
import Entity.Jobs;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class EmploymentJpaController implements Serializable {

    public EmploymentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employment employment) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (employment.getEmploymentPK() == null) {
            employment.setEmploymentPK(new EmploymentPK());
        }
        employment.getEmploymentPK().setJobsidJob(employment.getJobs().getIdJob());
        employment.getEmploymentPK().setEmployeeId(employment.getEmployees().getIdEmployees());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees employees = employment.getEmployees();
            if (employees != null) {
                employees = em.getReference(employees.getClass(), employees.getIdEmployees());
                employment.setEmployees(employees);
            }
            Jobs jobs = employment.getJobs();
            if (jobs != null) {
                jobs = em.getReference(jobs.getClass(), jobs.getIdJob());
                employment.setJobs(jobs);
            }
            em.persist(employment);
            if (employees != null) {
                employees.getEmploymentList().add(employment);
                employees = em.merge(employees);
            }
            if (jobs != null) {
                jobs.getEmploymentList().add(employment);
                jobs = em.merge(jobs);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmployment(employment.getEmploymentPK()) != null) {
                throw new PreexistingEntityException("Employment " + employment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employment employment) throws NonexistentEntityException, RollbackFailureException, Exception {
        employment.getEmploymentPK().setJobsidJob(employment.getJobs().getIdJob());
        employment.getEmploymentPK().setEmployeeId(employment.getEmployees().getIdEmployees());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employment persistentEmployment = em.find(Employment.class, employment.getEmploymentPK());
            Employees employeesOld = persistentEmployment.getEmployees();
            Employees employeesNew = employment.getEmployees();
            Jobs jobsOld = persistentEmployment.getJobs();
            Jobs jobsNew = employment.getJobs();
            if (employeesNew != null) {
                employeesNew = em.getReference(employeesNew.getClass(), employeesNew.getIdEmployees());
                employment.setEmployees(employeesNew);
            }
            if (jobsNew != null) {
                jobsNew = em.getReference(jobsNew.getClass(), jobsNew.getIdJob());
                employment.setJobs(jobsNew);
            }
            employment = em.merge(employment);
            if (employeesOld != null && !employeesOld.equals(employeesNew)) {
                employeesOld.getEmploymentList().remove(employment);
                employeesOld = em.merge(employeesOld);
            }
            if (employeesNew != null && !employeesNew.equals(employeesOld)) {
                employeesNew.getEmploymentList().add(employment);
                employeesNew = em.merge(employeesNew);
            }
            if (jobsOld != null && !jobsOld.equals(jobsNew)) {
                jobsOld.getEmploymentList().remove(employment);
                jobsOld = em.merge(jobsOld);
            }
            if (jobsNew != null && !jobsNew.equals(jobsOld)) {
                jobsNew.getEmploymentList().add(employment);
                jobsNew = em.merge(jobsNew);
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
                EmploymentPK id = employment.getEmploymentPK();
                if (findEmployment(id) == null) {
                    throw new NonexistentEntityException("The employment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EmploymentPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employment employment;
            try {
                employment = em.getReference(Employment.class, id);
                employment.getEmploymentPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employment with id " + id + " no longer exists.", enfe);
            }
            Employees employees = employment.getEmployees();
            if (employees != null) {
                employees.getEmploymentList().remove(employment);
                employees = em.merge(employees);
            }
            Jobs jobs = employment.getJobs();
            if (jobs != null) {
                jobs.getEmploymentList().remove(employment);
                jobs = em.merge(jobs);
            }
            em.remove(employment);
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

    public List<Employment> findEmploymentEntities() {
        return findEmploymentEntities(true, -1, -1);
    }

    public List<Employment> findEmploymentEntities(int maxResults, int firstResult) {
        return findEmploymentEntities(false, maxResults, firstResult);
    }

    private List<Employment> findEmploymentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employment.class));
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

    public Employment findEmployment(EmploymentPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employment.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmploymentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employment> rt = cq.from(Employment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
