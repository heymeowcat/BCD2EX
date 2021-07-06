/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Employees;
import Entity.Login;
import Entity.LoginPK;
import Entity.Loginlogoutsessions;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class LoginJpaController implements Serializable {

    public LoginJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Login login) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (login.getLoginPK() == null) {
            login.setLoginPK(new LoginPK());
        }
        if (login.getLoginlogoutsessionsList() == null) {
            login.setLoginlogoutsessionsList(new ArrayList<Loginlogoutsessions>());
        }
        login.getLoginPK().setEmployeeId(login.getEmployees().getIdEmployees());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees employees = login.getEmployees();
            if (employees != null) {
                employees = em.getReference(employees.getClass(), employees.getIdEmployees());
                login.setEmployees(employees);
            }
            List<Loginlogoutsessions> attachedLoginlogoutsessionsList = new ArrayList<Loginlogoutsessions>();
            for (Loginlogoutsessions loginlogoutsessionsListLoginlogoutsessionsToAttach : login.getLoginlogoutsessionsList()) {
                loginlogoutsessionsListLoginlogoutsessionsToAttach = em.getReference(loginlogoutsessionsListLoginlogoutsessionsToAttach.getClass(), loginlogoutsessionsListLoginlogoutsessionsToAttach.getIdLoginSessions());
                attachedLoginlogoutsessionsList.add(loginlogoutsessionsListLoginlogoutsessionsToAttach);
            }
            login.setLoginlogoutsessionsList(attachedLoginlogoutsessionsList);
            em.persist(login);
            if (employees != null) {
                employees.getLoginList().add(login);
                employees = em.merge(employees);
            }
            for (Loginlogoutsessions loginlogoutsessionsListLoginlogoutsessions : login.getLoginlogoutsessionsList()) {
                Login oldLoginIdOfLoginlogoutsessionsListLoginlogoutsessions = loginlogoutsessionsListLoginlogoutsessions.getLoginId();
                loginlogoutsessionsListLoginlogoutsessions.setLoginId(login);
                loginlogoutsessionsListLoginlogoutsessions = em.merge(loginlogoutsessionsListLoginlogoutsessions);
                if (oldLoginIdOfLoginlogoutsessionsListLoginlogoutsessions != null) {
                    oldLoginIdOfLoginlogoutsessionsListLoginlogoutsessions.getLoginlogoutsessionsList().remove(loginlogoutsessionsListLoginlogoutsessions);
                    oldLoginIdOfLoginlogoutsessionsListLoginlogoutsessions = em.merge(oldLoginIdOfLoginlogoutsessionsListLoginlogoutsessions);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLogin(login.getLoginPK()) != null) {
                throw new PreexistingEntityException("Login " + login + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Login login) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        login.getLoginPK().setEmployeeId(login.getEmployees().getIdEmployees());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Login persistentLogin = em.find(Login.class, login.getLoginPK());
            Employees employeesOld = persistentLogin.getEmployees();
            Employees employeesNew = login.getEmployees();
            List<Loginlogoutsessions> loginlogoutsessionsListOld = persistentLogin.getLoginlogoutsessionsList();
            List<Loginlogoutsessions> loginlogoutsessionsListNew = login.getLoginlogoutsessionsList();
            List<String> illegalOrphanMessages = null;
            for (Loginlogoutsessions loginlogoutsessionsListOldLoginlogoutsessions : loginlogoutsessionsListOld) {
                if (!loginlogoutsessionsListNew.contains(loginlogoutsessionsListOldLoginlogoutsessions)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Loginlogoutsessions " + loginlogoutsessionsListOldLoginlogoutsessions + " since its loginId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (employeesNew != null) {
                employeesNew = em.getReference(employeesNew.getClass(), employeesNew.getIdEmployees());
                login.setEmployees(employeesNew);
            }
            List<Loginlogoutsessions> attachedLoginlogoutsessionsListNew = new ArrayList<Loginlogoutsessions>();
            for (Loginlogoutsessions loginlogoutsessionsListNewLoginlogoutsessionsToAttach : loginlogoutsessionsListNew) {
                loginlogoutsessionsListNewLoginlogoutsessionsToAttach = em.getReference(loginlogoutsessionsListNewLoginlogoutsessionsToAttach.getClass(), loginlogoutsessionsListNewLoginlogoutsessionsToAttach.getIdLoginSessions());
                attachedLoginlogoutsessionsListNew.add(loginlogoutsessionsListNewLoginlogoutsessionsToAttach);
            }
            loginlogoutsessionsListNew = attachedLoginlogoutsessionsListNew;
            login.setLoginlogoutsessionsList(loginlogoutsessionsListNew);
            login = em.merge(login);
            if (employeesOld != null && !employeesOld.equals(employeesNew)) {
                employeesOld.getLoginList().remove(login);
                employeesOld = em.merge(employeesOld);
            }
            if (employeesNew != null && !employeesNew.equals(employeesOld)) {
                employeesNew.getLoginList().add(login);
                employeesNew = em.merge(employeesNew);
            }
            for (Loginlogoutsessions loginlogoutsessionsListNewLoginlogoutsessions : loginlogoutsessionsListNew) {
                if (!loginlogoutsessionsListOld.contains(loginlogoutsessionsListNewLoginlogoutsessions)) {
                    Login oldLoginIdOfLoginlogoutsessionsListNewLoginlogoutsessions = loginlogoutsessionsListNewLoginlogoutsessions.getLoginId();
                    loginlogoutsessionsListNewLoginlogoutsessions.setLoginId(login);
                    loginlogoutsessionsListNewLoginlogoutsessions = em.merge(loginlogoutsessionsListNewLoginlogoutsessions);
                    if (oldLoginIdOfLoginlogoutsessionsListNewLoginlogoutsessions != null && !oldLoginIdOfLoginlogoutsessionsListNewLoginlogoutsessions.equals(login)) {
                        oldLoginIdOfLoginlogoutsessionsListNewLoginlogoutsessions.getLoginlogoutsessionsList().remove(loginlogoutsessionsListNewLoginlogoutsessions);
                        oldLoginIdOfLoginlogoutsessionsListNewLoginlogoutsessions = em.merge(oldLoginIdOfLoginlogoutsessionsListNewLoginlogoutsessions);
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
                LoginPK id = login.getLoginPK();
                if (findLogin(id) == null) {
                    throw new NonexistentEntityException("The login with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LoginPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Login login;
            try {
                login = em.getReference(Login.class, id);
                login.getLoginPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The login with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Loginlogoutsessions> loginlogoutsessionsListOrphanCheck = login.getLoginlogoutsessionsList();
            for (Loginlogoutsessions loginlogoutsessionsListOrphanCheckLoginlogoutsessions : loginlogoutsessionsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Login (" + login + ") cannot be destroyed since the Loginlogoutsessions " + loginlogoutsessionsListOrphanCheckLoginlogoutsessions + " in its loginlogoutsessionsList field has a non-nullable loginId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Employees employees = login.getEmployees();
            if (employees != null) {
                employees.getLoginList().remove(login);
                employees = em.merge(employees);
            }
            em.remove(login);
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

    public List<Login> findLoginEntities() {
        return findLoginEntities(true, -1, -1);
    }

    public List<Login> findLoginEntities(int maxResults, int firstResult) {
        return findLoginEntities(false, maxResults, firstResult);
    }

    private List<Login> findLoginEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Login.class));
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

    public Login findLogin(LoginPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Login.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoginCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Login> rt = cq.from(Login.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
