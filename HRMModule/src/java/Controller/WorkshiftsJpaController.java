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
import Entity.Jobs;
import Entity.Attendance;
import Entity.Workshifts;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class WorkshiftsJpaController implements Serializable {

    public WorkshiftsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Workshifts workshifts) throws RollbackFailureException, Exception {
        if (workshifts.getAttendanceList() == null) {
            workshifts.setAttendanceList(new ArrayList<Attendance>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Jobs jobId = workshifts.getJobId();
            if (jobId != null) {
                jobId = em.getReference(jobId.getClass(), jobId.getIdJob());
                workshifts.setJobId(jobId);
            }
            List<Attendance> attachedAttendanceList = new ArrayList<Attendance>();
            for (Attendance attendanceListAttendanceToAttach : workshifts.getAttendanceList()) {
                attendanceListAttendanceToAttach = em.getReference(attendanceListAttendanceToAttach.getClass(), attendanceListAttendanceToAttach.getIdAttendance());
                attachedAttendanceList.add(attendanceListAttendanceToAttach);
            }
            workshifts.setAttendanceList(attachedAttendanceList);
            em.persist(workshifts);
            if (jobId != null) {
                jobId.getWorkshiftsList().add(workshifts);
                jobId = em.merge(jobId);
            }
            for (Attendance attendanceListAttendance : workshifts.getAttendanceList()) {
                Workshifts oldWorkShiftIdOfAttendanceListAttendance = attendanceListAttendance.getWorkShiftId();
                attendanceListAttendance.setWorkShiftId(workshifts);
                attendanceListAttendance = em.merge(attendanceListAttendance);
                if (oldWorkShiftIdOfAttendanceListAttendance != null) {
                    oldWorkShiftIdOfAttendanceListAttendance.getAttendanceList().remove(attendanceListAttendance);
                    oldWorkShiftIdOfAttendanceListAttendance = em.merge(oldWorkShiftIdOfAttendanceListAttendance);
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

    public void edit(Workshifts workshifts) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Workshifts persistentWorkshifts = em.find(Workshifts.class, workshifts.getIdWorkShifts());
            Jobs jobIdOld = persistentWorkshifts.getJobId();
            Jobs jobIdNew = workshifts.getJobId();
            List<Attendance> attendanceListOld = persistentWorkshifts.getAttendanceList();
            List<Attendance> attendanceListNew = workshifts.getAttendanceList();
            List<String> illegalOrphanMessages = null;
            for (Attendance attendanceListOldAttendance : attendanceListOld) {
                if (!attendanceListNew.contains(attendanceListOldAttendance)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Attendance " + attendanceListOldAttendance + " since its workShiftId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (jobIdNew != null) {
                jobIdNew = em.getReference(jobIdNew.getClass(), jobIdNew.getIdJob());
                workshifts.setJobId(jobIdNew);
            }
            List<Attendance> attachedAttendanceListNew = new ArrayList<Attendance>();
            for (Attendance attendanceListNewAttendanceToAttach : attendanceListNew) {
                attendanceListNewAttendanceToAttach = em.getReference(attendanceListNewAttendanceToAttach.getClass(), attendanceListNewAttendanceToAttach.getIdAttendance());
                attachedAttendanceListNew.add(attendanceListNewAttendanceToAttach);
            }
            attendanceListNew = attachedAttendanceListNew;
            workshifts.setAttendanceList(attendanceListNew);
            workshifts = em.merge(workshifts);
            if (jobIdOld != null && !jobIdOld.equals(jobIdNew)) {
                jobIdOld.getWorkshiftsList().remove(workshifts);
                jobIdOld = em.merge(jobIdOld);
            }
            if (jobIdNew != null && !jobIdNew.equals(jobIdOld)) {
                jobIdNew.getWorkshiftsList().add(workshifts);
                jobIdNew = em.merge(jobIdNew);
            }
            for (Attendance attendanceListNewAttendance : attendanceListNew) {
                if (!attendanceListOld.contains(attendanceListNewAttendance)) {
                    Workshifts oldWorkShiftIdOfAttendanceListNewAttendance = attendanceListNewAttendance.getWorkShiftId();
                    attendanceListNewAttendance.setWorkShiftId(workshifts);
                    attendanceListNewAttendance = em.merge(attendanceListNewAttendance);
                    if (oldWorkShiftIdOfAttendanceListNewAttendance != null && !oldWorkShiftIdOfAttendanceListNewAttendance.equals(workshifts)) {
                        oldWorkShiftIdOfAttendanceListNewAttendance.getAttendanceList().remove(attendanceListNewAttendance);
                        oldWorkShiftIdOfAttendanceListNewAttendance = em.merge(oldWorkShiftIdOfAttendanceListNewAttendance);
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
                Integer id = workshifts.getIdWorkShifts();
                if (findWorkshifts(id) == null) {
                    throw new NonexistentEntityException("The workshifts with id " + id + " no longer exists.");
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
            Workshifts workshifts;
            try {
                workshifts = em.getReference(Workshifts.class, id);
                workshifts.getIdWorkShifts();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workshifts with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Attendance> attendanceListOrphanCheck = workshifts.getAttendanceList();
            for (Attendance attendanceListOrphanCheckAttendance : attendanceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Workshifts (" + workshifts + ") cannot be destroyed since the Attendance " + attendanceListOrphanCheckAttendance + " in its attendanceList field has a non-nullable workShiftId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Jobs jobId = workshifts.getJobId();
            if (jobId != null) {
                jobId.getWorkshiftsList().remove(workshifts);
                jobId = em.merge(jobId);
            }
            em.remove(workshifts);
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

    public List<Workshifts> findWorkshiftsEntities() {
        return findWorkshiftsEntities(true, -1, -1);
    }

    public List<Workshifts> findWorkshiftsEntities(int maxResults, int firstResult) {
        return findWorkshiftsEntities(false, maxResults, firstResult);
    }

    private List<Workshifts> findWorkshiftsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Workshifts.class));
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

    public Workshifts findWorkshifts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Workshifts.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkshiftsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Workshifts> rt = cq.from(Workshifts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
