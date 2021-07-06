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
import Entity.Organization;
import Entity.Leave;
import Entity.Leaveactions;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class LeaveactionsJpaController implements Serializable {

    public LeaveactionsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Leaveactions leaveactions) throws RollbackFailureException, Exception {
        if (leaveactions.getLeaveList() == null) {
            leaveactions.setLeaveList(new ArrayList<Leave>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organization organizationId = leaveactions.getOrganizationId();
            if (organizationId != null) {
                organizationId = em.getReference(organizationId.getClass(), organizationId.getIdOrganization());
                leaveactions.setOrganizationId(organizationId);
            }
            List<Leave> attachedLeaveList = new ArrayList<Leave>();
            for (Leave leaveListLeaveToAttach : leaveactions.getLeaveList()) {
                leaveListLeaveToAttach = em.getReference(leaveListLeaveToAttach.getClass(), leaveListLeaveToAttach.getIdLeave());
                attachedLeaveList.add(leaveListLeaveToAttach);
            }
            leaveactions.setLeaveList(attachedLeaveList);
            em.persist(leaveactions);
            if (organizationId != null) {
                organizationId.getLeaveactionsList().add(leaveactions);
                organizationId = em.merge(organizationId);
            }
            for (Leave leaveListLeave : leaveactions.getLeaveList()) {
                Leaveactions oldLeaveActionOfLeaveListLeave = leaveListLeave.getLeaveAction();
                leaveListLeave.setLeaveAction(leaveactions);
                leaveListLeave = em.merge(leaveListLeave);
                if (oldLeaveActionOfLeaveListLeave != null) {
                    oldLeaveActionOfLeaveListLeave.getLeaveList().remove(leaveListLeave);
                    oldLeaveActionOfLeaveListLeave = em.merge(oldLeaveActionOfLeaveListLeave);
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

    public void edit(Leaveactions leaveactions) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Leaveactions persistentLeaveactions = em.find(Leaveactions.class, leaveactions.getIdLeaveActions());
            Organization organizationIdOld = persistentLeaveactions.getOrganizationId();
            Organization organizationIdNew = leaveactions.getOrganizationId();
            List<Leave> leaveListOld = persistentLeaveactions.getLeaveList();
            List<Leave> leaveListNew = leaveactions.getLeaveList();
            List<String> illegalOrphanMessages = null;
            for (Leave leaveListOldLeave : leaveListOld) {
                if (!leaveListNew.contains(leaveListOldLeave)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Leave " + leaveListOldLeave + " since its leaveAction field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (organizationIdNew != null) {
                organizationIdNew = em.getReference(organizationIdNew.getClass(), organizationIdNew.getIdOrganization());
                leaveactions.setOrganizationId(organizationIdNew);
            }
            List<Leave> attachedLeaveListNew = new ArrayList<Leave>();
            for (Leave leaveListNewLeaveToAttach : leaveListNew) {
                leaveListNewLeaveToAttach = em.getReference(leaveListNewLeaveToAttach.getClass(), leaveListNewLeaveToAttach.getIdLeave());
                attachedLeaveListNew.add(leaveListNewLeaveToAttach);
            }
            leaveListNew = attachedLeaveListNew;
            leaveactions.setLeaveList(leaveListNew);
            leaveactions = em.merge(leaveactions);
            if (organizationIdOld != null && !organizationIdOld.equals(organizationIdNew)) {
                organizationIdOld.getLeaveactionsList().remove(leaveactions);
                organizationIdOld = em.merge(organizationIdOld);
            }
            if (organizationIdNew != null && !organizationIdNew.equals(organizationIdOld)) {
                organizationIdNew.getLeaveactionsList().add(leaveactions);
                organizationIdNew = em.merge(organizationIdNew);
            }
            for (Leave leaveListNewLeave : leaveListNew) {
                if (!leaveListOld.contains(leaveListNewLeave)) {
                    Leaveactions oldLeaveActionOfLeaveListNewLeave = leaveListNewLeave.getLeaveAction();
                    leaveListNewLeave.setLeaveAction(leaveactions);
                    leaveListNewLeave = em.merge(leaveListNewLeave);
                    if (oldLeaveActionOfLeaveListNewLeave != null && !oldLeaveActionOfLeaveListNewLeave.equals(leaveactions)) {
                        oldLeaveActionOfLeaveListNewLeave.getLeaveList().remove(leaveListNewLeave);
                        oldLeaveActionOfLeaveListNewLeave = em.merge(oldLeaveActionOfLeaveListNewLeave);
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
                Integer id = leaveactions.getIdLeaveActions();
                if (findLeaveactions(id) == null) {
                    throw new NonexistentEntityException("The leaveactions with id " + id + " no longer exists.");
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
            Leaveactions leaveactions;
            try {
                leaveactions = em.getReference(Leaveactions.class, id);
                leaveactions.getIdLeaveActions();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The leaveactions with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Leave> leaveListOrphanCheck = leaveactions.getLeaveList();
            for (Leave leaveListOrphanCheckLeave : leaveListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Leaveactions (" + leaveactions + ") cannot be destroyed since the Leave " + leaveListOrphanCheckLeave + " in its leaveList field has a non-nullable leaveAction field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Organization organizationId = leaveactions.getOrganizationId();
            if (organizationId != null) {
                organizationId.getLeaveactionsList().remove(leaveactions);
                organizationId = em.merge(organizationId);
            }
            em.remove(leaveactions);
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

    public List<Leaveactions> findLeaveactionsEntities() {
        return findLeaveactionsEntities(true, -1, -1);
    }

    public List<Leaveactions> findLeaveactionsEntities(int maxResults, int firstResult) {
        return findLeaveactionsEntities(false, maxResults, firstResult);
    }

    private List<Leaveactions> findLeaveactionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Leaveactions.class));
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

    public Leaveactions findLeaveactions(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Leaveactions.class, id);
        } finally {
            em.close();
        }
    }

    public int getLeaveactionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Leaveactions> rt = cq.from(Leaveactions.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
