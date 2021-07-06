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
import Entity.Projects;
import Entity.Timesheet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class TimesheetJpaController implements Serializable {

    public TimesheetJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Timesheet timesheet) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Projects projectId = timesheet.getProjectId();
            if (projectId != null) {
                projectId = em.getReference(projectId.getClass(), projectId.getIdProjects());
                timesheet.setProjectId(projectId);
            }
            em.persist(timesheet);
            if (projectId != null) {
                projectId.getTimesheetList().add(timesheet);
                projectId = em.merge(projectId);
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

    public void edit(Timesheet timesheet) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Timesheet persistentTimesheet = em.find(Timesheet.class, timesheet.getIdTimesheet());
            Projects projectIdOld = persistentTimesheet.getProjectId();
            Projects projectIdNew = timesheet.getProjectId();
            if (projectIdNew != null) {
                projectIdNew = em.getReference(projectIdNew.getClass(), projectIdNew.getIdProjects());
                timesheet.setProjectId(projectIdNew);
            }
            timesheet = em.merge(timesheet);
            if (projectIdOld != null && !projectIdOld.equals(projectIdNew)) {
                projectIdOld.getTimesheetList().remove(timesheet);
                projectIdOld = em.merge(projectIdOld);
            }
            if (projectIdNew != null && !projectIdNew.equals(projectIdOld)) {
                projectIdNew.getTimesheetList().add(timesheet);
                projectIdNew = em.merge(projectIdNew);
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
                Integer id = timesheet.getIdTimesheet();
                if (findTimesheet(id) == null) {
                    throw new NonexistentEntityException("The timesheet with id " + id + " no longer exists.");
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
            Timesheet timesheet;
            try {
                timesheet = em.getReference(Timesheet.class, id);
                timesheet.getIdTimesheet();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The timesheet with id " + id + " no longer exists.", enfe);
            }
            Projects projectId = timesheet.getProjectId();
            if (projectId != null) {
                projectId.getTimesheetList().remove(timesheet);
                projectId = em.merge(projectId);
            }
            em.remove(timesheet);
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

    public List<Timesheet> findTimesheetEntities() {
        return findTimesheetEntities(true, -1, -1);
    }

    public List<Timesheet> findTimesheetEntities(int maxResults, int firstResult) {
        return findTimesheetEntities(false, maxResults, firstResult);
    }

    private List<Timesheet> findTimesheetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Timesheet.class));
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

    public Timesheet findTimesheet(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Timesheet.class, id);
        } finally {
            em.close();
        }
    }

    public int getTimesheetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Timesheet> rt = cq.from(Timesheet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
