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
import Entity.Workexperience;
import Entity.WorkexperiencePK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class WorkexperienceJpaController implements Serializable {

    public WorkexperienceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Workexperience workexperience) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (workexperience.getWorkexperiencePK() == null) {
            workexperience.setWorkexperiencePK(new WorkexperiencePK());
        }
        workexperience.getWorkexperiencePK().setEmployeeId(workexperience.getEmployees().getIdEmployees());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees employees = workexperience.getEmployees();
            if (employees != null) {
                employees = em.getReference(employees.getClass(), employees.getIdEmployees());
                workexperience.setEmployees(employees);
            }
            em.persist(workexperience);
            if (employees != null) {
                employees.getWorkexperienceList().add(workexperience);
                employees = em.merge(employees);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findWorkexperience(workexperience.getWorkexperiencePK()) != null) {
                throw new PreexistingEntityException("Workexperience " + workexperience + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Workexperience workexperience) throws NonexistentEntityException, RollbackFailureException, Exception {
        workexperience.getWorkexperiencePK().setEmployeeId(workexperience.getEmployees().getIdEmployees());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Workexperience persistentWorkexperience = em.find(Workexperience.class, workexperience.getWorkexperiencePK());
            Employees employeesOld = persistentWorkexperience.getEmployees();
            Employees employeesNew = workexperience.getEmployees();
            if (employeesNew != null) {
                employeesNew = em.getReference(employeesNew.getClass(), employeesNew.getIdEmployees());
                workexperience.setEmployees(employeesNew);
            }
            workexperience = em.merge(workexperience);
            if (employeesOld != null && !employeesOld.equals(employeesNew)) {
                employeesOld.getWorkexperienceList().remove(workexperience);
                employeesOld = em.merge(employeesOld);
            }
            if (employeesNew != null && !employeesNew.equals(employeesOld)) {
                employeesNew.getWorkexperienceList().add(workexperience);
                employeesNew = em.merge(employeesNew);
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
                WorkexperiencePK id = workexperience.getWorkexperiencePK();
                if (findWorkexperience(id) == null) {
                    throw new NonexistentEntityException("The workexperience with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(WorkexperiencePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Workexperience workexperience;
            try {
                workexperience = em.getReference(Workexperience.class, id);
                workexperience.getWorkexperiencePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workexperience with id " + id + " no longer exists.", enfe);
            }
            Employees employees = workexperience.getEmployees();
            if (employees != null) {
                employees.getWorkexperienceList().remove(workexperience);
                employees = em.merge(employees);
            }
            em.remove(workexperience);
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

    public List<Workexperience> findWorkexperienceEntities() {
        return findWorkexperienceEntities(true, -1, -1);
    }

    public List<Workexperience> findWorkexperienceEntities(int maxResults, int firstResult) {
        return findWorkexperienceEntities(false, maxResults, firstResult);
    }

    private List<Workexperience> findWorkexperienceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Workexperience.class));
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

    public Workexperience findWorkexperience(WorkexperiencePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Workexperience.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkexperienceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Workexperience> rt = cq.from(Workexperience.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
