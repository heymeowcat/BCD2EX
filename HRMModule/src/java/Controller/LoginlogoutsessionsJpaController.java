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
import Entity.Login;
import Entity.Loginlogoutsessions;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class LoginlogoutsessionsJpaController implements Serializable {

    public LoginlogoutsessionsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Loginlogoutsessions loginlogoutsessions) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Login loginId = loginlogoutsessions.getLoginId();
            if (loginId != null) {
                loginId = em.getReference(loginId.getClass(), loginId.getLoginPK());
                loginlogoutsessions.setLoginId(loginId);
            }
            em.persist(loginlogoutsessions);
            if (loginId != null) {
                loginId.getLoginlogoutsessionsList().add(loginlogoutsessions);
                loginId = em.merge(loginId);
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

    public void edit(Loginlogoutsessions loginlogoutsessions) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Loginlogoutsessions persistentLoginlogoutsessions = em.find(Loginlogoutsessions.class, loginlogoutsessions.getIdLoginSessions());
            Login loginIdOld = persistentLoginlogoutsessions.getLoginId();
            Login loginIdNew = loginlogoutsessions.getLoginId();
            if (loginIdNew != null) {
                loginIdNew = em.getReference(loginIdNew.getClass(), loginIdNew.getLoginPK());
                loginlogoutsessions.setLoginId(loginIdNew);
            }
            loginlogoutsessions = em.merge(loginlogoutsessions);
            if (loginIdOld != null && !loginIdOld.equals(loginIdNew)) {
                loginIdOld.getLoginlogoutsessionsList().remove(loginlogoutsessions);
                loginIdOld = em.merge(loginIdOld);
            }
            if (loginIdNew != null && !loginIdNew.equals(loginIdOld)) {
                loginIdNew.getLoginlogoutsessionsList().add(loginlogoutsessions);
                loginIdNew = em.merge(loginIdNew);
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
                Integer id = loginlogoutsessions.getIdLoginSessions();
                if (findLoginlogoutsessions(id) == null) {
                    throw new NonexistentEntityException("The loginlogoutsessions with id " + id + " no longer exists.");
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
            Loginlogoutsessions loginlogoutsessions;
            try {
                loginlogoutsessions = em.getReference(Loginlogoutsessions.class, id);
                loginlogoutsessions.getIdLoginSessions();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The loginlogoutsessions with id " + id + " no longer exists.", enfe);
            }
            Login loginId = loginlogoutsessions.getLoginId();
            if (loginId != null) {
                loginId.getLoginlogoutsessionsList().remove(loginlogoutsessions);
                loginId = em.merge(loginId);
            }
            em.remove(loginlogoutsessions);
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

    public List<Loginlogoutsessions> findLoginlogoutsessionsEntities() {
        return findLoginlogoutsessionsEntities(true, -1, -1);
    }

    public List<Loginlogoutsessions> findLoginlogoutsessionsEntities(int maxResults, int firstResult) {
        return findLoginlogoutsessionsEntities(false, maxResults, firstResult);
    }

    private List<Loginlogoutsessions> findLoginlogoutsessionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Loginlogoutsessions.class));
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

    public Loginlogoutsessions findLoginlogoutsessions(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Loginlogoutsessions.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoginlogoutsessionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Loginlogoutsessions> rt = cq.from(Loginlogoutsessions.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
