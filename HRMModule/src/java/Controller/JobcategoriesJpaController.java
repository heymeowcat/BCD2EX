/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import Entity.Jobcategories;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Subunit;
import Entity.Jobs;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class JobcategoriesJpaController implements Serializable {

    public JobcategoriesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jobcategories jobcategories) throws RollbackFailureException, Exception {
        if (jobcategories.getJobsList() == null) {
            jobcategories.setJobsList(new ArrayList<Jobs>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subunit subUnitId = jobcategories.getSubUnitId();
            if (subUnitId != null) {
                subUnitId = em.getReference(subUnitId.getClass(), subUnitId.getIdSubUnit());
                jobcategories.setSubUnitId(subUnitId);
            }
            List<Jobs> attachedJobsList = new ArrayList<Jobs>();
            for (Jobs jobsListJobsToAttach : jobcategories.getJobsList()) {
                jobsListJobsToAttach = em.getReference(jobsListJobsToAttach.getClass(), jobsListJobsToAttach.getIdJob());
                attachedJobsList.add(jobsListJobsToAttach);
            }
            jobcategories.setJobsList(attachedJobsList);
            em.persist(jobcategories);
            if (subUnitId != null) {
                subUnitId.getJobcategoriesList().add(jobcategories);
                subUnitId = em.merge(subUnitId);
            }
            for (Jobs jobsListJobs : jobcategories.getJobsList()) {
                Jobcategories oldJobCategoryIdOfJobsListJobs = jobsListJobs.getJobCategoryId();
                jobsListJobs.setJobCategoryId(jobcategories);
                jobsListJobs = em.merge(jobsListJobs);
                if (oldJobCategoryIdOfJobsListJobs != null) {
                    oldJobCategoryIdOfJobsListJobs.getJobsList().remove(jobsListJobs);
                    oldJobCategoryIdOfJobsListJobs = em.merge(oldJobCategoryIdOfJobsListJobs);
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

    public void edit(Jobcategories jobcategories) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Jobcategories persistentJobcategories = em.find(Jobcategories.class, jobcategories.getIdJobCategories());
            Subunit subUnitIdOld = persistentJobcategories.getSubUnitId();
            Subunit subUnitIdNew = jobcategories.getSubUnitId();
            List<Jobs> jobsListOld = persistentJobcategories.getJobsList();
            List<Jobs> jobsListNew = jobcategories.getJobsList();
            List<String> illegalOrphanMessages = null;
            for (Jobs jobsListOldJobs : jobsListOld) {
                if (!jobsListNew.contains(jobsListOldJobs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Jobs " + jobsListOldJobs + " since its jobCategoryId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (subUnitIdNew != null) {
                subUnitIdNew = em.getReference(subUnitIdNew.getClass(), subUnitIdNew.getIdSubUnit());
                jobcategories.setSubUnitId(subUnitIdNew);
            }
            List<Jobs> attachedJobsListNew = new ArrayList<Jobs>();
            for (Jobs jobsListNewJobsToAttach : jobsListNew) {
                jobsListNewJobsToAttach = em.getReference(jobsListNewJobsToAttach.getClass(), jobsListNewJobsToAttach.getIdJob());
                attachedJobsListNew.add(jobsListNewJobsToAttach);
            }
            jobsListNew = attachedJobsListNew;
            jobcategories.setJobsList(jobsListNew);
            jobcategories = em.merge(jobcategories);
            if (subUnitIdOld != null && !subUnitIdOld.equals(subUnitIdNew)) {
                subUnitIdOld.getJobcategoriesList().remove(jobcategories);
                subUnitIdOld = em.merge(subUnitIdOld);
            }
            if (subUnitIdNew != null && !subUnitIdNew.equals(subUnitIdOld)) {
                subUnitIdNew.getJobcategoriesList().add(jobcategories);
                subUnitIdNew = em.merge(subUnitIdNew);
            }
            for (Jobs jobsListNewJobs : jobsListNew) {
                if (!jobsListOld.contains(jobsListNewJobs)) {
                    Jobcategories oldJobCategoryIdOfJobsListNewJobs = jobsListNewJobs.getJobCategoryId();
                    jobsListNewJobs.setJobCategoryId(jobcategories);
                    jobsListNewJobs = em.merge(jobsListNewJobs);
                    if (oldJobCategoryIdOfJobsListNewJobs != null && !oldJobCategoryIdOfJobsListNewJobs.equals(jobcategories)) {
                        oldJobCategoryIdOfJobsListNewJobs.getJobsList().remove(jobsListNewJobs);
                        oldJobCategoryIdOfJobsListNewJobs = em.merge(oldJobCategoryIdOfJobsListNewJobs);
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
                Integer id = jobcategories.getIdJobCategories();
                if (findJobcategories(id) == null) {
                    throw new NonexistentEntityException("The jobcategories with id " + id + " no longer exists.");
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
            Jobcategories jobcategories;
            try {
                jobcategories = em.getReference(Jobcategories.class, id);
                jobcategories.getIdJobCategories();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jobcategories with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Jobs> jobsListOrphanCheck = jobcategories.getJobsList();
            for (Jobs jobsListOrphanCheckJobs : jobsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jobcategories (" + jobcategories + ") cannot be destroyed since the Jobs " + jobsListOrphanCheckJobs + " in its jobsList field has a non-nullable jobCategoryId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Subunit subUnitId = jobcategories.getSubUnitId();
            if (subUnitId != null) {
                subUnitId.getJobcategoriesList().remove(jobcategories);
                subUnitId = em.merge(subUnitId);
            }
            em.remove(jobcategories);
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

    public List<Jobcategories> findJobcategoriesEntities() {
        return findJobcategoriesEntities(true, -1, -1);
    }

    public List<Jobcategories> findJobcategoriesEntities(int maxResults, int firstResult) {
        return findJobcategoriesEntities(false, maxResults, firstResult);
    }

    private List<Jobcategories> findJobcategoriesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jobcategories.class));
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

    public Jobcategories findJobcategories(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jobcategories.class, id);
        } finally {
            em.close();
        }
    }

    public int getJobcategoriesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jobcategories> rt = cq.from(Jobcategories.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
