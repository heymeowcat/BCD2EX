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
import Entity.Userrole;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class UserroleJpaController implements Serializable {

    public UserroleJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Userrole userrole) throws RollbackFailureException, Exception {
        if (userrole.getEmployeesList() == null) {
            userrole.setEmployeesList(new ArrayList<Employees>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Employees> attachedEmployeesList = new ArrayList<Employees>();
            for (Employees employeesListEmployeesToAttach : userrole.getEmployeesList()) {
                employeesListEmployeesToAttach = em.getReference(employeesListEmployeesToAttach.getClass(), employeesListEmployeesToAttach.getIdEmployees());
                attachedEmployeesList.add(employeesListEmployeesToAttach);
            }
            userrole.setEmployeesList(attachedEmployeesList);
            em.persist(userrole);
            for (Employees employeesListEmployees : userrole.getEmployeesList()) {
                Userrole oldRoleOfEmployeesListEmployees = employeesListEmployees.getRole();
                employeesListEmployees.setRole(userrole);
                employeesListEmployees = em.merge(employeesListEmployees);
                if (oldRoleOfEmployeesListEmployees != null) {
                    oldRoleOfEmployeesListEmployees.getEmployeesList().remove(employeesListEmployees);
                    oldRoleOfEmployeesListEmployees = em.merge(oldRoleOfEmployeesListEmployees);
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

    public void edit(Userrole userrole) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Userrole persistentUserrole = em.find(Userrole.class, userrole.getIdUserRole());
            List<Employees> employeesListOld = persistentUserrole.getEmployeesList();
            List<Employees> employeesListNew = userrole.getEmployeesList();
            List<String> illegalOrphanMessages = null;
            for (Employees employeesListOldEmployees : employeesListOld) {
                if (!employeesListNew.contains(employeesListOldEmployees)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employees " + employeesListOldEmployees + " since its role field is not nullable.");
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
            userrole.setEmployeesList(employeesListNew);
            userrole = em.merge(userrole);
            for (Employees employeesListNewEmployees : employeesListNew) {
                if (!employeesListOld.contains(employeesListNewEmployees)) {
                    Userrole oldRoleOfEmployeesListNewEmployees = employeesListNewEmployees.getRole();
                    employeesListNewEmployees.setRole(userrole);
                    employeesListNewEmployees = em.merge(employeesListNewEmployees);
                    if (oldRoleOfEmployeesListNewEmployees != null && !oldRoleOfEmployeesListNewEmployees.equals(userrole)) {
                        oldRoleOfEmployeesListNewEmployees.getEmployeesList().remove(employeesListNewEmployees);
                        oldRoleOfEmployeesListNewEmployees = em.merge(oldRoleOfEmployeesListNewEmployees);
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
                Integer id = userrole.getIdUserRole();
                if (findUserrole(id) == null) {
                    throw new NonexistentEntityException("The userrole with id " + id + " no longer exists.");
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
            Userrole userrole;
            try {
                userrole = em.getReference(Userrole.class, id);
                userrole.getIdUserRole();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userrole with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Employees> employeesListOrphanCheck = userrole.getEmployeesList();
            for (Employees employeesListOrphanCheckEmployees : employeesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Userrole (" + userrole + ") cannot be destroyed since the Employees " + employeesListOrphanCheckEmployees + " in its employeesList field has a non-nullable role field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(userrole);
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

    public List<Userrole> findUserroleEntities() {
        return findUserroleEntities(true, -1, -1);
    }

    public List<Userrole> findUserroleEntities(int maxResults, int firstResult) {
        return findUserroleEntities(false, maxResults, firstResult);
    }

    private List<Userrole> findUserroleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Userrole.class));
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

    public Userrole findUserrole(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Userrole.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserroleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Userrole> rt = cq.from(Userrole.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
