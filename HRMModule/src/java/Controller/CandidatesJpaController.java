/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import Entity.Candidates;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Vacancies;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class CandidatesJpaController implements Serializable {

    public CandidatesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Candidates candidates) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Vacancies vacancyId = candidates.getVacancyId();
            if (vacancyId != null) {
                vacancyId = em.getReference(vacancyId.getClass(), vacancyId.getIdVacancies());
                candidates.setVacancyId(vacancyId);
            }
            em.persist(candidates);
            if (vacancyId != null) {
                vacancyId.getCandidatesList().add(candidates);
                vacancyId = em.merge(vacancyId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCandidates(candidates.getIdCandidates()) != null) {
                throw new PreexistingEntityException("Candidates " + candidates + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Candidates candidates) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Candidates persistentCandidates = em.find(Candidates.class, candidates.getIdCandidates());
            Vacancies vacancyIdOld = persistentCandidates.getVacancyId();
            Vacancies vacancyIdNew = candidates.getVacancyId();
            if (vacancyIdNew != null) {
                vacancyIdNew = em.getReference(vacancyIdNew.getClass(), vacancyIdNew.getIdVacancies());
                candidates.setVacancyId(vacancyIdNew);
            }
            candidates = em.merge(candidates);
            if (vacancyIdOld != null && !vacancyIdOld.equals(vacancyIdNew)) {
                vacancyIdOld.getCandidatesList().remove(candidates);
                vacancyIdOld = em.merge(vacancyIdOld);
            }
            if (vacancyIdNew != null && !vacancyIdNew.equals(vacancyIdOld)) {
                vacancyIdNew.getCandidatesList().add(candidates);
                vacancyIdNew = em.merge(vacancyIdNew);
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
                Integer id = candidates.getIdCandidates();
                if (findCandidates(id) == null) {
                    throw new NonexistentEntityException("The candidates with id " + id + " no longer exists.");
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
            Candidates candidates;
            try {
                candidates = em.getReference(Candidates.class, id);
                candidates.getIdCandidates();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The candidates with id " + id + " no longer exists.", enfe);
            }
            Vacancies vacancyId = candidates.getVacancyId();
            if (vacancyId != null) {
                vacancyId.getCandidatesList().remove(candidates);
                vacancyId = em.merge(vacancyId);
            }
            em.remove(candidates);
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

    public List<Candidates> findCandidatesEntities() {
        return findCandidatesEntities(true, -1, -1);
    }

    public List<Candidates> findCandidatesEntities(int maxResults, int firstResult) {
        return findCandidatesEntities(false, maxResults, firstResult);
    }

    private List<Candidates> findCandidatesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Candidates.class));
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

    public Candidates findCandidates(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Candidates.class, id);
        } finally {
            em.close();
        }
    }

    public int getCandidatesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Candidates> rt = cq.from(Candidates.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
