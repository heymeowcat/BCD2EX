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
import Entity.Employees;
import Entity.Gender;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class GenderJpaController implements Serializable {

    public GenderJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gender gender) throws RollbackFailureException, Exception {
        if (gender.getEmployeesList() == null) {
            gender.setEmployeesList(new ArrayList<Employees>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Employees> attachedEmployeesList = new ArrayList<Employees>();
            for (Employees employeesListEmployeesToAttach : gender.getEmployeesList()) {
                employeesListEmployeesToAttach = em.getReference(employeesListEmployeesToAttach.getClass(), employeesListEmployeesToAttach.getIdEmployees());
                attachedEmployeesList.add(employeesListEmployeesToAttach);
            }
            gender.setEmployeesList(attachedEmployeesList);
            em.persist(gender);
            for (Employees employeesListEmployees : gender.getEmployeesList()) {
                Gender oldGenderOfEmployeesListEmployees = employeesListEmployees.getGender();
                employeesListEmployees.setGender(gender);
                employeesListEmployees = em.merge(employeesListEmployees);
                if (oldGenderOfEmployeesListEmployees != null) {
                    oldGenderOfEmployeesListEmployees.getEmployeesList().remove(employeesListEmployees);
                    oldGenderOfEmployeesListEmployees = em.merge(oldGenderOfEmployeesListEmployees);
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

    public void edit(Gender gender) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Gender persistentGender = em.find(Gender.class, gender.getIdGender());
            List<Employees> employeesListOld = persistentGender.getEmployeesList();
            List<Employees> employeesListNew = gender.getEmployeesList();
            List<String> illegalOrphanMessages = null;
            for (Employees employeesListOldEmployees : employeesListOld) {
                if (!employeesListNew.contains(employeesListOldEmployees)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employees " + employeesListOldEmployees + " since its gender field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Employees> attachedEmployeesListNew = new ArrayList<Employees>();
            for (Employees employeesListNewEmployeesToAttach : employeesListNew) {
                employeesListNewEmployeesToAttach = em.getReference(employeesListNewEmployeesToAttach.getClass(), employeesListNewEmployeesToAttach.getIdEmployees());
                attachedEmployeesListNew.add(employeesListNewEmployeesToAttach);
            }
            employeesListNew = attachedEmployeesListNew;
            gender.setEmployeesList(employeesListNew);
            gender = em.merge(gender);
            for (Employees employeesListNewEmployees : employeesListNew) {
                if (!employeesListOld.contains(employeesListNewEmployees)) {
                    Gender oldGenderOfEmployeesListNewEmployees = employeesListNewEmployees.getGender();
                    employeesListNewEmployees.setGender(gender);
                    employeesListNewEmployees = em.merge(employeesListNewEmployees);
                    if (oldGenderOfEmployeesListNewEmployees != null && !oldGenderOfEmployeesListNewEmployees.equals(gender)) {
                        oldGenderOfEmployeesListNewEmployees.getEmployeesList().remove(employeesListNewEmployees);
                        oldGenderOfEmployeesListNewEmployees = em.merge(oldGenderOfEmployeesListNewEmployees);
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
                Integer id = gender.getIdGender();
                if (findGender(id) == null) {
                    throw new NonexistentEntityException("The gender with id " + id + " no longer exists.");
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
            Gender gender;
            try {
                gender = em.getReference(Gender.class, id);
                gender.getIdGender();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gender with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Employees> employeesListOrphanCheck = gender.getEmployeesList();
            for (Employees employeesListOrphanCheckEmployees : employeesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Gender (" + gender + ") cannot be destroyed since the Employees " + employeesListOrphanCheckEmployees + " in its employeesList field has a non-nullable gender field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(gender);
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

    public List<Gender> findGenderEntities() {
        return findGenderEntities(true, -1, -1);
    }

    public List<Gender> findGenderEntities(int maxResults, int firstResult) {
        return findGenderEntities(false, maxResults, firstResult);
    }

    private List<Gender> findGenderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gender.class));
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

    public Gender findGender(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gender.class, id);
        } finally {
            em.close();
        }
    }

    public int getGenderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gender> rt = cq.from(Gender.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
