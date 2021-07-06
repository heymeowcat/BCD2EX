/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import Entity.Education;
import Entity.EducationPK;
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
public class EducationJpaController implements Serializable {

    public EducationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Education education) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (education.getEducationPK() == null) {
            education.setEducationPK(new EducationPK());
        }
        education.getEducationPK().setEmployeeId(education.getEmployees().getIdEmployees());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees employees = education.getEmployees();
            if (employees != null) {
                employees = em.getReference(employees.getClass(), employees.getIdEmployees());
                education.setEmployees(employees);
            }
            em.persist(education);
            if (employees != null) {
                employees.getEducationList().add(education);
                employees = em.merge(employees);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEducation(education.getEducationPK()) != null) {
                throw new PreexistingEntityException("Education " + education + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Education education) throws NonexistentEntityException, RollbackFailureException, Exception {
        education.getEducationPK().setEmployeeId(education.getEmployees().getIdEmployees());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Education persistentEducation = em.find(Education.class, education.getEducationPK());
            Employees employeesOld = persistentEducation.getEmployees();
            Employees employeesNew = education.getEmployees();
            if (employeesNew != null) {
                employeesNew = em.getReference(employeesNew.getClass(), employeesNew.getIdEmployees());
                education.setEmployees(employeesNew);
            }
            education = em.merge(education);
            if (employeesOld != null && !employeesOld.equals(employeesNew)) {
                employeesOld.getEducationList().remove(education);
                employeesOld = em.merge(employeesOld);
            }
            if (employeesNew != null && !employeesNew.equals(employeesOld)) {
                employeesNew.getEducationList().add(education);
                employeesNew = em.merge(employeesNew);
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
                EducationPK id = education.getEducationPK();
                if (findEducation(id) == null) {
                    throw new NonexistentEntityException("The education with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EducationPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Education education;
            try {
                education = em.getReference(Education.class, id);
                education.getEducationPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The education with id " + id + " no longer exists.", enfe);
            }
            Employees employees = education.getEmployees();
            if (employees != null) {
                employees.getEducationList().remove(education);
                employees = em.merge(employees);
            }
            em.remove(education);
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

    public List<Education> findEducationEntities() {
        return findEducationEntities(true, -1, -1);
    }

    public List<Education> findEducationEntities(int maxResults, int firstResult) {
        return findEducationEntities(false, maxResults, firstResult);
    }

    private List<Education> findEducationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Education.class));
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

    public Education findEducation(EducationPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Education.class, id);
        } finally {
            em.close();
        }
    }

    public int getEducationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Education> rt = cq.from(Education.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
