/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import Entity.Leavestatus;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Organization;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class LeavestatusJpaController implements Serializable {

    public LeavestatusJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Leavestatus leavestatus) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organization organizationId = leavestatus.getOrganizationId();
            if (organizationId != null) {
                organizationId = em.getReference(organizationId.getClass(), organizationId.getIdOrganization());
                leavestatus.setOrganizationId(organizationId);
            }
            em.persist(leavestatus);
            if (organizationId != null) {
                organizationId.getLeavestatusList().add(leavestatus);
                organizationId = em.merge(organizationId);
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

    public void edit(Leavestatus leavestatus) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Leavestatus persistentLeavestatus = em.find(Leavestatus.class, leavestatus.getIdLeaveStatus());
            Organization organizationIdOld = persistentLeavestatus.getOrganizationId();
            Organization organizationIdNew = leavestatus.getOrganizationId();
            if (organizationIdNew != null) {
                organizationIdNew = em.getReference(organizationIdNew.getClass(), organizationIdNew.getIdOrganization());
                leavestatus.setOrganizationId(organizationIdNew);
            }
            leavestatus = em.merge(leavestatus);
            if (organizationIdOld != null && !organizationIdOld.equals(organizationIdNew)) {
                organizationIdOld.getLeavestatusList().remove(leavestatus);
                organizationIdOld = em.merge(organizationIdOld);
            }
            if (organizationIdNew != null && !organizationIdNew.equals(organizationIdOld)) {
                organizationIdNew.getLeavestatusList().add(leavestatus);
                organizationIdNew = em.merge(organizationIdNew);
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
                Integer id = leavestatus.getIdLeaveStatus();
                if (findLeavestatus(id) == null) {
                    throw new NonexistentEntityException("The leavestatus with id " + id + " no longer exists.");
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
            Leavestatus leavestatus;
            try {
                leavestatus = em.getReference(Leavestatus.class, id);
                leavestatus.getIdLeaveStatus();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The leavestatus with id " + id + " no longer exists.", enfe);
            }
            Organization organizationId = leavestatus.getOrganizationId();
            if (organizationId != null) {
                organizationId.getLeavestatusList().remove(leavestatus);
                organizationId = em.merge(organizationId);
            }
            em.remove(leavestatus);
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

    public List<Leavestatus> findLeavestatusEntities() {
        return findLeavestatusEntities(true, -1, -1);
    }

    public List<Leavestatus> findLeavestatusEntities(int maxResults, int firstResult) {
        return findLeavestatusEntities(false, maxResults, firstResult);
    }

    private List<Leavestatus> findLeavestatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Leavestatus.class));
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

    public Leavestatus findLeavestatus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Leavestatus.class, id);
        } finally {
            em.close();
        }
    }

    public int getLeavestatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Leavestatus> rt = cq.from(Leavestatus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
