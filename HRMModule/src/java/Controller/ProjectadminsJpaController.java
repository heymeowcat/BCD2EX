/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Employees;
import Entity.Projectadmins;
import Entity.Projects;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class ProjectadminsJpaController implements Serializable {

    public ProjectadminsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Projectadmins projectadmins) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees projectAdmin = projectadmins.getProjectAdmin();
            if (projectAdmin != null) {
                projectAdmin = em.getReference(projectAdmin.getClass(), projectAdmin.getIdEmployees());
                projectadmins.setProjectAdmin(projectAdmin);
            }
            Projects porjectId = projectadmins.getPorjectId();
            if (porjectId != null) {
                porjectId = em.getReference(porjectId.getClass(), porjectId.getIdProjects());
                projectadmins.setPorjectId(porjectId);
            }
            em.persist(projectadmins);
            if (projectAdmin != null) {
                projectAdmin.getProjectadminsList().add(projectadmins);
                projectAdmin = em.merge(projectAdmin);
            }
            if (porjectId != null) {
                porjectId.getProjectadminsList().add(projectadmins);
                porjectId = em.merge(porjectId);
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

    public void edit(Projectadmins projectadmins) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Projectadmins persistentProjectadmins = em.find(Projectadmins.class, projectadmins.getIdProjectAdmins());
            Employees projectAdminOld = persistentProjectadmins.getProjectAdmin();
            Employees projectAdminNew = projectadmins.getProjectAdmin();
            Projects porjectIdOld = persistentProjectadmins.getPorjectId();
            Projects porjectIdNew = projectadmins.getPorjectId();
            if (projectAdminNew != null) {
                projectAdminNew = em.getReference(projectAdminNew.getClass(), projectAdminNew.getIdEmployees());
                projectadmins.setProjectAdmin(projectAdminNew);
            }
            if (porjectIdNew != null) {
                porjectIdNew = em.getReference(porjectIdNew.getClass(), porjectIdNew.getIdProjects());
                projectadmins.setPorjectId(porjectIdNew);
            }
            projectadmins = em.merge(projectadmins);
            if (projectAdminOld != null && !projectAdminOld.equals(projectAdminNew)) {
                projectAdminOld.getProjectadminsList().remove(projectadmins);
                projectAdminOld = em.merge(projectAdminOld);
            }
            if (projectAdminNew != null && !projectAdminNew.equals(projectAdminOld)) {
                projectAdminNew.getProjectadminsList().add(projectadmins);
                projectAdminNew = em.merge(projectAdminNew);
            }
            if (porjectIdOld != null && !porjectIdOld.equals(porjectIdNew)) {
                porjectIdOld.getProjectadminsList().remove(projectadmins);
                porjectIdOld = em.merge(porjectIdOld);
            }
            if (porjectIdNew != null && !porjectIdNew.equals(porjectIdOld)) {
                porjectIdNew.getProjectadminsList().add(projectadmins);
                porjectIdNew = em.merge(porjectIdNew);
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
                Integer id = projectadmins.getIdProjectAdmins();
                if (findProjectadmins(id) == null) {
                    throw new NonexistentEntityException("The projectadmins with id " + id + " no longer exists.");
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
            Projectadmins projectadmins;
            try {
                projectadmins = em.getReference(Projectadmins.class, id);
                projectadmins.getIdProjectAdmins();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projectadmins with id " + id + " no longer exists.", enfe);
            }
            Employees projectAdmin = projectadmins.getProjectAdmin();
            if (projectAdmin != null) {
                projectAdmin.getProjectadminsList().remove(projectadmins);
                projectAdmin = em.merge(projectAdmin);
            }
            Projects porjectId = projectadmins.getPorjectId();
            if (porjectId != null) {
                porjectId.getProjectadminsList().remove(projectadmins);
                porjectId = em.merge(porjectId);
            }
            em.remove(projectadmins);
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

    public List<Projectadmins> findProjectadminsEntities() {
        return findProjectadminsEntities(true, -1, -1);
    }

    public List<Projectadmins> findProjectadminsEntities(int maxResults, int firstResult) {
        return findProjectadminsEntities(false, maxResults, firstResult);
    }

    private List<Projectadmins> findProjectadminsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projectadmins.class));
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

    public Projectadmins findProjectadmins(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projectadmins.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectadminsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projectadmins> rt = cq.from(Projectadmins.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
