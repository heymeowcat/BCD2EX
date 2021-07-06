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
import Entity.Jobcategories;
import Entity.Employment;
import Entity.Jobs;
import java.util.ArrayList;
import java.util.List;
import Entity.Vacancies;
import Entity.Workshifts;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class JobsJpaController implements Serializable {

    public JobsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jobs jobs) throws RollbackFailureException, Exception {
        if (jobs.getEmploymentList() == null) {
            jobs.setEmploymentList(new ArrayList<Employment>());
        }
        if (jobs.getVacanciesList() == null) {
            jobs.setVacanciesList(new ArrayList<Vacancies>());
        }
        if (jobs.getWorkshiftsList() == null) {
            jobs.setWorkshiftsList(new ArrayList<Workshifts>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Jobcategories jobCategoryId = jobs.getJobCategoryId();
            if (jobCategoryId != null) {
                jobCategoryId = em.getReference(jobCategoryId.getClass(), jobCategoryId.getIdJobCategories());
                jobs.setJobCategoryId(jobCategoryId);
            }
            List<Employment> attachedEmploymentList = new ArrayList<Employment>();
            for (Employment employmentListEmploymentToAttach : jobs.getEmploymentList()) {
                employmentListEmploymentToAttach = em.getReference(employmentListEmploymentToAttach.getClass(), employmentListEmploymentToAttach.getEmploymentPK());
                attachedEmploymentList.add(employmentListEmploymentToAttach);
            }
            jobs.setEmploymentList(attachedEmploymentList);
            List<Vacancies> attachedVacanciesList = new ArrayList<Vacancies>();
            for (Vacancies vacanciesListVacanciesToAttach : jobs.getVacanciesList()) {
                vacanciesListVacanciesToAttach = em.getReference(vacanciesListVacanciesToAttach.getClass(), vacanciesListVacanciesToAttach.getIdVacancies());
                attachedVacanciesList.add(vacanciesListVacanciesToAttach);
            }
            jobs.setVacanciesList(attachedVacanciesList);
            List<Workshifts> attachedWorkshiftsList = new ArrayList<Workshifts>();
            for (Workshifts workshiftsListWorkshiftsToAttach : jobs.getWorkshiftsList()) {
                workshiftsListWorkshiftsToAttach = em.getReference(workshiftsListWorkshiftsToAttach.getClass(), workshiftsListWorkshiftsToAttach.getIdWorkShifts());
                attachedWorkshiftsList.add(workshiftsListWorkshiftsToAttach);
            }
            jobs.setWorkshiftsList(attachedWorkshiftsList);
            em.persist(jobs);
            if (jobCategoryId != null) {
                jobCategoryId.getJobsList().add(jobs);
                jobCategoryId = em.merge(jobCategoryId);
            }
            for (Employment employmentListEmployment : jobs.getEmploymentList()) {
                Jobs oldJobsOfEmploymentListEmployment = employmentListEmployment.getJobs();
                employmentListEmployment.setJobs(jobs);
                employmentListEmployment = em.merge(employmentListEmployment);
                if (oldJobsOfEmploymentListEmployment != null) {
                    oldJobsOfEmploymentListEmployment.getEmploymentList().remove(employmentListEmployment);
                    oldJobsOfEmploymentListEmployment = em.merge(oldJobsOfEmploymentListEmployment);
                }
            }
            for (Vacancies vacanciesListVacancies : jobs.getVacanciesList()) {
                Jobs oldJobIdOfVacanciesListVacancies = vacanciesListVacancies.getJobId();
                vacanciesListVacancies.setJobId(jobs);
                vacanciesListVacancies = em.merge(vacanciesListVacancies);
                if (oldJobIdOfVacanciesListVacancies != null) {
                    oldJobIdOfVacanciesListVacancies.getVacanciesList().remove(vacanciesListVacancies);
                    oldJobIdOfVacanciesListVacancies = em.merge(oldJobIdOfVacanciesListVacancies);
                }
            }
            for (Workshifts workshiftsListWorkshifts : jobs.getWorkshiftsList()) {
                Jobs oldJobIdOfWorkshiftsListWorkshifts = workshiftsListWorkshifts.getJobId();
                workshiftsListWorkshifts.setJobId(jobs);
                workshiftsListWorkshifts = em.merge(workshiftsListWorkshifts);
                if (oldJobIdOfWorkshiftsListWorkshifts != null) {
                    oldJobIdOfWorkshiftsListWorkshifts.getWorkshiftsList().remove(workshiftsListWorkshifts);
                    oldJobIdOfWorkshiftsListWorkshifts = em.merge(oldJobIdOfWorkshiftsListWorkshifts);
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

    public void edit(Jobs jobs) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Jobs persistentJobs = em.find(Jobs.class, jobs.getIdJob());
            Jobcategories jobCategoryIdOld = persistentJobs.getJobCategoryId();
            Jobcategories jobCategoryIdNew = jobs.getJobCategoryId();
            List<Employment> employmentListOld = persistentJobs.getEmploymentList();
            List<Employment> employmentListNew = jobs.getEmploymentList();
            List<Vacancies> vacanciesListOld = persistentJobs.getVacanciesList();
            List<Vacancies> vacanciesListNew = jobs.getVacanciesList();
            List<Workshifts> workshiftsListOld = persistentJobs.getWorkshiftsList();
            List<Workshifts> workshiftsListNew = jobs.getWorkshiftsList();
            List<String> illegalOrphanMessages = null;
            for (Employment employmentListOldEmployment : employmentListOld) {
                if (!employmentListNew.contains(employmentListOldEmployment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employment " + employmentListOldEmployment + " since its jobs field is not nullable.");
                }
            }
            for (Vacancies vacanciesListOldVacancies : vacanciesListOld) {
                if (!vacanciesListNew.contains(vacanciesListOldVacancies)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vacancies " + vacanciesListOldVacancies + " since its jobId field is not nullable.");
                }
            }
            for (Workshifts workshiftsListOldWorkshifts : workshiftsListOld) {
                if (!workshiftsListNew.contains(workshiftsListOldWorkshifts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Workshifts " + workshiftsListOldWorkshifts + " since its jobId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (jobCategoryIdNew != null) {
                jobCategoryIdNew = em.getReference(jobCategoryIdNew.getClass(), jobCategoryIdNew.getIdJobCategories());
                jobs.setJobCategoryId(jobCategoryIdNew);
            }
            List<Employment> attachedEmploymentListNew = new ArrayList<Employment>();
            for (Employment employmentListNewEmploymentToAttach : employmentListNew) {
                employmentListNewEmploymentToAttach = em.getReference(employmentListNewEmploymentToAttach.getClass(), employmentListNewEmploymentToAttach.getEmploymentPK());
                attachedEmploymentListNew.add(employmentListNewEmploymentToAttach);
            }
            employmentListNew = attachedEmploymentListNew;
            jobs.setEmploymentList(employmentListNew);
            List<Vacancies> attachedVacanciesListNew = new ArrayList<Vacancies>();
            for (Vacancies vacanciesListNewVacanciesToAttach : vacanciesListNew) {
                vacanciesListNewVacanciesToAttach = em.getReference(vacanciesListNewVacanciesToAttach.getClass(), vacanciesListNewVacanciesToAttach.getIdVacancies());
                attachedVacanciesListNew.add(vacanciesListNewVacanciesToAttach);
            }
            vacanciesListNew = attachedVacanciesListNew;
            jobs.setVacanciesList(vacanciesListNew);
            List<Workshifts> attachedWorkshiftsListNew = new ArrayList<Workshifts>();
            for (Workshifts workshiftsListNewWorkshiftsToAttach : workshiftsListNew) {
                workshiftsListNewWorkshiftsToAttach = em.getReference(workshiftsListNewWorkshiftsToAttach.getClass(), workshiftsListNewWorkshiftsToAttach.getIdWorkShifts());
                attachedWorkshiftsListNew.add(workshiftsListNewWorkshiftsToAttach);
            }
            workshiftsListNew = attachedWorkshiftsListNew;
            jobs.setWorkshiftsList(workshiftsListNew);
            jobs = em.merge(jobs);
            if (jobCategoryIdOld != null && !jobCategoryIdOld.equals(jobCategoryIdNew)) {
                jobCategoryIdOld.getJobsList().remove(jobs);
                jobCategoryIdOld = em.merge(jobCategoryIdOld);
            }
            if (jobCategoryIdNew != null && !jobCategoryIdNew.equals(jobCategoryIdOld)) {
                jobCategoryIdNew.getJobsList().add(jobs);
                jobCategoryIdNew = em.merge(jobCategoryIdNew);
            }
            for (Employment employmentListNewEmployment : employmentListNew) {
                if (!employmentListOld.contains(employmentListNewEmployment)) {
                    Jobs oldJobsOfEmploymentListNewEmployment = employmentListNewEmployment.getJobs();
                    employmentListNewEmployment.setJobs(jobs);
                    employmentListNewEmployment = em.merge(employmentListNewEmployment);
                    if (oldJobsOfEmploymentListNewEmployment != null && !oldJobsOfEmploymentListNewEmployment.equals(jobs)) {
                        oldJobsOfEmploymentListNewEmployment.getEmploymentList().remove(employmentListNewEmployment);
                        oldJobsOfEmploymentListNewEmployment = em.merge(oldJobsOfEmploymentListNewEmployment);
                    }
                }
            }
            for (Vacancies vacanciesListNewVacancies : vacanciesListNew) {
                if (!vacanciesListOld.contains(vacanciesListNewVacancies)) {
                    Jobs oldJobIdOfVacanciesListNewVacancies = vacanciesListNewVacancies.getJobId();
                    vacanciesListNewVacancies.setJobId(jobs);
                    vacanciesListNewVacancies = em.merge(vacanciesListNewVacancies);
                    if (oldJobIdOfVacanciesListNewVacancies != null && !oldJobIdOfVacanciesListNewVacancies.equals(jobs)) {
                        oldJobIdOfVacanciesListNewVacancies.getVacanciesList().remove(vacanciesListNewVacancies);
                        oldJobIdOfVacanciesListNewVacancies = em.merge(oldJobIdOfVacanciesListNewVacancies);
                    }
                }
            }
            for (Workshifts workshiftsListNewWorkshifts : workshiftsListNew) {
                if (!workshiftsListOld.contains(workshiftsListNewWorkshifts)) {
                    Jobs oldJobIdOfWorkshiftsListNewWorkshifts = workshiftsListNewWorkshifts.getJobId();
                    workshiftsListNewWorkshifts.setJobId(jobs);
                    workshiftsListNewWorkshifts = em.merge(workshiftsListNewWorkshifts);
                    if (oldJobIdOfWorkshiftsListNewWorkshifts != null && !oldJobIdOfWorkshiftsListNewWorkshifts.equals(jobs)) {
                        oldJobIdOfWorkshiftsListNewWorkshifts.getWorkshiftsList().remove(workshiftsListNewWorkshifts);
                        oldJobIdOfWorkshiftsListNewWorkshifts = em.merge(oldJobIdOfWorkshiftsListNewWorkshifts);
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
                Integer id = jobs.getIdJob();
                if (findJobs(id) == null) {
                    throw new NonexistentEntityException("The jobs with id " + id + " no longer exists.");
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
            Jobs jobs;
            try {
                jobs = em.getReference(Jobs.class, id);
                jobs.getIdJob();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jobs with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Employment> employmentListOrphanCheck = jobs.getEmploymentList();
            for (Employment employmentListOrphanCheckEmployment : employmentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jobs (" + jobs + ") cannot be destroyed since the Employment " + employmentListOrphanCheckEmployment + " in its employmentList field has a non-nullable jobs field.");
            }
            List<Vacancies> vacanciesListOrphanCheck = jobs.getVacanciesList();
            for (Vacancies vacanciesListOrphanCheckVacancies : vacanciesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jobs (" + jobs + ") cannot be destroyed since the Vacancies " + vacanciesListOrphanCheckVacancies + " in its vacanciesList field has a non-nullable jobId field.");
            }
            List<Workshifts> workshiftsListOrphanCheck = jobs.getWorkshiftsList();
            for (Workshifts workshiftsListOrphanCheckWorkshifts : workshiftsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jobs (" + jobs + ") cannot be destroyed since the Workshifts " + workshiftsListOrphanCheckWorkshifts + " in its workshiftsList field has a non-nullable jobId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Jobcategories jobCategoryId = jobs.getJobCategoryId();
            if (jobCategoryId != null) {
                jobCategoryId.getJobsList().remove(jobs);
                jobCategoryId = em.merge(jobCategoryId);
            }
            em.remove(jobs);
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

    public List<Jobs> findJobsEntities() {
        return findJobsEntities(true, -1, -1);
    }

    public List<Jobs> findJobsEntities(int maxResults, int firstResult) {
        return findJobsEntities(false, maxResults, firstResult);
    }

    private List<Jobs> findJobsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jobs.class));
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

    public Jobs findJobs(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jobs.class, id);
        } finally {
            em.close();
        }
    }

    public int getJobsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jobs> rt = cq.from(Jobs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
