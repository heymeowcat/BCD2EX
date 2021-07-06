/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import Entity.Attendance;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Employees;
import Entity.Workshifts;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class AttendanceJpaController implements Serializable {

    public AttendanceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Attendance attendance) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees employeeId = attendance.getEmployeeId();
            if (employeeId != null) {
                employeeId = em.getReference(employeeId.getClass(), employeeId.getIdEmployees());
                attendance.setEmployeeId(employeeId);
            }
            Workshifts workShiftId = attendance.getWorkShiftId();
            if (workShiftId != null) {
                workShiftId = em.getReference(workShiftId.getClass(), workShiftId.getIdWorkShifts());
                attendance.setWorkShiftId(workShiftId);
            }
            em.persist(attendance);
            if (employeeId != null) {
                employeeId.getAttendanceList().add(attendance);
                employeeId = em.merge(employeeId);
            }
            if (workShiftId != null) {
                workShiftId.getAttendanceList().add(attendance);
                workShiftId = em.merge(workShiftId);
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

    public void edit(Attendance attendance) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Attendance persistentAttendance = em.find(Attendance.class, attendance.getIdAttendance());
            Employees employeeIdOld = persistentAttendance.getEmployeeId();
            Employees employeeIdNew = attendance.getEmployeeId();
            Workshifts workShiftIdOld = persistentAttendance.getWorkShiftId();
            Workshifts workShiftIdNew = attendance.getWorkShiftId();
            if (employeeIdNew != null) {
                employeeIdNew = em.getReference(employeeIdNew.getClass(), employeeIdNew.getIdEmployees());
                attendance.setEmployeeId(employeeIdNew);
            }
            if (workShiftIdNew != null) {
                workShiftIdNew = em.getReference(workShiftIdNew.getClass(), workShiftIdNew.getIdWorkShifts());
                attendance.setWorkShiftId(workShiftIdNew);
            }
            attendance = em.merge(attendance);
            if (employeeIdOld != null && !employeeIdOld.equals(employeeIdNew)) {
                employeeIdOld.getAttendanceList().remove(attendance);
                employeeIdOld = em.merge(employeeIdOld);
            }
            if (employeeIdNew != null && !employeeIdNew.equals(employeeIdOld)) {
                employeeIdNew.getAttendanceList().add(attendance);
                employeeIdNew = em.merge(employeeIdNew);
            }
            if (workShiftIdOld != null && !workShiftIdOld.equals(workShiftIdNew)) {
                workShiftIdOld.getAttendanceList().remove(attendance);
                workShiftIdOld = em.merge(workShiftIdOld);
            }
            if (workShiftIdNew != null && !workShiftIdNew.equals(workShiftIdOld)) {
                workShiftIdNew.getAttendanceList().add(attendance);
                workShiftIdNew = em.merge(workShiftIdNew);
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
                Integer id = attendance.getIdAttendance();
                if (findAttendance(id) == null) {
                    throw new NonexistentEntityException("The attendance with id " + id + " no longer exists.");
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
            Attendance attendance;
            try {
                attendance = em.getReference(Attendance.class, id);
                attendance.getIdAttendance();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attendance with id " + id + " no longer exists.", enfe);
            }
            Employees employeeId = attendance.getEmployeeId();
            if (employeeId != null) {
                employeeId.getAttendanceList().remove(attendance);
                employeeId = em.merge(employeeId);
            }
            Workshifts workShiftId = attendance.getWorkShiftId();
            if (workShiftId != null) {
                workShiftId.getAttendanceList().remove(attendance);
                workShiftId = em.merge(workShiftId);
            }
            em.remove(attendance);
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

    public List<Attendance> findAttendanceEntities() {
        return findAttendanceEntities(true, -1, -1);
    }

    public List<Attendance> findAttendanceEntities(int maxResults, int firstResult) {
        return findAttendanceEntities(false, maxResults, firstResult);
    }

    private List<Attendance> findAttendanceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attendance.class));
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

    public Attendance findAttendance(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attendance.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttendanceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attendance> rt = cq.from(Attendance.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
