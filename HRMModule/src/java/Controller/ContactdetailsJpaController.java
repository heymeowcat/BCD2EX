/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import Entity.Contactdetails;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Employees;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class ContactdetailsJpaController implements Serializable {

    public ContactdetailsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contactdetails contactdetails) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees employeeId = contactdetails.getEmployeeId();
            if (employeeId != null) {
                employeeId = em.getReference(employeeId.getClass(), employeeId.getIdEmployees());
                contactdetails.setEmployeeId(employeeId);
            }
            em.persist(contactdetails);
            if (employeeId != null) {
                employeeId.getContactdetailsList().add(contactdetails);
                employeeId = em.merge(employeeId);
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

    public void edit(Contactdetails contactdetails) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contactdetails persistentContactdetails = em.find(Contactdetails.class, contactdetails.getIdContactDetails());
            Employees employeeIdOld = persistentContactdetails.getEmployeeId();
            Employees employeeIdNew = contactdetails.getEmployeeId();
            if (employeeIdNew != null) {
                employeeIdNew = em.getReference(employeeIdNew.getClass(), employeeIdNew.getIdEmployees());
                contactdetails.setEmployeeId(employeeIdNew);
            }
            contactdetails = em.merge(contactdetails);
            if (employeeIdOld != null && !employeeIdOld.equals(employeeIdNew)) {
                employeeIdOld.getContactdetailsList().remove(contactdetails);
                employeeIdOld = em.merge(employeeIdOld);
            }
            if (employeeIdNew != null && !employeeIdNew.equals(employeeIdOld)) {
                employeeIdNew.getContactdetailsList().add(contactdetails);
                employeeIdNew = em.merge(employeeIdNew);
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
                Integer id = contactdetails.getIdContactDetails();
                if (findContactdetails(id) == null) {
                    throw new NonexistentEntityException("The contactdetails with id " + id + " no longer exists.");
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
            Contactdetails contactdetails;
            try {
                contactdetails = em.getReference(Contactdetails.class, id);
                contactdetails.getIdContactDetails();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contactdetails with id " + id + " no longer exists.", enfe);
            }
            Employees employeeId = contactdetails.getEmployeeId();
            if (employeeId != null) {
                employeeId.getContactdetailsList().remove(contactdetails);
                employeeId = em.merge(employeeId);
            }
            em.remove(contactdetails);
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

    public List<Contactdetails> findContactdetailsEntities() {
        return findContactdetailsEntities(true, -1, -1);
    }

    public List<Contactdetails> findContactdetailsEntities(int maxResults, int firstResult) {
        return findContactdetailsEntities(false, maxResults, firstResult);
    }

    private List<Contactdetails> findContactdetailsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contactdetails.class));
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

    public Contactdetails findContactdetails(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contactdetails.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactdetailsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contactdetails> rt = cq.from(Contactdetails.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
