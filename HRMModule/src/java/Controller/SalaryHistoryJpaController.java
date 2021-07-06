/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import Entity.SalaryHistory;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Salarycomponent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class SalaryHistoryJpaController implements Serializable {

    public SalaryHistoryJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SalaryHistory salaryHistory) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Salarycomponent salarycomponent = salaryHistory.getSalarycomponent();
            if (salarycomponent != null) {
                salarycomponent = em.getReference(salarycomponent.getClass(), salarycomponent.getSalarycomponentPK());
                salaryHistory.setSalarycomponent(salarycomponent);
            }
            em.persist(salaryHistory);
            if (salarycomponent != null) {
                salarycomponent.getSalaryHistoryList().add(salaryHistory);
                salarycomponent = em.merge(salarycomponent);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSalaryHistory(salaryHistory.getIdSalaryHistory()) != null) {
                throw new PreexistingEntityException("SalaryHistory " + salaryHistory + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SalaryHistory salaryHistory) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SalaryHistory persistentSalaryHistory = em.find(SalaryHistory.class, salaryHistory.getIdSalaryHistory());
            Salarycomponent salarycomponentOld = persistentSalaryHistory.getSalarycomponent();
            Salarycomponent salarycomponentNew = salaryHistory.getSalarycomponent();
            if (salarycomponentNew != null) {
                salarycomponentNew = em.getReference(salarycomponentNew.getClass(), salarycomponentNew.getSalarycomponentPK());
                salaryHistory.setSalarycomponent(salarycomponentNew);
            }
            salaryHistory = em.merge(salaryHistory);
            if (salarycomponentOld != null && !salarycomponentOld.equals(salarycomponentNew)) {
                salarycomponentOld.getSalaryHistoryList().remove(salaryHistory);
                salarycomponentOld = em.merge(salarycomponentOld);
            }
            if (salarycomponentNew != null && !salarycomponentNew.equals(salarycomponentOld)) {
                salarycomponentNew.getSalaryHistoryList().add(salaryHistory);
                salarycomponentNew = em.merge(salarycomponentNew);
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
                Integer id = salaryHistory.getIdSalaryHistory();
                if (findSalaryHistory(id) == null) {
                    throw new NonexistentEntityException("The salaryHistory with id " + id + " no longer exists.");
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
            SalaryHistory salaryHistory;
            try {
                salaryHistory = em.getReference(SalaryHistory.class, id);
                salaryHistory.getIdSalaryHistory();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salaryHistory with id " + id + " no longer exists.", enfe);
            }
            Salarycomponent salarycomponent = salaryHistory.getSalarycomponent();
            if (salarycomponent != null) {
                salarycomponent.getSalaryHistoryList().remove(salaryHistory);
                salarycomponent = em.merge(salarycomponent);
            }
            em.remove(salaryHistory);
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

    public List<SalaryHistory> findSalaryHistoryEntities() {
        return findSalaryHistoryEntities(true, -1, -1);
    }

    public List<SalaryHistory> findSalaryHistoryEntities(int maxResults, int firstResult) {
        return findSalaryHistoryEntities(false, maxResults, firstResult);
    }

    private List<SalaryHistory> findSalaryHistoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SalaryHistory.class));
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

    public SalaryHistory findSalaryHistory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SalaryHistory.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalaryHistoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SalaryHistory> rt = cq.from(SalaryHistory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
