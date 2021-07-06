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
import Entity.Leavetypes;
import java.util.ArrayList;
import java.util.List;
import Entity.Projects;
import Entity.Leaveactions;
import Entity.Structure;
import Entity.Leavestatus;
import Entity.Locations;
import Entity.Organization;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class OrganizationJpaController implements Serializable {

    public OrganizationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Organization organization) throws RollbackFailureException, Exception {
        if (organization.getLeavetypesList() == null) {
            organization.setLeavetypesList(new ArrayList<Leavetypes>());
        }
        if (organization.getProjectsList() == null) {
            organization.setProjectsList(new ArrayList<Projects>());
        }
        if (organization.getLeaveactionsList() == null) {
            organization.setLeaveactionsList(new ArrayList<Leaveactions>());
        }
        if (organization.getStructureList() == null) {
            organization.setStructureList(new ArrayList<Structure>());
        }
        if (organization.getLeavestatusList() == null) {
            organization.setLeavestatusList(new ArrayList<Leavestatus>());
        }
        if (organization.getLocationsList() == null) {
            organization.setLocationsList(new ArrayList<Locations>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Leavetypes> attachedLeavetypesList = new ArrayList<Leavetypes>();
            for (Leavetypes leavetypesListLeavetypesToAttach : organization.getLeavetypesList()) {
                leavetypesListLeavetypesToAttach = em.getReference(leavetypesListLeavetypesToAttach.getClass(), leavetypesListLeavetypesToAttach.getIdLeaveTypes());
                attachedLeavetypesList.add(leavetypesListLeavetypesToAttach);
            }
            organization.setLeavetypesList(attachedLeavetypesList);
            List<Projects> attachedProjectsList = new ArrayList<Projects>();
            for (Projects projectsListProjectsToAttach : organization.getProjectsList()) {
                projectsListProjectsToAttach = em.getReference(projectsListProjectsToAttach.getClass(), projectsListProjectsToAttach.getIdProjects());
                attachedProjectsList.add(projectsListProjectsToAttach);
            }
            organization.setProjectsList(attachedProjectsList);
            List<Leaveactions> attachedLeaveactionsList = new ArrayList<Leaveactions>();
            for (Leaveactions leaveactionsListLeaveactionsToAttach : organization.getLeaveactionsList()) {
                leaveactionsListLeaveactionsToAttach = em.getReference(leaveactionsListLeaveactionsToAttach.getClass(), leaveactionsListLeaveactionsToAttach.getIdLeaveActions());
                attachedLeaveactionsList.add(leaveactionsListLeaveactionsToAttach);
            }
            organization.setLeaveactionsList(attachedLeaveactionsList);
            List<Structure> attachedStructureList = new ArrayList<Structure>();
            for (Structure structureListStructureToAttach : organization.getStructureList()) {
                structureListStructureToAttach = em.getReference(structureListStructureToAttach.getClass(), structureListStructureToAttach.getStructurePK());
                attachedStructureList.add(structureListStructureToAttach);
            }
            organization.setStructureList(attachedStructureList);
            List<Leavestatus> attachedLeavestatusList = new ArrayList<Leavestatus>();
            for (Leavestatus leavestatusListLeavestatusToAttach : organization.getLeavestatusList()) {
                leavestatusListLeavestatusToAttach = em.getReference(leavestatusListLeavestatusToAttach.getClass(), leavestatusListLeavestatusToAttach.getIdLeaveStatus());
                attachedLeavestatusList.add(leavestatusListLeavestatusToAttach);
            }
            organization.setLeavestatusList(attachedLeavestatusList);
            List<Locations> attachedLocationsList = new ArrayList<Locations>();
            for (Locations locationsListLocationsToAttach : organization.getLocationsList()) {
                locationsListLocationsToAttach = em.getReference(locationsListLocationsToAttach.getClass(), locationsListLocationsToAttach.getIdLocations());
                attachedLocationsList.add(locationsListLocationsToAttach);
            }
            organization.setLocationsList(attachedLocationsList);
            em.persist(organization);
            for (Leavetypes leavetypesListLeavetypes : organization.getLeavetypesList()) {
                Organization oldOrganizationIdOfLeavetypesListLeavetypes = leavetypesListLeavetypes.getOrganizationId();
                leavetypesListLeavetypes.setOrganizationId(organization);
                leavetypesListLeavetypes = em.merge(leavetypesListLeavetypes);
                if (oldOrganizationIdOfLeavetypesListLeavetypes != null) {
                    oldOrganizationIdOfLeavetypesListLeavetypes.getLeavetypesList().remove(leavetypesListLeavetypes);
                    oldOrganizationIdOfLeavetypesListLeavetypes = em.merge(oldOrganizationIdOfLeavetypesListLeavetypes);
                }
            }
            for (Projects projectsListProjects : organization.getProjectsList()) {
                Organization oldOrganizationIdOfProjectsListProjects = projectsListProjects.getOrganizationId();
                projectsListProjects.setOrganizationId(organization);
                projectsListProjects = em.merge(projectsListProjects);
                if (oldOrganizationIdOfProjectsListProjects != null) {
                    oldOrganizationIdOfProjectsListProjects.getProjectsList().remove(projectsListProjects);
                    oldOrganizationIdOfProjectsListProjects = em.merge(oldOrganizationIdOfProjectsListProjects);
                }
            }
            for (Leaveactions leaveactionsListLeaveactions : organization.getLeaveactionsList()) {
                Organization oldOrganizationIdOfLeaveactionsListLeaveactions = leaveactionsListLeaveactions.getOrganizationId();
                leaveactionsListLeaveactions.setOrganizationId(organization);
                leaveactionsListLeaveactions = em.merge(leaveactionsListLeaveactions);
                if (oldOrganizationIdOfLeaveactionsListLeaveactions != null) {
                    oldOrganizationIdOfLeaveactionsListLeaveactions.getLeaveactionsList().remove(leaveactionsListLeaveactions);
                    oldOrganizationIdOfLeaveactionsListLeaveactions = em.merge(oldOrganizationIdOfLeaveactionsListLeaveactions);
                }
            }
            for (Structure structureListStructure : organization.getStructureList()) {
                Organization oldOrganizationOfStructureListStructure = structureListStructure.getOrganization();
                structureListStructure.setOrganization(organization);
                structureListStructure = em.merge(structureListStructure);
                if (oldOrganizationOfStructureListStructure != null) {
                    oldOrganizationOfStructureListStructure.getStructureList().remove(structureListStructure);
                    oldOrganizationOfStructureListStructure = em.merge(oldOrganizationOfStructureListStructure);
                }
            }
            for (Leavestatus leavestatusListLeavestatus : organization.getLeavestatusList()) {
                Organization oldOrganizationIdOfLeavestatusListLeavestatus = leavestatusListLeavestatus.getOrganizationId();
                leavestatusListLeavestatus.setOrganizationId(organization);
                leavestatusListLeavestatus = em.merge(leavestatusListLeavestatus);
                if (oldOrganizationIdOfLeavestatusListLeavestatus != null) {
                    oldOrganizationIdOfLeavestatusListLeavestatus.getLeavestatusList().remove(leavestatusListLeavestatus);
                    oldOrganizationIdOfLeavestatusListLeavestatus = em.merge(oldOrganizationIdOfLeavestatusListLeavestatus);
                }
            }
            for (Locations locationsListLocations : organization.getLocationsList()) {
                Organization oldOrganizationIdOfLocationsListLocations = locationsListLocations.getOrganizationId();
                locationsListLocations.setOrganizationId(organization);
                locationsListLocations = em.merge(locationsListLocations);
                if (oldOrganizationIdOfLocationsListLocations != null) {
                    oldOrganizationIdOfLocationsListLocations.getLocationsList().remove(locationsListLocations);
                    oldOrganizationIdOfLocationsListLocations = em.merge(oldOrganizationIdOfLocationsListLocations);
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

    public void edit(Organization organization) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organization persistentOrganization = em.find(Organization.class, organization.getIdOrganization());
            List<Leavetypes> leavetypesListOld = persistentOrganization.getLeavetypesList();
            List<Leavetypes> leavetypesListNew = organization.getLeavetypesList();
            List<Projects> projectsListOld = persistentOrganization.getProjectsList();
            List<Projects> projectsListNew = organization.getProjectsList();
            List<Leaveactions> leaveactionsListOld = persistentOrganization.getLeaveactionsList();
            List<Leaveactions> leaveactionsListNew = organization.getLeaveactionsList();
            List<Structure> structureListOld = persistentOrganization.getStructureList();
            List<Structure> structureListNew = organization.getStructureList();
            List<Leavestatus> leavestatusListOld = persistentOrganization.getLeavestatusList();
            List<Leavestatus> leavestatusListNew = organization.getLeavestatusList();
            List<Locations> locationsListOld = persistentOrganization.getLocationsList();
            List<Locations> locationsListNew = organization.getLocationsList();
            List<String> illegalOrphanMessages = null;
            for (Leavetypes leavetypesListOldLeavetypes : leavetypesListOld) {
                if (!leavetypesListNew.contains(leavetypesListOldLeavetypes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Leavetypes " + leavetypesListOldLeavetypes + " since its organizationId field is not nullable.");
                }
            }
            for (Projects projectsListOldProjects : projectsListOld) {
                if (!projectsListNew.contains(projectsListOldProjects)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Projects " + projectsListOldProjects + " since its organizationId field is not nullable.");
                }
            }
            for (Leaveactions leaveactionsListOldLeaveactions : leaveactionsListOld) {
                if (!leaveactionsListNew.contains(leaveactionsListOldLeaveactions)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Leaveactions " + leaveactionsListOldLeaveactions + " since its organizationId field is not nullable.");
                }
            }
            for (Structure structureListOldStructure : structureListOld) {
                if (!structureListNew.contains(structureListOldStructure)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Structure " + structureListOldStructure + " since its organization field is not nullable.");
                }
            }
            for (Leavestatus leavestatusListOldLeavestatus : leavestatusListOld) {
                if (!leavestatusListNew.contains(leavestatusListOldLeavestatus)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Leavestatus " + leavestatusListOldLeavestatus + " since its organizationId field is not nullable.");
                }
            }
            for (Locations locationsListOldLocations : locationsListOld) {
                if (!locationsListNew.contains(locationsListOldLocations)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Locations " + locationsListOldLocations + " since its organizationId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Leavetypes> attachedLeavetypesListNew = new ArrayList<Leavetypes>();
            for (Leavetypes leavetypesListNewLeavetypesToAttach : leavetypesListNew) {
                leavetypesListNewLeavetypesToAttach = em.getReference(leavetypesListNewLeavetypesToAttach.getClass(), leavetypesListNewLeavetypesToAttach.getIdLeaveTypes());
                attachedLeavetypesListNew.add(leavetypesListNewLeavetypesToAttach);
            }
            leavetypesListNew = attachedLeavetypesListNew;
            organization.setLeavetypesList(leavetypesListNew);
            List<Projects> attachedProjectsListNew = new ArrayList<Projects>();
            for (Projects projectsListNewProjectsToAttach : projectsListNew) {
                projectsListNewProjectsToAttach = em.getReference(projectsListNewProjectsToAttach.getClass(), projectsListNewProjectsToAttach.getIdProjects());
                attachedProjectsListNew.add(projectsListNewProjectsToAttach);
            }
            projectsListNew = attachedProjectsListNew;
            organization.setProjectsList(projectsListNew);
            List<Leaveactions> attachedLeaveactionsListNew = new ArrayList<Leaveactions>();
            for (Leaveactions leaveactionsListNewLeaveactionsToAttach : leaveactionsListNew) {
                leaveactionsListNewLeaveactionsToAttach = em.getReference(leaveactionsListNewLeaveactionsToAttach.getClass(), leaveactionsListNewLeaveactionsToAttach.getIdLeaveActions());
                attachedLeaveactionsListNew.add(leaveactionsListNewLeaveactionsToAttach);
            }
            leaveactionsListNew = attachedLeaveactionsListNew;
            organization.setLeaveactionsList(leaveactionsListNew);
            List<Structure> attachedStructureListNew = new ArrayList<Structure>();
            for (Structure structureListNewStructureToAttach : structureListNew) {
                structureListNewStructureToAttach = em.getReference(structureListNewStructureToAttach.getClass(), structureListNewStructureToAttach.getStructurePK());
                attachedStructureListNew.add(structureListNewStructureToAttach);
            }
            structureListNew = attachedStructureListNew;
            organization.setStructureList(structureListNew);
            List<Leavestatus> attachedLeavestatusListNew = new ArrayList<Leavestatus>();
            for (Leavestatus leavestatusListNewLeavestatusToAttach : leavestatusListNew) {
                leavestatusListNewLeavestatusToAttach = em.getReference(leavestatusListNewLeavestatusToAttach.getClass(), leavestatusListNewLeavestatusToAttach.getIdLeaveStatus());
                attachedLeavestatusListNew.add(leavestatusListNewLeavestatusToAttach);
            }
            leavestatusListNew = attachedLeavestatusListNew;
            organization.setLeavestatusList(leavestatusListNew);
            List<Locations> attachedLocationsListNew = new ArrayList<Locations>();
            for (Locations locationsListNewLocationsToAttach : locationsListNew) {
                locationsListNewLocationsToAttach = em.getReference(locationsListNewLocationsToAttach.getClass(), locationsListNewLocationsToAttach.getIdLocations());
                attachedLocationsListNew.add(locationsListNewLocationsToAttach);
            }
            locationsListNew = attachedLocationsListNew;
            organization.setLocationsList(locationsListNew);
            organization = em.merge(organization);
            for (Leavetypes leavetypesListNewLeavetypes : leavetypesListNew) {
                if (!leavetypesListOld.contains(leavetypesListNewLeavetypes)) {
                    Organization oldOrganizationIdOfLeavetypesListNewLeavetypes = leavetypesListNewLeavetypes.getOrganizationId();
                    leavetypesListNewLeavetypes.setOrganizationId(organization);
                    leavetypesListNewLeavetypes = em.merge(leavetypesListNewLeavetypes);
                    if (oldOrganizationIdOfLeavetypesListNewLeavetypes != null && !oldOrganizationIdOfLeavetypesListNewLeavetypes.equals(organization)) {
                        oldOrganizationIdOfLeavetypesListNewLeavetypes.getLeavetypesList().remove(leavetypesListNewLeavetypes);
                        oldOrganizationIdOfLeavetypesListNewLeavetypes = em.merge(oldOrganizationIdOfLeavetypesListNewLeavetypes);
                    }
                }
            }
            for (Projects projectsListNewProjects : projectsListNew) {
                if (!projectsListOld.contains(projectsListNewProjects)) {
                    Organization oldOrganizationIdOfProjectsListNewProjects = projectsListNewProjects.getOrganizationId();
                    projectsListNewProjects.setOrganizationId(organization);
                    projectsListNewProjects = em.merge(projectsListNewProjects);
                    if (oldOrganizationIdOfProjectsListNewProjects != null && !oldOrganizationIdOfProjectsListNewProjects.equals(organization)) {
                        oldOrganizationIdOfProjectsListNewProjects.getProjectsList().remove(projectsListNewProjects);
                        oldOrganizationIdOfProjectsListNewProjects = em.merge(oldOrganizationIdOfProjectsListNewProjects);
                    }
                }
            }
            for (Leaveactions leaveactionsListNewLeaveactions : leaveactionsListNew) {
                if (!leaveactionsListOld.contains(leaveactionsListNewLeaveactions)) {
                    Organization oldOrganizationIdOfLeaveactionsListNewLeaveactions = leaveactionsListNewLeaveactions.getOrganizationId();
                    leaveactionsListNewLeaveactions.setOrganizationId(organization);
                    leaveactionsListNewLeaveactions = em.merge(leaveactionsListNewLeaveactions);
                    if (oldOrganizationIdOfLeaveactionsListNewLeaveactions != null && !oldOrganizationIdOfLeaveactionsListNewLeaveactions.equals(organization)) {
                        oldOrganizationIdOfLeaveactionsListNewLeaveactions.getLeaveactionsList().remove(leaveactionsListNewLeaveactions);
                        oldOrganizationIdOfLeaveactionsListNewLeaveactions = em.merge(oldOrganizationIdOfLeaveactionsListNewLeaveactions);
                    }
                }
            }
            for (Structure structureListNewStructure : structureListNew) {
                if (!structureListOld.contains(structureListNewStructure)) {
                    Organization oldOrganizationOfStructureListNewStructure = structureListNewStructure.getOrganization();
                    structureListNewStructure.setOrganization(organization);
                    structureListNewStructure = em.merge(structureListNewStructure);
                    if (oldOrganizationOfStructureListNewStructure != null && !oldOrganizationOfStructureListNewStructure.equals(organization)) {
                        oldOrganizationOfStructureListNewStructure.getStructureList().remove(structureListNewStructure);
                        oldOrganizationOfStructureListNewStructure = em.merge(oldOrganizationOfStructureListNewStructure);
                    }
                }
            }
            for (Leavestatus leavestatusListNewLeavestatus : leavestatusListNew) {
                if (!leavestatusListOld.contains(leavestatusListNewLeavestatus)) {
                    Organization oldOrganizationIdOfLeavestatusListNewLeavestatus = leavestatusListNewLeavestatus.getOrganizationId();
                    leavestatusListNewLeavestatus.setOrganizationId(organization);
                    leavestatusListNewLeavestatus = em.merge(leavestatusListNewLeavestatus);
                    if (oldOrganizationIdOfLeavestatusListNewLeavestatus != null && !oldOrganizationIdOfLeavestatusListNewLeavestatus.equals(organization)) {
                        oldOrganizationIdOfLeavestatusListNewLeavestatus.getLeavestatusList().remove(leavestatusListNewLeavestatus);
                        oldOrganizationIdOfLeavestatusListNewLeavestatus = em.merge(oldOrganizationIdOfLeavestatusListNewLeavestatus);
                    }
                }
            }
            for (Locations locationsListNewLocations : locationsListNew) {
                if (!locationsListOld.contains(locationsListNewLocations)) {
                    Organization oldOrganizationIdOfLocationsListNewLocations = locationsListNewLocations.getOrganizationId();
                    locationsListNewLocations.setOrganizationId(organization);
                    locationsListNewLocations = em.merge(locationsListNewLocations);
                    if (oldOrganizationIdOfLocationsListNewLocations != null && !oldOrganizationIdOfLocationsListNewLocations.equals(organization)) {
                        oldOrganizationIdOfLocationsListNewLocations.getLocationsList().remove(locationsListNewLocations);
                        oldOrganizationIdOfLocationsListNewLocations = em.merge(oldOrganizationIdOfLocationsListNewLocations);
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
                Integer id = organization.getIdOrganization();
                if (findOrganization(id) == null) {
                    throw new NonexistentEntityException("The organization with id " + id + " no longer exists.");
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
            Organization organization;
            try {
                organization = em.getReference(Organization.class, id);
                organization.getIdOrganization();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The organization with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Leavetypes> leavetypesListOrphanCheck = organization.getLeavetypesList();
            for (Leavetypes leavetypesListOrphanCheckLeavetypes : leavetypesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Organization (" + organization + ") cannot be destroyed since the Leavetypes " + leavetypesListOrphanCheckLeavetypes + " in its leavetypesList field has a non-nullable organizationId field.");
            }
            List<Projects> projectsListOrphanCheck = organization.getProjectsList();
            for (Projects projectsListOrphanCheckProjects : projectsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Organization (" + organization + ") cannot be destroyed since the Projects " + projectsListOrphanCheckProjects + " in its projectsList field has a non-nullable organizationId field.");
            }
            List<Leaveactions> leaveactionsListOrphanCheck = organization.getLeaveactionsList();
            for (Leaveactions leaveactionsListOrphanCheckLeaveactions : leaveactionsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Organization (" + organization + ") cannot be destroyed since the Leaveactions " + leaveactionsListOrphanCheckLeaveactions + " in its leaveactionsList field has a non-nullable organizationId field.");
            }
            List<Structure> structureListOrphanCheck = organization.getStructureList();
            for (Structure structureListOrphanCheckStructure : structureListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Organization (" + organization + ") cannot be destroyed since the Structure " + structureListOrphanCheckStructure + " in its structureList field has a non-nullable organization field.");
            }
            List<Leavestatus> leavestatusListOrphanCheck = organization.getLeavestatusList();
            for (Leavestatus leavestatusListOrphanCheckLeavestatus : leavestatusListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Organization (" + organization + ") cannot be destroyed since the Leavestatus " + leavestatusListOrphanCheckLeavestatus + " in its leavestatusList field has a non-nullable organizationId field.");
            }
            List<Locations> locationsListOrphanCheck = organization.getLocationsList();
            for (Locations locationsListOrphanCheckLocations : locationsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Organization (" + organization + ") cannot be destroyed since the Locations " + locationsListOrphanCheckLocations + " in its locationsList field has a non-nullable organizationId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(organization);
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

    public List<Organization> findOrganizationEntities() {
        return findOrganizationEntities(true, -1, -1);
    }

    public List<Organization> findOrganizationEntities(int maxResults, int firstResult) {
        return findOrganizationEntities(false, maxResults, firstResult);
    }

    private List<Organization> findOrganizationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Organization.class));
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

    public Organization findOrganization(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Organization.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrganizationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Organization> rt = cq.from(Organization.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
