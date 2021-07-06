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
import Entity.Employees;
import Entity.Jobs;
import Entity.Candidates;
import Entity.Vacancies;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class VacanciesJpaController implements Serializable {

    public VacanciesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vacancies vacancies) throws RollbackFailureException, Exception {
        if (vacancies.getCandidatesList() == null) {
            vacancies.setCandidatesList(new ArrayList<Candidates>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees hiringManager = vacancies.getHiringManager();
            if (hiringManager != null) {
                hiringManager = em.getReference(hiringManager.getClass(), hiringManager.getIdEmployees());
                vacancies.setHiringManager(hiringManager);
            }
            Jobs jobId = vacancies.getJobId();
            if (jobId != null) {
                jobId = em.getReference(jobId.getClass(), jobId.getIdJob());
                vacancies.setJobId(jobId);
            }
            List<Candidates> attachedCandidatesList = new ArrayList<Candidates>();
            for (Candidates candidatesListCandidatesToAttach : vacancies.getCandidatesList()) {
                candidatesListCandidatesToAttach = em.getReference(candidatesListCandidatesToAttach.getClass(), candidatesListCandidatesToAttach.getIdCandidates());
                attachedCandidatesList.add(candidatesListCandidatesToAttach);
            }
            vacancies.setCandidatesList(attachedCandidatesList);
            em.persist(vacancies);
            if (hiringManager != null) {
                hiringManager.getVacanciesList().add(vacancies);
                hiringManager = em.merge(hiringManager);
            }
            if (jobId != null) {
                jobId.getVacanciesList().add(vacancies);
                jobId = em.merge(jobId);
            }
            for (Candidates candidatesListCandidates : vacancies.getCandidatesList()) {
                Vacancies oldVacancyIdOfCandidatesListCandidates = candidatesListCandidates.getVacancyId();
                candidatesListCandidates.setVacancyId(vacancies);
                candidatesListCandidates = em.merge(candidatesListCandidates);
                if (oldVacancyIdOfCandidatesListCandidates != null) {
                    oldVacancyIdOfCandidatesListCandidates.getCandidatesList().remove(candidatesListCandidates);
                    oldVacancyIdOfCandidatesListCandidates = em.merge(oldVacancyIdOfCandidatesListCandidates);
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

    public void edit(Vacancies vacancies) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Vacancies persistentVacancies = em.find(Vacancies.class, vacancies.getIdVacancies());
            Employees hiringManagerOld = persistentVacancies.getHiringManager();
            Employees hiringManagerNew = vacancies.getHiringManager();
            Jobs jobIdOld = persistentVacancies.getJobId();
            Jobs jobIdNew = vacancies.getJobId();
            List<Candidates> candidatesListOld = persistentVacancies.getCandidatesList();
            List<Candidates> candidatesListNew = vacancies.getCandidatesList();
            List<String> illegalOrphanMessages = null;
            for (Candidates candidatesListOldCandidates : candidatesListOld) {
                if (!candidatesListNew.contains(candidatesListOldCandidates)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Candidates " + candidatesListOldCandidates + " since its vacancyId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (hiringManagerNew != null) {
                hiringManagerNew = em.getReference(hiringManagerNew.getClass(), hiringManagerNew.getIdEmployees());
                vacancies.setHiringManager(hiringManagerNew);
            }
            if (jobIdNew != null) {
                jobIdNew = em.getReference(jobIdNew.getClass(), jobIdNew.getIdJob());
                vacancies.setJobId(jobIdNew);
            }
            List<Candidates> attachedCandidatesListNew = new ArrayList<Candidates>();
            for (Candidates candidatesListNewCandidatesToAttach : candidatesListNew) {
                candidatesListNewCandidatesToAttach = em.getReference(candidatesListNewCandidatesToAttach.getClass(), candidatesListNewCandidatesToAttach.getIdCandidates());
                attachedCandidatesListNew.add(candidatesListNewCandidatesToAttach);
            }
            candidatesListNew = attachedCandidatesListNew;
            vacancies.setCandidatesList(candidatesListNew);
            vacancies = em.merge(vacancies);
            if (hiringManagerOld != null && !hiringManagerOld.equals(hiringManagerNew)) {
                hiringManagerOld.getVacanciesList().remove(vacancies);
                hiringManagerOld = em.merge(hiringManagerOld);
            }
            if (hiringManagerNew != null && !hiringManagerNew.equals(hiringManagerOld)) {
                hiringManagerNew.getVacanciesList().add(vacancies);
                hiringManagerNew = em.merge(hiringManagerNew);
            }
            if (jobIdOld != null && !jobIdOld.equals(jobIdNew)) {
                jobIdOld.getVacanciesList().remove(vacancies);
                jobIdOld = em.merge(jobIdOld);
            }
            if (jobIdNew != null && !jobIdNew.equals(jobIdOld)) {
                jobIdNew.getVacanciesList().add(vacancies);
                jobIdNew = em.merge(jobIdNew);
            }
            for (Candidates candidatesListNewCandidates : candidatesListNew) {
                if (!candidatesListOld.contains(candidatesListNewCandidates)) {
                    Vacancies oldVacancyIdOfCandidatesListNewCandidates = candidatesListNewCandidates.getVacancyId();
                    candidatesListNewCandidates.setVacancyId(vacancies);
                    candidatesListNewCandidates = em.merge(candidatesListNewCandidates);
                    if (oldVacancyIdOfCandidatesListNewCandidates != null && !oldVacancyIdOfCandidatesListNewCandidates.equals(vacancies)) {
                        oldVacancyIdOfCandidatesListNewCandidates.getCandidatesList().remove(candidatesListNewCandidates);
                        oldVacancyIdOfCandidatesListNewCandidates = em.merge(oldVacancyIdOfCandidatesListNewCandidates);
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
                Integer id = vacancies.getIdVacancies();
                if (findVacancies(id) == null) {
                    throw new NonexistentEntityException("The vacancies with id " + id + " no longer exists.");
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
            Vacancies vacancies;
            try {
                vacancies = em.getReference(Vacancies.class, id);
                vacancies.getIdVacancies();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vacancies with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Candidates> candidatesListOrphanCheck = vacancies.getCandidatesList();
            for (Candidates candidatesListOrphanCheckCandidates : candidatesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vacancies (" + vacancies + ") cannot be destroyed since the Candidates " + candidatesListOrphanCheckCandidates + " in its candidatesList field has a non-nullable vacancyId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Employees hiringManager = vacancies.getHiringManager();
            if (hiringManager != null) {
                hiringManager.getVacanciesList().remove(vacancies);
                hiringManager = em.merge(hiringManager);
            }
            Jobs jobId = vacancies.getJobId();
            if (jobId != null) {
                jobId.getVacanciesList().remove(vacancies);
                jobId = em.merge(jobId);
            }
            em.remove(vacancies);
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

    public List<Vacancies> findVacanciesEntities() {
        return findVacanciesEntities(true, -1, -1);
    }

    public List<Vacancies> findVacanciesEntities(int maxResults, int firstResult) {
        return findVacanciesEntities(false, maxResults, firstResult);
    }

    private List<Vacancies> findVacanciesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vacancies.class));
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

    public Vacancies findVacancies(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vacancies.class, id);
        } finally {
            em.close();
        }
    }

    public int getVacanciesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vacancies> rt = cq.from(Vacancies.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
