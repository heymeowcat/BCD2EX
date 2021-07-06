/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Activities;
import Entity.Assignedemployees;
import Entity.Employees;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class AssignedemployeesJpaController implements Serializable {

    public AssignedemployeesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Assignedemployees assignedemployees) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Activities activityId = assignedemployees.getActivityId();
            if (activityId != null) {
                activityId = em.getReference(activityId.getClass(), activityId.getIdActivities());
                assignedemployees.setActivityId(activityId);
            }
            Employees employeeId = assignedemployees.getEmployeeId();
            if (employeeId != null) {
                employeeId = em.getReference(employeeId.getClass(), employeeId.getIdEmployees());
                assignedemployees.setEmployeeId(employeeId);
            }
            em.persist(assignedemployees);
            if (activityId != null) {
                activityId.getAssignedemployeesList().add(assignedemployees);
                activityId = em.merge(activityId);
            }
            if (employeeId != null) {
                employeeId.getAssignedemployeesList().add(assignedemployees);
                employeeId = em.merge(employeeId);
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

    public void edit(Assignedemployees assignedemployees) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Assignedemployees persistentAssignedemployees = em.find(Assignedemployees.class, assignedemployees.getIdAssignedEmployees());
            Activities activityIdOld = persistentAssignedemployees.getActivityId();
            Activities activityIdNew = assignedemployees.getActivityId();
            Employees employeeIdOld = persistentAssignedemployees.getEmployeeId();
            Employees employeeIdNew = assignedemployees.getEmployeeId();
            if (activityIdNew != null) {
                activityIdNew = em.getReference(activityIdNew.getClass(), activityIdNew.getIdActivities());
                assignedemployees.setActivityId(activityIdNew);
            }
            if (employeeIdNew != null) {
                employeeIdNew = em.getReference(employeeIdNew.getClass(), employeeIdNew.getIdEmployees());
                assignedemployees.setEmployeeId(employeeIdNew);
            }
            assignedemployees = em.merge(assignedemployees);
            if (activityIdOld != null && !activityIdOld.equals(activityIdNew)) {
                activityIdOld.getAssignedemployeesList().remove(assignedemployees);
                activityIdOld = em.merge(activityIdOld);
            }
            if (activityIdNew != null && !activityIdNew.equals(activityIdOld)) {
                activityIdNew.getAssignedemployeesList().add(assignedemployees);
                activityIdNew = em.merge(activityIdNew);
            }
            if (employeeIdOld != null && !employeeIdOld.equals(employeeIdNew)) {
                employeeIdOld.getAssignedemployeesList().remove(assignedemployees);
                employeeIdOld = em.merge(employeeIdOld);
            }
            if (employeeIdNew != null && !employeeIdNew.equals(employeeIdOld)) {
                employeeIdNew.getAssignedemployeesList().add(assignedemployees);
                employeeIdNew = em.merge(employeeIdNew);
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
                Integer id = assignedemployees.getIdAssignedEmployees();
                if (findAssignedemployees(id) == null) {
                    throw new NonexistentEntityException("The assignedemployees with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Assignedemployees assignedemployees;
            try {
                assignedemployees = em.getReference(Assignedemployees.class, id);
                assignedemployees.getIdAssignedEmployees();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The assignedemployees with id " + id + " no longer exists.", enfe);
            }
            Activities activityId = assignedemployees.getActivityId();
            if (activityId != null) {
                activityId.getAssignedemployeesList().remove(assignedemployees);
                activityId = em.merge(activityId);
            }
            Employees employeeId = assignedemployees.getEmployeeId();
            if (employeeId != null) {
                employeeId.getAssignedemployeesList().remove(assignedemployees);
                employeeId = em.merge(employeeId);
            }
            em.remove(assignedemployees);
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

    public List<Assignedemployees> findAssignedemployeesEntities() {
        return findAssignedemployeesEntities(true, -1, -1);
    }

    public List<Assignedemployees> findAssignedemployeesEntities(int maxResults, int firstResult) {
        return findAssignedemployeesEntities(false, maxResults, firstResult);
    }

    private List<Assignedemployees> findAssignedemployeesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Assignedemployees.class));
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

    public Assignedemployees findAssignedemployees(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Assignedemployees.class, id);
        } finally {
            em.close();
        }
    }

    public int getAssignedemployeesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Assignedemployees> rt = cq.from(Assignedemployees.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
