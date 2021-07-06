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
import Entity.Timesheet;
import java.util.ArrayList;
import java.util.List;
import Entity.Activities;
import Entity.Projectadmins;
import Entity.Projects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class ProjectsJpaController implements Serializable {

    public ProjectsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Projects projects) throws RollbackFailureException, Exception {
        if (projects.getTimesheetList() == null) {
            projects.setTimesheetList(new ArrayList<Timesheet>());
        }
        if (projects.getActivitiesList() == null) {
            projects.setActivitiesList(new ArrayList<Activities>());
        }
        if (projects.getProjectadminsList() == null) {
            projects.setProjectadminsList(new ArrayList<Projectadmins>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organization organizationId = projects.getOrganizationId();
            if (organizationId != null) {
                organizationId = em.getReference(organizationId.getClass(), organizationId.getIdOrganization());
                projects.setOrganizationId(organizationId);
            }
            List<Timesheet> attachedTimesheetList = new ArrayList<Timesheet>();
            for (Timesheet timesheetListTimesheetToAttach : projects.getTimesheetList()) {
                timesheetListTimesheetToAttach = em.getReference(timesheetListTimesheetToAttach.getClass(), timesheetListTimesheetToAttach.getIdTimesheet());
                attachedTimesheetList.add(timesheetListTimesheetToAttach);
            }
            projects.setTimesheetList(attachedTimesheetList);
            List<Activities> attachedActivitiesList = new ArrayList<Activities>();
            for (Activities activitiesListActivitiesToAttach : projects.getActivitiesList()) {
                activitiesListActivitiesToAttach = em.getReference(activitiesListActivitiesToAttach.getClass(), activitiesListActivitiesToAttach.getIdActivities());
                attachedActivitiesList.add(activitiesListActivitiesToAttach);
            }
            projects.setActivitiesList(attachedActivitiesList);
            List<Projectadmins> attachedProjectadminsList = new ArrayList<Projectadmins>();
            for (Projectadmins projectadminsListProjectadminsToAttach : projects.getProjectadminsList()) {
                projectadminsListProjectadminsToAttach = em.getReference(projectadminsListProjectadminsToAttach.getClass(), projectadminsListProjectadminsToAttach.getIdProjectAdmins());
                attachedProjectadminsList.add(projectadminsListProjectadminsToAttach);
            }
            projects.setProjectadminsList(attachedProjectadminsList);
            em.persist(projects);
            if (organizationId != null) {
                organizationId.getProjectsList().add(projects);
                organizationId = em.merge(organizationId);
            }
            for (Timesheet timesheetListTimesheet : projects.getTimesheetList()) {
                Projects oldProjectIdOfTimesheetListTimesheet = timesheetListTimesheet.getProjectId();
                timesheetListTimesheet.setProjectId(projects);
                timesheetListTimesheet = em.merge(timesheetListTimesheet);
                if (oldProjectIdOfTimesheetListTimesheet != null) {
                    oldProjectIdOfTimesheetListTimesheet.getTimesheetList().remove(timesheetListTimesheet);
                    oldProjectIdOfTimesheetListTimesheet = em.merge(oldProjectIdOfTimesheetListTimesheet);
                }
            }
            for (Activities activitiesListActivities : projects.getActivitiesList()) {
                Projects oldProjectIdOfActivitiesListActivities = activitiesListActivities.getProjectId();
                activitiesListActivities.setProjectId(projects);
                activitiesListActivities = em.merge(activitiesListActivities);
                if (oldProjectIdOfActivitiesListActivities != null) {
                    oldProjectIdOfActivitiesListActivities.getActivitiesList().remove(activitiesListActivities);
                    oldProjectIdOfActivitiesListActivities = em.merge(oldProjectIdOfActivitiesListActivities);
                }
            }
            for (Projectadmins projectadminsListProjectadmins : projects.getProjectadminsList()) {
                Projects oldPorjectIdOfProjectadminsListProjectadmins = projectadminsListProjectadmins.getPorjectId();
                projectadminsListProjectadmins.setPorjectId(projects);
                projectadminsListProjectadmins = em.merge(projectadminsListProjectadmins);
                if (oldPorjectIdOfProjectadminsListProjectadmins != null) {
                    oldPorjectIdOfProjectadminsListProjectadmins.getProjectadminsList().remove(projectadminsListProjectadmins);
                    oldPorjectIdOfProjectadminsListProjectadmins = em.merge(oldPorjectIdOfProjectadminsListProjectadmins);
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

    public void edit(Projects projects) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Projects persistentProjects = em.find(Projects.class, projects.getIdProjects());
            Organization organizationIdOld = persistentProjects.getOrganizationId();
            Organization organizationIdNew = projects.getOrganizationId();
            List<Timesheet> timesheetListOld = persistentProjects.getTimesheetList();
            List<Timesheet> timesheetListNew = projects.getTimesheetList();
            List<Activities> activitiesListOld = persistentProjects.getActivitiesList();
            List<Activities> activitiesListNew = projects.getActivitiesList();
            List<Projectadmins> projectadminsListOld = persistentProjects.getProjectadminsList();
            List<Projectadmins> projectadminsListNew = projects.getProjectadminsList();
            List<String> illegalOrphanMessages = null;
            for (Timesheet timesheetListOldTimesheet : timesheetListOld) {
                if (!timesheetListNew.contains(timesheetListOldTimesheet)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Timesheet " + timesheetListOldTimesheet + " since its projectId field is not nullable.");
                }
            }
            for (Activities activitiesListOldActivities : activitiesListOld) {
                if (!activitiesListNew.contains(activitiesListOldActivities)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Activities " + activitiesListOldActivities + " since its projectId field is not nullable.");
                }
            }
            for (Projectadmins projectadminsListOldProjectadmins : projectadminsListOld) {
                if (!projectadminsListNew.contains(projectadminsListOldProjectadmins)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Projectadmins " + projectadminsListOldProjectadmins + " since its porjectId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (organizationIdNew != null) {
                organizationIdNew = em.getReference(organizationIdNew.getClass(), organizationIdNew.getIdOrganization());
                projects.setOrganizationId(organizationIdNew);
            }
            List<Timesheet> attachedTimesheetListNew = new ArrayList<Timesheet>();
            for (Timesheet timesheetListNewTimesheetToAttach : timesheetListNew) {
                timesheetListNewTimesheetToAttach = em.getReference(timesheetListNewTimesheetToAttach.getClass(), timesheetListNewTimesheetToAttach.getIdTimesheet());
                attachedTimesheetListNew.add(timesheetListNewTimesheetToAttach);
            }
            timesheetListNew = attachedTimesheetListNew;
            projects.setTimesheetList(timesheetListNew);
            List<Activities> attachedActivitiesListNew = new ArrayList<Activities>();
            for (Activities activitiesListNewActivitiesToAttach : activitiesListNew) {
                activitiesListNewActivitiesToAttach = em.getReference(activitiesListNewActivitiesToAttach.getClass(), activitiesListNewActivitiesToAttach.getIdActivities());
                attachedActivitiesListNew.add(activitiesListNewActivitiesToAttach);
            }
            activitiesListNew = attachedActivitiesListNew;
            projects.setActivitiesList(activitiesListNew);
            List<Projectadmins> attachedProjectadminsListNew = new ArrayList<Projectadmins>();
            for (Projectadmins projectadminsListNewProjectadminsToAttach : projectadminsListNew) {
                projectadminsListNewProjectadminsToAttach = em.getReference(projectadminsListNewProjectadminsToAttach.getClass(), projectadminsListNewProjectadminsToAttach.getIdProjectAdmins());
                attachedProjectadminsListNew.add(projectadminsListNewProjectadminsToAttach);
            }
            projectadminsListNew = attachedProjectadminsListNew;
            projects.setProjectadminsList(projectadminsListNew);
            projects = em.merge(projects);
            if (organizationIdOld != null && !organizationIdOld.equals(organizationIdNew)) {
                organizationIdOld.getProjectsList().remove(projects);
                organizationIdOld = em.merge(organizationIdOld);
            }
            if (organizationIdNew != null && !organizationIdNew.equals(organizationIdOld)) {
                organizationIdNew.getProjectsList().add(projects);
                organizationIdNew = em.merge(organizationIdNew);
            }
            for (Timesheet timesheetListNewTimesheet : timesheetListNew) {
                if (!timesheetListOld.contains(timesheetListNewTimesheet)) {
                    Projects oldProjectIdOfTimesheetListNewTimesheet = timesheetListNewTimesheet.getProjectId();
                    timesheetListNewTimesheet.setProjectId(projects);
                    timesheetListNewTimesheet = em.merge(timesheetListNewTimesheet);
                    if (oldProjectIdOfTimesheetListNewTimesheet != null && !oldProjectIdOfTimesheetListNewTimesheet.equals(projects)) {
                        oldProjectIdOfTimesheetListNewTimesheet.getTimesheetList().remove(timesheetListNewTimesheet);
                        oldProjectIdOfTimesheetListNewTimesheet = em.merge(oldProjectIdOfTimesheetListNewTimesheet);
                    }
                }
            }
            for (Activities activitiesListNewActivities : activitiesListNew) {
                if (!activitiesListOld.contains(activitiesListNewActivities)) {
                    Projects oldProjectIdOfActivitiesListNewActivities = activitiesListNewActivities.getProjectId();
                    activitiesListNewActivities.setProjectId(projects);
                    activitiesListNewActivities = em.merge(activitiesListNewActivities);
                    if (oldProjectIdOfActivitiesListNewActivities != null && !oldProjectIdOfActivitiesListNewActivities.equals(projects)) {
                        oldProjectIdOfActivitiesListNewActivities.getActivitiesList().remove(activitiesListNewActivities);
                        oldProjectIdOfActivitiesListNewActivities = em.merge(oldProjectIdOfActivitiesListNewActivities);
                    }
                }
            }
            for (Projectadmins projectadminsListNewProjectadmins : projectadminsListNew) {
                if (!projectadminsListOld.contains(projectadminsListNewProjectadmins)) {
                    Projects oldPorjectIdOfProjectadminsListNewProjectadmins = projectadminsListNewProjectadmins.getPorjectId();
                    projectadminsListNewProjectadmins.setPorjectId(projects);
                    projectadminsListNewProjectadmins = em.merge(projectadminsListNewProjectadmins);
                    if (oldPorjectIdOfProjectadminsListNewProjectadmins != null && !oldPorjectIdOfProjectadminsListNewProjectadmins.equals(projects)) {
                        oldPorjectIdOfProjectadminsListNewProjectadmins.getProjectadminsList().remove(projectadminsListNewProjectadmins);
                        oldPorjectIdOfProjectadminsListNewProjectadmins = em.merge(oldPorjectIdOfProjectadminsListNewProjectadmins);
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
                Integer id = projects.getIdProjects();
                if (findProjects(id) == null) {
                    throw new NonexistentEntityException("The projects with id " + id + " no longer exists.");
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
            Projects projects;
            try {
                projects = em.getReference(Projects.class, id);
                projects.getIdProjects();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projects with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Timesheet> timesheetListOrphanCheck = projects.getTimesheetList();
            for (Timesheet timesheetListOrphanCheckTimesheet : timesheetListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Projects (" + projects + ") cannot be destroyed since the Timesheet " + timesheetListOrphanCheckTimesheet + " in its timesheetList field has a non-nullable projectId field.");
            }
            List<Activities> activitiesListOrphanCheck = projects.getActivitiesList();
            for (Activities activitiesListOrphanCheckActivities : activitiesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Projects (" + projects + ") cannot be destroyed since the Activities " + activitiesListOrphanCheckActivities + " in its activitiesList field has a non-nullable projectId field.");
            }
            List<Projectadmins> projectadminsListOrphanCheck = projects.getProjectadminsList();
            for (Projectadmins projectadminsListOrphanCheckProjectadmins : projectadminsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Projects (" + projects + ") cannot be destroyed since the Projectadmins " + projectadminsListOrphanCheckProjectadmins + " in its projectadminsList field has a non-nullable porjectId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Organization organizationId = projects.getOrganizationId();
            if (organizationId != null) {
                organizationId.getProjectsList().remove(projects);
                organizationId = em.merge(organizationId);
            }
            em.remove(projects);
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

    public List<Projects> findProjectsEntities() {
        return findProjectsEntities(true, -1, -1);
    }

    public List<Projects> findProjectsEntities(int maxResults, int firstResult) {
        return findProjectsEntities(false, maxResults, firstResult);
    }

    private List<Projects> findProjectsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projects.class));
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

    public Projects findProjects(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projects.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projects> rt = cq.from(Projects.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
