/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Organization;
import Entity.Structure;
import Entity.StructurePK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class StructureJpaController implements Serializable {

    public StructureJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Structure structure) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (structure.getStructurePK() == null) {
            structure.setStructurePK(new StructurePK());
        }
        if (structure.getStructureList() == null) {
            structure.setStructureList(new ArrayList<Structure>());
        }
        structure.getStructurePK().setOrganizationId(structure.getOrganization().getIdOrganization());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organization organization = structure.getOrganization();
            if (organization != null) {
                organization = em.getReference(organization.getClass(), organization.getIdOrganization());
                structure.setOrganization(organization);
            }
            Structure subStructureId = structure.getSubStructureId();
            if (subStructureId != null) {
                subStructureId = em.getReference(subStructureId.getClass(), subStructureId.getStructurePK());
                structure.setSubStructureId(subStructureId);
            }
            List<Structure> attachedStructureList = new ArrayList<Structure>();
            for (Structure structureListStructureToAttach : structure.getStructureList()) {
                structureListStructureToAttach = em.getReference(structureListStructureToAttach.getClass(), structureListStructureToAttach.getStructurePK());
                attachedStructureList.add(structureListStructureToAttach);
            }
            structure.setStructureList(attachedStructureList);
            em.persist(structure);
            if (organization != null) {
                organization.getStructureList().add(structure);
                organization = em.merge(organization);
            }
            if (subStructureId != null) {
                subStructureId.getStructureList().add(structure);
                subStructureId = em.merge(subStructureId);
            }
            for (Structure structureListStructure : structure.getStructureList()) {
                Structure oldSubStructureIdOfStructureListStructure = structureListStructure.getSubStructureId();
                structureListStructure.setSubStructureId(structure);
                structureListStructure = em.merge(structureListStructure);
                if (oldSubStructureIdOfStructureListStructure != null) {
                    oldSubStructureIdOfStructureListStructure.getStructureList().remove(structureListStructure);
                    oldSubStructureIdOfStructureListStructure = em.merge(oldSubStructureIdOfStructureListStructure);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findStructure(structure.getStructurePK()) != null) {
                throw new PreexistingEntityException("Structure " + structure + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Structure structure) throws NonexistentEntityException, RollbackFailureException, Exception {
        structure.getStructurePK().setOrganizationId(structure.getOrganization().getIdOrganization());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Structure persistentStructure = em.find(Structure.class, structure.getStructurePK());
            Organization organizationOld = persistentStructure.getOrganization();
            Organization organizationNew = structure.getOrganization();
            Structure subStructureIdOld = persistentStructure.getSubStructureId();
            Structure subStructureIdNew = structure.getSubStructureId();
            List<Structure> structureListOld = persistentStructure.getStructureList();
            List<Structure> structureListNew = structure.getStructureList();
            if (organizationNew != null) {
                organizationNew = em.getReference(organizationNew.getClass(), organizationNew.getIdOrganization());
                structure.setOrganization(organizationNew);
            }
            if (subStructureIdNew != null) {
                subStructureIdNew = em.getReference(subStructureIdNew.getClass(), subStructureIdNew.getStructurePK());
                structure.setSubStructureId(subStructureIdNew);
            }
            List<Structure> attachedStructureListNew = new ArrayList<Structure>();
            for (Structure structureListNewStructureToAttach : structureListNew) {
                structureListNewStructureToAttach = em.getReference(structureListNewStructureToAttach.getClass(), structureListNewStructureToAttach.getStructurePK());
                attachedStructureListNew.add(structureListNewStructureToAttach);
            }
            structureListNew = attachedStructureListNew;
            structure.setStructureList(structureListNew);
            structure = em.merge(structure);
            if (organizationOld != null && !organizationOld.equals(organizationNew)) {
                organizationOld.getStructureList().remove(structure);
                organizationOld = em.merge(organizationOld);
            }
            if (organizationNew != null && !organizationNew.equals(organizationOld)) {
                organizationNew.getStructureList().add(structure);
                organizationNew = em.merge(organizationNew);
            }
            if (subStructureIdOld != null && !subStructureIdOld.equals(subStructureIdNew)) {
                subStructureIdOld.getStructureList().remove(structure);
                subStructureIdOld = em.merge(subStructureIdOld);
            }
            if (subStructureIdNew != null && !subStructureIdNew.equals(subStructureIdOld)) {
                subStructureIdNew.getStructureList().add(structure);
                subStructureIdNew = em.merge(subStructureIdNew);
            }
            for (Structure structureListOldStructure : structureListOld) {
                if (!structureListNew.contains(structureListOldStructure)) {
                    structureListOldStructure.setSubStructureId(null);
                    structureListOldStructure = em.merge(structureListOldStructure);
                }
            }
            for (Structure structureListNewStructure : structureListNew) {
                if (!structureListOld.contains(structureListNewStructure)) {
                    Structure oldSubStructureIdOfStructureListNewStructure = structureListNewStructure.getSubStructureId();
                    structureListNewStructure.setSubStructureId(structure);
                    structureListNewStructure = em.merge(structureListNewStructure);
                    if (oldSubStructureIdOfStructureListNewStructure != null && !oldSubStructureIdOfStructureListNewStructure.equals(structure)) {
                        oldSubStructureIdOfStructureListNewStructure.getStructureList().remove(structureListNewStructure);
                        oldSubStructureIdOfStructureListNewStructure = em.merge(oldSubStructureIdOfStructureListNewStructure);
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
                StructurePK id = structure.getStructurePK();
                if (findStructure(id) == null) {
                    throw new NonexistentEntityException("The structure with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(StructurePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Structure structure;
            try {
                structure = em.getReference(Structure.class, id);
                structure.getStructurePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The structure with id " + id + " no longer exists.", enfe);
            }
            Organization organization = structure.getOrganization();
            if (organization != null) {
                organization.getStructureList().remove(structure);
                organization = em.merge(organization);
            }
            Structure subStructureId = structure.getSubStructureId();
            if (subStructureId != null) {
                subStructureId.getStructureList().remove(structure);
                subStructureId = em.merge(subStructureId);
            }
            List<Structure> structureList = structure.getStructureList();
            for (Structure structureListStructure : structureList) {
                structureListStructure.setSubStructureId(null);
                structureListStructure = em.merge(structureListStructure);
            }
            em.remove(structure);
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

    public List<Structure> findStructureEntities() {
        return findStructureEntities(true, -1, -1);
    }

    public List<Structure> findStructureEntities(int maxResults, int firstResult) {
        return findStructureEntities(false, maxResults, firstResult);
    }

    private List<Structure> findStructureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Structure.class));
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

    public Structure findStructure(StructurePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Structure.class, id);
        } finally {
            em.close();
        }
    }

    public int getStructureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Structure> rt = cq.from(Structure.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
