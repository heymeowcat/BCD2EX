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
import Entity.Leavetypes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class LeavetypesJpaController implements Serializable {

    public LeavetypesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Leavetypes leavetypes) throws RollbackFailureException, Exception {
        if (leavetypes.getLeaveList() == null) {
            leavetypes.setLeaveList(new ArrayList<Leave>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organization organizationId = leavetypes.getOrganizationId();
            if (organizationId != null) {
                organizationId = em.getReference(organizationId.getClass(), organizationId.getIdOrganization());
                leavetypes.setOrganizationId(organizationId);
            }
            List<Leave> attachedLeaveList = new ArrayList<Leave>();
            for (Leave leaveListLeaveToAttach : leavetypes.getLeaveList()) {
                leaveListLeaveToAttach = em.getReference(leaveListLeaveToAttach.getClass(), leaveListLeaveToAttach.getIdLeave());
                attachedLeaveList.add(leaveListLeaveToAttach);
            }
            leavetypes.setLeaveList(attachedLeaveList);
            em.persist(leavetypes);
            if (organizationId != null) {
                organizationId.getLeavetypesList().add(leavetypes);
                organizationId = em.merge(organizationId);
            }
            for (Leave leaveListLeave : leavetypes.getLeaveList()) {
                Leavetypes oldLeavetypeIdOfLeaveListLeave = leaveListLeave.getLeavetypeId();
                leaveListLeave.setLeavetypeId(leavetypes);
                leaveListLeave = em.merge(leaveListLeave);
                if (oldLeavetypeIdOfLeaveListLeave != null) {
                    oldLeavetypeIdOfLeaveListLeave.getLeaveList().remove(leaveListLeave);
                    oldLeavetypeIdOfLeaveListLeave = em.merge(oldLeavetypeIdOfLeaveListLeave);
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

    public void edit(Leavetypes leavetypes) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Leavetypes persistentLeavetypes = em.find(Leavetypes.class, leavetypes.getIdLeaveTypes());
            Organization organizationIdOld = persistentLeavetypes.getOrganizationId();
            Organization organizationIdNew = leavetypes.getOrganizationId();
            List<Leave> leaveListOld = persistentLeavetypes.getLeaveList();
            List<Leave> leaveListNew = leavetypes.getLeaveList();
            List<String> illegalOrphanMessages = null;
            for (Leave leaveListOldLeave : leaveListOld) {
                if (!leaveListNew.contains(leaveListOldLeave)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Leave " + leaveListOldLeave + " since its leavetypeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (organizationIdNew != null) {
                organizationIdNew = em.getReference(organizationIdNew.getClass(), organizationIdNew.getIdOrganization());
                leavetypes.setOrganizationId(organizationIdNew);
            }
            List<Leave> attachedLeaveListNew = new ArrayList<Leave>();
            for (Leave leaveListNewLeaveToAttach : leaveListNew) {
                leaveListNewLeaveToAttach = em.getReference(leaveListNewLeaveToAttach.getClass(), leaveListNewLeaveToAttach.getIdLeave());
                attachedLeaveListNew.add(leaveListNewLeaveToAttach);
            }
            leaveListNew = attachedLeaveListNew;
            leavetypes.setLeaveList(leaveListNew);
            leavetypes = em.merge(leavetypes);
            if (organizationIdOld != null && !organizationIdOld.equals(organizationIdNew)) {
                organizationIdOld.getLeavetypesList().remove(leavetypes);
                organizationIdOld = em.merge(organizationIdOld);
            }
            if (organizationIdNew != null && !organizationIdNew.equals(organizationIdOld)) {
                organizationIdNew.getLeavetypesList().add(leavetypes);
                organizationIdNew = em.merge(organizationIdNew);
            }
            for (Leave leaveListNewLeave : leaveListNew) {
                if (!leaveListOld.contains(leaveListNewLeave)) {
                    Leavetypes oldLeavetypeIdOfLeaveListNewLeave = leaveListNewLeave.getLeavetypeId();
                    leaveListNewLeave.setLeavetypeId(leavetypes);
                    leaveListNewLeave = em.merge(leaveListNewLeave);
                    if (oldLeavetypeIdOfLeaveListNewLeave != null && !oldLeavetypeIdOfLeaveListNewLeave.equals(leavetypes)) {
                        oldLeavetypeIdOfLeaveListNewLeave.getLeaveList().remove(leaveListNewLeave);
                        oldLeavetypeIdOfLeaveListNewLeave = em.merge(oldLeavetypeIdOfLeaveListNewLeave);
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
                Integer id = leavetypes.getIdLeaveTypes();
                if (findLeavetypes(id) == null) {
                    throw new NonexistentEntityException("The leavetypes with id " + id + " no longer exists.");
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
            Leavetypes leavetypes;
            try {
                leavetypes = em.getReference(Leavetypes.class, id);
                leavetypes.getIdLeaveTypes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The leavetypes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Leave> leaveListOrphanCheck = leavetypes.getLeaveList();
            for (Leave leaveListOrphanCheckLeave : leaveListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Leavetypes (" + leavetypes + ") cannot be destroyed since the Leave " + leaveListOrphanCheckLeave + " in its leaveList field has a non-nullable leavetypeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Organization organizationId = leavetypes.getOrganizationId();
            if (organizationId != null) {
                organizationId.getLeavetypesList().remove(leavetypes);
                organizationId = em.merge(organizationId);
            }
            em.remove(leavetypes);
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

    public List<Leavetypes> findLeavetypesEntities() {
        return findLeavetypesEntities(true, -1, -1);
    }

    public List<Leavetypes> findLeavetypesEntities(int maxResults, int firstResult) {
        return findLeavetypesEntities(false, maxResults, firstResult);
    }

    private List<Leavetypes> findLeavetypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Leavetypes.class));
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

    public Leavetypes findLeavetypes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Leavetypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getLeavetypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Leavetypes> rt = cq.from(Leavetypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
