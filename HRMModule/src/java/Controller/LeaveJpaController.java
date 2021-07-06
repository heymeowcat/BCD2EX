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
import Entity.Employees;
import Entity.Leave;
import Entity.Leaveactions;
import Entity.Leavetypes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class LeaveJpaController implements Serializable {

    public LeaveJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Leave leave) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees employeeId = leave.getEmployeeId();
            if (employeeId != null) {
                employeeId = em.getReference(employeeId.getClass(), employeeId.getIdEmployees());
                leave.setEmployeeId(employeeId);
            }
            Employees assignedBy = leave.getAssignedBy();
            if (assignedBy != null) {
                assignedBy = em.getReference(assignedBy.getClass(), assignedBy.getIdEmployees());
                leave.setAssignedBy(assignedBy);
            }
            Leaveactions leaveAction = leave.getLeaveAction();
            if (leaveAction != null) {
                leaveAction = em.getReference(leaveAction.getClass(), leaveAction.getIdLeaveActions());
                leave.setLeaveAction(leaveAction);
            }
            Leavetypes leavetypeId = leave.getLeavetypeId();
            if (leavetypeId != null) {
                leavetypeId = em.getReference(leavetypeId.getClass(), leavetypeId.getIdLeaveTypes());
                leave.setLeavetypeId(leavetypeId);
            }
            em.persist(leave);
            if (employeeId != null) {
                employeeId.getLeaveList().add(leave);
                employeeId = em.merge(employeeId);
            }
            if (assignedBy != null) {
                assignedBy.getLeaveList().add(leave);
                assignedBy = em.merge(assignedBy);
            }
            if (leaveAction != null) {
                leaveAction.getLeaveList().add(leave);
                leaveAction = em.merge(leaveAction);
            }
            if (leavetypeId != null) {
                leavetypeId.getLeaveList().add(leave);
                leavetypeId = em.merge(leavetypeId);
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

    public void edit(Leave leave) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Leave persistentLeave = em.find(Leave.class, leave.getIdLeave());
            Employees employeeIdOld = persistentLeave.getEmployeeId();
            Employees employeeIdNew = leave.getEmployeeId();
            Employees assignedByOld = persistentLeave.getAssignedBy();
            Employees assignedByNew = leave.getAssignedBy();
            Leaveactions leaveActionOld = persistentLeave.getLeaveAction();
            Leaveactions leaveActionNew = leave.getLeaveAction();
            Leavetypes leavetypeIdOld = persistentLeave.getLeavetypeId();
            Leavetypes leavetypeIdNew = leave.getLeavetypeId();
            if (employeeIdNew != null) {
                employeeIdNew = em.getReference(employeeIdNew.getClass(), employeeIdNew.getIdEmployees());
                leave.setEmployeeId(employeeIdNew);
            }
            if (assignedByNew != null) {
                assignedByNew = em.getReference(assignedByNew.getClass(), assignedByNew.getIdEmployees());
                leave.setAssignedBy(assignedByNew);
            }
            if (leaveActionNew != null) {
                leaveActionNew = em.getReference(leaveActionNew.getClass(), leaveActionNew.getIdLeaveActions());
                leave.setLeaveAction(leaveActionNew);
            }
            if (leavetypeIdNew != null) {
                leavetypeIdNew = em.getReference(leavetypeIdNew.getClass(), leavetypeIdNew.getIdLeaveTypes());
                leave.setLeavetypeId(leavetypeIdNew);
            }
            leave = em.merge(leave);
            if (employeeIdOld != null && !employeeIdOld.equals(employeeIdNew)) {
                employeeIdOld.getLeaveList().remove(leave);
                employeeIdOld = em.merge(employeeIdOld);
            }
            if (employeeIdNew != null && !employeeIdNew.equals(employeeIdOld)) {
                employeeIdNew.getLeaveList().add(leave);
                employeeIdNew = em.merge(employeeIdNew);
            }
            if (assignedByOld != null && !assignedByOld.equals(assignedByNew)) {
                assignedByOld.getLeaveList().remove(leave);
                assignedByOld = em.merge(assignedByOld);
            }
            if (assignedByNew != null && !assignedByNew.equals(assignedByOld)) {
                assignedByNew.getLeaveList().add(leave);
                assignedByNew = em.merge(assignedByNew);
            }
            if (leaveActionOld != null && !leaveActionOld.equals(leaveActionNew)) {
                leaveActionOld.getLeaveList().remove(leave);
                leaveActionOld = em.merge(leaveActionOld);
            }
            if (leaveActionNew != null && !leaveActionNew.equals(leaveActionOld)) {
                leaveActionNew.getLeaveList().add(leave);
                leaveActionNew = em.merge(leaveActionNew);
            }
            if (leavetypeIdOld != null && !leavetypeIdOld.equals(leavetypeIdNew)) {
                leavetypeIdOld.getLeaveList().remove(leave);
                leavetypeIdOld = em.merge(leavetypeIdOld);
            }
            if (leavetypeIdNew != null && !leavetypeIdNew.equals(leavetypeIdOld)) {
                leavetypeIdNew.getLeaveList().add(leave);
                leavetypeIdNew = em.merge(leavetypeIdNew);
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
                Integer id = leave.getIdLeave();
                if (findLeave(id) == null) {
                    throw new NonexistentEntityException("The leave with id " + id + " no longer exists.");
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
            Leave leave;
            try {
                leave = em.getReference(Leave.class, id);
                leave.getIdLeave();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The leave with id " + id + " no longer exists.", enfe);
            }
            Employees employeeId = leave.getEmployeeId();
            if (employeeId != null) {
                employeeId.getLeaveList().remove(leave);
                employeeId = em.merge(employeeId);
            }
            Employees assignedBy = leave.getAssignedBy();
            if (assignedBy != null) {
                assignedBy.getLeaveList().remove(leave);
                assignedBy = em.merge(assignedBy);
            }
            Leaveactions leaveAction = leave.getLeaveAction();
            if (leaveAction != null) {
                leaveAction.getLeaveList().remove(leave);
                leaveAction = em.merge(leaveAction);
            }
            Leavetypes leavetypeId = leave.getLeavetypeId();
            if (leavetypeId != null) {
                leavetypeId.getLeaveList().remove(leave);
                leavetypeId = em.merge(leavetypeId);
            }
            em.remove(leave);
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

    public List<Leave> findLeaveEntities() {
        return findLeaveEntities(true, -1, -1);
    }

    public List<Leave> findLeaveEntities(int maxResults, int firstResult) {
        return findLeaveEntities(false, maxResults, firstResult);
    }

    private List<Leave> findLeaveEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Leave.class));
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

    public Leave findLeave(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Leave.class, id);
        } finally {
            em.close();
        }
    }

    public int getLeaveCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Leave> rt = cq.from(Leave.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
