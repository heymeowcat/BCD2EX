/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import Entity.Activities;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Projects;
import Entity.Assignedemployees;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class ActivitiesJpaController implements Serializable {

    public ActivitiesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Activities activities) throws RollbackFailureException, Exception {
        if (activities.getAssignedemployeesList() == null) {
            activities.setAssignedemployeesList(new ArrayList<Assignedemployees>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Projects projectId = activities.getProjectId();
            if (projectId != null) {
                projectId = em.getReference(projectId.getClass(), projectId.getIdProjects());
                activities.setProjectId(projectId);
            }
            List<Assignedemployees> attachedAssignedemployeesList = new ArrayList<Assignedemployees>();
            for (Assignedemployees assignedemployeesListAssignedemployeesToAttach : activities.getAssignedemployeesList()) {
                assignedemployeesListAssignedemployeesToAttach = em.getReference(assignedemployeesListAssignedemployeesToAttach.getClass(), assignedemployeesListAssignedemployeesToAttach.getIdAssignedEmployees());
                attachedAssignedemployeesList.add(assignedemployeesListAssignedemployeesToAttach);
            }
            activities.setAssignedemployeesList(attachedAssignedemployeesList);
            em.persist(activities);
            if (projectId != null) {
                projectId.getActivitiesList().add(activities);
                projectId = em.merge(projectId);
            }
            for (Assignedemployees assignedemployeesListAssignedemployees : activities.getAssignedemployeesList()) {
                Activities oldActivityIdOfAssignedemployeesListAssignedemployees = assignedemployeesListAssignedemployees.getActivityId();
                assignedemployeesListAssignedemployees.setActivityId(activities);
                assignedemployeesListAssignedemployees = em.merge(assignedemployeesListAssignedemployees);
                if (oldActivityIdOfAssignedemployeesListAssignedemployees != null) {
                    oldActivityIdOfAssignedemployeesListAssignedemployees.getAssignedemployeesList().remove(assignedemployeesListAssignedemployees);
                    oldActivityIdOfAssignedemployeesListAssignedemployees = em.merge(oldActivityIdOfAssignedemployeesListAssignedemployees);
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

    public void edit(Activities activities) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Activities persistentActivities = em.find(Activities.class, activities.getIdActivities());
            Projects projectIdOld = persistentActivities.getProjectId();
            Projects projectIdNew = activities.getProjectId();
            List<Assignedemployees> assignedemployeesListOld = persistentActivities.getAssignedemployeesList();
            List<Assignedemployees> assignedemployeesListNew = activities.getAssignedemployeesList();
            List<String> illegalOrphanMessages = null;
            for (Assignedemployees assignedemployeesListOldAssignedemployees : assignedemployeesListOld) {
                if (!assignedemployeesListNew.contains(assignedemployeesListOldAssignedemployees)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Assignedemployees " + assignedemployeesListOldAssignedemployees + " since its activityId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (projectIdNew != null) {
                projectIdNew = em.getReference(projectIdNew.getClass(), projectIdNew.getIdProjects());
                activities.setProjectId(projectIdNew);
            }
            List<Assignedemployees> attachedAssignedemployeesListNew = new ArrayList<Assignedemployees>();
            for (Assignedemployees assignedemployeesListNewAssignedemployeesToAttach : assignedemployeesListNew) {
                assignedemployeesListNewAssignedemployeesToAttach = em.getReference(assignedemployeesListNewAssignedemployeesToAttach.getClass(), assignedemployeesListNewAssignedemployeesToAttach.getIdAssignedEmployees());
                attachedAssignedemployeesListNew.add(assignedemployeesListNewAssignedemployeesToAttach);
            }
            assignedemployeesListNew = attachedAssignedemployeesListNew;
            activities.setAssignedemployeesList(assignedemployeesListNew);
            activities = em.merge(activities);
            if (projectIdOld != null && !projectIdOld.equals(projectIdNew)) {
                projectIdOld.getActivitiesList().remove(activities);
                projectIdOld = em.merge(projectIdOld);
            }
            if (projectIdNew != null && !projectIdNew.equals(projectIdOld)) {
                projectIdNew.getActivitiesList().add(activities);
                projectIdNew = em.merge(projectIdNew);
            }
            for (Assignedemployees assignedemployeesListNewAssignedemployees : assignedemployeesListNew) {
                if (!assignedemployeesListOld.contains(assignedemployeesListNewAssignedemployees)) {
                    Activities oldActivityIdOfAssignedemployeesListNewAssignedemployees = assignedemployeesListNewAssignedemployees.getActivityId();
                    assignedemployeesListNewAssignedemployees.setActivityId(activities);
                    assignedemployeesListNewAssignedemployees = em.merge(assignedemployeesListNewAssignedemployees);
                    if (oldActivityIdOfAssignedemployeesListNewAssignedemployees != null && !oldActivityIdOfAssignedemployeesListNewAssignedemployees.equals(activities)) {
                        oldActivityIdOfAssignedemployeesListNewAssignedemployees.getAssignedemployeesList().remove(assignedemployeesListNewAssignedemployees);
                        oldActivityIdOfAssignedemployeesListNewAssignedemployees = em.merge(oldActivityIdOfAssignedemployeesListNewAssignedemployees);
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
                Integer id = activities.getIdActivities();
                if (findActivities(id) == null) {
                    throw new NonexistentEntityException("The activities with id " + id + " no longer exists.");
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
            Activities activities;
            try {
                activities = em.getReference(Activities.class, id);
                activities.getIdActivities();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The activities with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Assignedemployees> assignedemployeesListOrphanCheck = activities.getAssignedemployeesList();
            for (Assignedemployees assignedemployeesListOrphanCheckAssignedemployees : assignedemployeesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Activities (" + activities + ") cannot be destroyed since the Assignedemployees " + assignedemployeesListOrphanCheckAssignedemployees + " in its assignedemployeesList field has a non-nullable activityId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Projects projectId = activities.getProjectId();
            if (projectId != null) {
                projectId.getActivitiesList().remove(activities);
                projectId = em.merge(projectId);
            }
            em.remove(activities);
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

    public List<Activities> findActivitiesEntities() {
        return findActivitiesEntities(true, -1, -1);
    }

    public List<Activities> findActivitiesEntities(int maxResults, int firstResult) {
        return findActivitiesEntities(false, maxResults, firstResult);
    }

    private List<Activities> findActivitiesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Activities.class));
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

    public Activities findActivities(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Activities.class, id);
        } finally {
            em.close();
        }
    }

    public int getActivitiesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Activities> rt = cq.from(Activities.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
