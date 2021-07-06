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
import Entity.Insuranceplans;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class InsuranceplansJpaController implements Serializable {

    public InsuranceplansJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Insuranceplans insuranceplans) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees employeeid = insuranceplans.getEmployeeid();
            if (employeeid != null) {
                employeeid = em.getReference(employeeid.getClass(), employeeid.getIdEmployees());
                insuranceplans.setEmployeeid(employeeid);
            }
            em.persist(insuranceplans);
            if (employeeid != null) {
                employeeid.getInsuranceplansList().add(insuranceplans);
                employeeid = em.merge(employeeid);
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

    public void edit(Insuranceplans insuranceplans) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Insuranceplans persistentInsuranceplans = em.find(Insuranceplans.class, insuranceplans.getIdInsurancePlans());
            Employees employeeidOld = persistentInsuranceplans.getEmployeeid();
            Employees employeeidNew = insuranceplans.getEmployeeid();
            if (employeeidNew != null) {
                employeeidNew = em.getReference(employeeidNew.getClass(), employeeidNew.getIdEmployees());
                insuranceplans.setEmployeeid(employeeidNew);
            }
            insuranceplans = em.merge(insuranceplans);
            if (employeeidOld != null && !employeeidOld.equals(employeeidNew)) {
                employeeidOld.getInsuranceplansList().remove(insuranceplans);
                employeeidOld = em.merge(employeeidOld);
            }
            if (employeeidNew != null && !employeeidNew.equals(employeeidOld)) {
                employeeidNew.getInsuranceplansList().add(insuranceplans);
                employeeidNew = em.merge(employeeidNew);
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
                Integer id = insuranceplans.getIdInsurancePlans();
                if (findInsuranceplans(id) == null) {
                    throw new NonexistentEntityException("The insuranceplans with id " + id + " no longer exists.");
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
            Insuranceplans insuranceplans;
            try {
                insuranceplans = em.getReference(Insuranceplans.class, id);
                insuranceplans.getIdInsurancePlans();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The insuranceplans with id " + id + " no longer exists.", enfe);
            }
            Employees employeeid = insuranceplans.getEmployeeid();
            if (employeeid != null) {
                employeeid.getInsuranceplansList().remove(insuranceplans);
                employeeid = em.merge(employeeid);
            }
            em.remove(insuranceplans);
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

    public List<Insuranceplans> findInsuranceplansEntities() {
        return findInsuranceplansEntities(true, -1, -1);
    }

    public List<Insuranceplans> findInsuranceplansEntities(int maxResults, int firstResult) {
        return findInsuranceplansEntities(false, maxResults, firstResult);
    }

    private List<Insuranceplans> findInsuranceplansEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Insuranceplans.class));
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

    public Insuranceplans findInsuranceplans(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Insuranceplans.class, id);
        } finally {
            em.close();
        }
    }

    public int getInsuranceplansCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Insuranceplans> rt = cq.from(Insuranceplans.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
