/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import Entity.Locations;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Organization;
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
public class LocationsJpaController implements Serializable {

    public LocationsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Locations locations) throws RollbackFailureException, Exception {
        if (locations.getSubunitList() == null) {
            locations.setSubunitList(new ArrayList<Subunit>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organization organizationId = locations.getOrganizationId();
            if (organizationId != null) {
                organizationId = em.getReference(organizationId.getClass(), organizationId.getIdOrganization());
                locations.setOrganizationId(organizationId);
            }
            List<Subunit> attachedSubunitList = new ArrayList<Subunit>();
            for (Subunit subunitListSubunitToAttach : locations.getSubunitList()) {
                subunitListSubunitToAttach = em.getReference(subunitListSubunitToAttach.getClass(), subunitListSubunitToAttach.getIdSubUnit());
                attachedSubunitList.add(subunitListSubunitToAttach);
            }
            locations.setSubunitList(attachedSubunitList);
            em.persist(locations);
            if (organizationId != null) {
                organizationId.getLocationsList().add(locations);
                organizationId = em.merge(organizationId);
            }
            for (Subunit subunitListSubunit : locations.getSubunitList()) {
                Locations oldLocationIdOfSubunitListSubunit = subunitListSubunit.getLocationId();
                subunitListSubunit.setLocationId(locations);
                subunitListSubunit = em.merge(subunitListSubunit);
                if (oldLocationIdOfSubunitListSubunit != null) {
                    oldLocationIdOfSubunitListSubunit.getSubunitList().remove(subunitListSubunit);
                    oldLocationIdOfSubunitListSubunit = em.merge(oldLocationIdOfSubunitListSubunit);
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

    public void edit(Locations locations) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Locations persistentLocations = em.find(Locations.class, locations.getIdLocations());
            Organization organizationIdOld = persistentLocations.getOrganizationId();
            Organization organizationIdNew = locations.getOrganizationId();
            List<Subunit> subunitListOld = persistentLocations.getSubunitList();
            List<Subunit> subunitListNew = locations.getSubunitList();
            List<String> illegalOrphanMessages = null;
            for (Subunit subunitListOldSubunit : subunitListOld) {
                if (!subunitListNew.contains(subunitListOldSubunit)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Subunit " + subunitListOldSubunit + " since its locationId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (organizationIdNew != null) {
                organizationIdNew = em.getReference(organizationIdNew.getClass(), organizationIdNew.getIdOrganization());
                locations.setOrganizationId(organizationIdNew);
            }
            List<Subunit> attachedSubunitListNew = new ArrayList<Subunit>();
            for (Subunit subunitListNewSubunitToAttach : subunitListNew) {
                subunitListNewSubunitToAttach = em.getReference(subunitListNewSubunitToAttach.getClass(), subunitListNewSubunitToAttach.getIdSubUnit());
                attachedSubunitListNew.add(subunitListNewSubunitToAttach);
            }
            subunitListNew = attachedSubunitListNew;
            locations.setSubunitList(subunitListNew);
            locations = em.merge(locations);
            if (organizationIdOld != null && !organizationIdOld.equals(organizationIdNew)) {
                organizationIdOld.getLocationsList().remove(locations);
                organizationIdOld = em.merge(organizationIdOld);
            }
            if (organizationIdNew != null && !organizationIdNew.equals(organizationIdOld)) {
                organizationIdNew.getLocationsList().add(locations);
                organizationIdNew = em.merge(organizationIdNew);
            }
            for (Subunit subunitListNewSubunit : subunitListNew) {
                if (!subunitListOld.contains(subunitListNewSubunit)) {
                    Locations oldLocationIdOfSubunitListNewSubunit = subunitListNewSubunit.getLocationId();
                    subunitListNewSubunit.setLocationId(locations);
                    subunitListNewSubunit = em.merge(subunitListNewSubunit);
                    if (oldLocationIdOfSubunitListNewSubunit != null && !oldLocationIdOfSubunitListNewSubunit.equals(locations)) {
                        oldLocationIdOfSubunitListNewSubunit.getSubunitList().remove(subunitListNewSubunit);
                        oldLocationIdOfSubunitListNewSubunit = em.merge(oldLocationIdOfSubunitListNewSubunit);
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
                Integer id = locations.getIdLocations();
                if (findLocations(id) == null) {
                    throw new NonexistentEntityException("The locations with id " + id + " no longer exists.");
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
            Locations locations;
            try {
                locations = em.getReference(Locations.class, id);
                locations.getIdLocations();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The locations with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Subunit> subunitListOrphanCheck = locations.getSubunitList();
            for (Subunit subunitListOrphanCheckSubunit : subunitListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Locations (" + locations + ") cannot be destroyed since the Subunit " + subunitListOrphanCheckSubunit + " in its subunitList field has a non-nullable locationId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Organization organizationId = locations.getOrganizationId();
            if (organizationId != null) {
                organizationId.getLocationsList().remove(locations);
                organizationId = em.merge(organizationId);
            }
            em.remove(locations);
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

    public List<Locations> findLocationsEntities() {
        return findLocationsEntities(true, -1, -1);
    }

    public List<Locations> findLocationsEntities(int maxResults, int firstResult) {
        return findLocationsEntities(false, maxResults, firstResult);
    }

    private List<Locations> findLocationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Locations.class));
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

    public Locations findLocations(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Locations.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Locations> rt = cq.from(Locations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
