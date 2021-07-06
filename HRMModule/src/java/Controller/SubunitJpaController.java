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
import Entity.Locations;
import Entity.Jobcategories;
import Entity.Subunit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class SubunitJpaController implements Serializable {

    public SubunitJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subunit subunit) throws RollbackFailureException, Exception {
        if (subunit.getJobcategoriesList() == null) {
            subunit.setJobcategoriesList(new ArrayList<Jobcategories>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Locations locationId = subunit.getLocationId();
            if (locationId != null) {
                locationId = em.getReference(locationId.getClass(), locationId.getIdLocations());
                subunit.setLocationId(locationId);
            }
            List<Jobcategories> attachedJobcategoriesList = new ArrayList<Jobcategories>();
            for (Jobcategories jobcategoriesListJobcategoriesToAttach : subunit.getJobcategoriesList()) {
                jobcategoriesListJobcategoriesToAttach = em.getReference(jobcategoriesListJobcategoriesToAttach.getClass(), jobcategoriesListJobcategoriesToAttach.getIdJobCategories());
                attachedJobcategoriesList.add(jobcategoriesListJobcategoriesToAttach);
            }
            subunit.setJobcategoriesList(attachedJobcategoriesList);
            em.persist(subunit);
            if (locationId != null) {
                locationId.getSubunitList().add(subunit);
                locationId = em.merge(locationId);
            }
            for (Jobcategories jobcategoriesListJobcategories : subunit.getJobcategoriesList()) {
                Subunit oldSubUnitIdOfJobcategoriesListJobcategories = jobcategoriesListJobcategories.getSubUnitId();
                jobcategoriesListJobcategories.setSubUnitId(subunit);
                jobcategoriesListJobcategories = em.merge(jobcategoriesListJobcategories);
                if (oldSubUnitIdOfJobcategoriesListJobcategories != null) {
                    oldSubUnitIdOfJobcategoriesListJobcategories.getJobcategoriesList().remove(jobcategoriesListJobcategories);
                    oldSubUnitIdOfJobcategoriesListJobcategories = em.merge(oldSubUnitIdOfJobcategoriesListJobcategories);
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

    public void edit(Subunit subunit) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subunit persistentSubunit = em.find(Subunit.class, subunit.getIdSubUnit());
            Locations locationIdOld = persistentSubunit.getLocationId();
            Locations locationIdNew = subunit.getLocationId();
            List<Jobcategories> jobcategoriesListOld = persistentSubunit.getJobcategoriesList();
            List<Jobcategories> jobcategoriesListNew = subunit.getJobcategoriesList();
            List<String> illegalOrphanMessages = null;
            for (Jobcategories jobcategoriesListOldJobcategories : jobcategoriesListOld) {
                if (!jobcategoriesListNew.contains(jobcategoriesListOldJobcategories)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Jobcategories " + jobcategoriesListOldJobcategories + " since its subUnitId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (locationIdNew != null) {
                locationIdNew = em.getReference(locationIdNew.getClass(), locationIdNew.getIdLocations());
                subunit.setLocationId(locationIdNew);
            }
            List<Jobcategories> attachedJobcategoriesListNew = new ArrayList<Jobcategories>();
            for (Jobcategories jobcategoriesListNewJobcategoriesToAttach : jobcategoriesListNew) {
                jobcategoriesListNewJobcategoriesToAttach = em.getReference(jobcategoriesListNewJobcategoriesToAttach.getClass(), jobcategoriesListNewJobcategoriesToAttach.getIdJobCategories());
                attachedJobcategoriesListNew.add(jobcategoriesListNewJobcategoriesToAttach);
            }
            jobcategoriesListNew = attachedJobcategoriesListNew;
            subunit.setJobcategoriesList(jobcategoriesListNew);
            subunit = em.merge(subunit);
            if (locationIdOld != null && !locationIdOld.equals(locationIdNew)) {
                locationIdOld.getSubunitList().remove(subunit);
                locationIdOld = em.merge(locationIdOld);
            }
            if (locationIdNew != null && !locationIdNew.equals(locationIdOld)) {
                locationIdNew.getSubunitList().add(subunit);
                locationIdNew = em.merge(locationIdNew);
            }
            for (Jobcategories jobcategoriesListNewJobcategories : jobcategoriesListNew) {
                if (!jobcategoriesListOld.contains(jobcategoriesListNewJobcategories)) {
                    Subunit oldSubUnitIdOfJobcategoriesListNewJobcategories = jobcategoriesListNewJobcategories.getSubUnitId();
                    jobcategoriesListNewJobcategories.setSubUnitId(subunit);
                    jobcategoriesListNewJobcategories = em.merge(jobcategoriesListNewJobcategories);
                    if (oldSubUnitIdOfJobcategoriesListNewJobcategories != null && !oldSubUnitIdOfJobcategoriesListNewJobcategories.equals(subunit)) {
                        oldSubUnitIdOfJobcategoriesListNewJobcategories.getJobcategoriesList().remove(jobcategoriesListNewJobcategories);
                        oldSubUnitIdOfJobcategoriesListNewJobcategories = em.merge(oldSubUnitIdOfJobcategoriesListNewJobcategories);
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
                Integer id = subunit.getIdSubUnit();
                if (findSubunit(id) == null) {
                    throw new NonexistentEntityException("The subunit with id " + id + " no longer exists.");
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
            Subunit subunit;
            try {
                subunit = em.getReference(Subunit.class, id);
                subunit.getIdSubUnit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subunit with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Jobcategories> jobcategoriesListOrphanCheck = subunit.getJobcategoriesList();
            for (Jobcategories jobcategoriesListOrphanCheckJobcategories : jobcategoriesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subunit (" + subunit + ") cannot be destroyed since the Jobcategories " + jobcategoriesListOrphanCheckJobcategories + " in its jobcategoriesList field has a non-nullable subUnitId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Locations locationId = subunit.getLocationId();
            if (locationId != null) {
                locationId.getSubunitList().remove(subunit);
                locationId = em.merge(locationId);
            }
            em.remove(subunit);
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

    public List<Subunit> findSubunitEntities() {
        return findSubunitEntities(true, -1, -1);
    }

    public List<Subunit> findSubunitEntities(int maxResults, int firstResult) {
        return findSubunitEntities(false, maxResults, firstResult);
    }

    private List<Subunit> findSubunitEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subunit.class));
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

    public Subunit findSubunit(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subunit.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubunitCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subunit> rt = cq.from(Subunit.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
