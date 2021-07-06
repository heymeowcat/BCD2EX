/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import Entity.Bankingtaxdetails;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Employees;
import Entity.Salarycomponent;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author heymeowcat
 */
public class BankingtaxdetailsJpaController implements Serializable {

    public BankingtaxdetailsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bankingtaxdetails bankingtaxdetails) throws RollbackFailureException, Exception {
        if (bankingtaxdetails.getSalarycomponentList() == null) {
            bankingtaxdetails.setSalarycomponentList(new ArrayList<Salarycomponent>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employees employeeid = bankingtaxdetails.getEmployeeid();
            if (employeeid != null) {
                employeeid = em.getReference(employeeid.getClass(), employeeid.getIdEmployees());
                bankingtaxdetails.setEmployeeid(employeeid);
            }
            List<Salarycomponent> attachedSalarycomponentList = new ArrayList<Salarycomponent>();
            for (Salarycomponent salarycomponentListSalarycomponentToAttach : bankingtaxdetails.getSalarycomponentList()) {
                salarycomponentListSalarycomponentToAttach = em.getReference(salarycomponentListSalarycomponentToAttach.getClass(), salarycomponentListSalarycomponentToAttach.getSalarycomponentPK());
                attachedSalarycomponentList.add(salarycomponentListSalarycomponentToAttach);
            }
            bankingtaxdetails.setSalarycomponentList(attachedSalarycomponentList);
            em.persist(bankingtaxdetails);
            if (employeeid != null) {
                employeeid.getBankingtaxdetailsList().add(bankingtaxdetails);
                employeeid = em.merge(employeeid);
            }
            for (Salarycomponent salarycomponentListSalarycomponent : bankingtaxdetails.getSalarycomponentList()) {
                Bankingtaxdetails oldBankingtaxdetailsOfSalarycomponentListSalarycomponent = salarycomponentListSalarycomponent.getBankingtaxdetails();
                salarycomponentListSalarycomponent.setBankingtaxdetails(bankingtaxdetails);
                salarycomponentListSalarycomponent = em.merge(salarycomponentListSalarycomponent);
                if (oldBankingtaxdetailsOfSalarycomponentListSalarycomponent != null) {
                    oldBankingtaxdetailsOfSalarycomponentListSalarycomponent.getSalarycomponentList().remove(salarycomponentListSalarycomponent);
                    oldBankingtaxdetailsOfSalarycomponentListSalarycomponent = em.merge(oldBankingtaxdetailsOfSalarycomponentListSalarycomponent);
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

    public void edit(Bankingtaxdetails bankingtaxdetails) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Bankingtaxdetails persistentBankingtaxdetails = em.find(Bankingtaxdetails.class, bankingtaxdetails.getIdBankingTaxDetails());
            Employees employeeidOld = persistentBankingtaxdetails.getEmployeeid();
            Employees employeeidNew = bankingtaxdetails.getEmployeeid();
            List<Salarycomponent> salarycomponentListOld = persistentBankingtaxdetails.getSalarycomponentList();
            List<Salarycomponent> salarycomponentListNew = bankingtaxdetails.getSalarycomponentList();
            List<String> illegalOrphanMessages = null;
            for (Salarycomponent salarycomponentListOldSalarycomponent : salarycomponentListOld) {
                if (!salarycomponentListNew.contains(salarycomponentListOldSalarycomponent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Salarycomponent " + salarycomponentListOldSalarycomponent + " since its bankingtaxdetails field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (employeeidNew != null) {
                employeeidNew = em.getReference(employeeidNew.getClass(), employeeidNew.getIdEmployees());
                bankingtaxdetails.setEmployeeid(employeeidNew);
            }
            List<Salarycomponent> attachedSalarycomponentListNew = new ArrayList<Salarycomponent>();
            for (Salarycomponent salarycomponentListNewSalarycomponentToAttach : salarycomponentListNew) {
                salarycomponentListNewSalarycomponentToAttach = em.getReference(salarycomponentListNewSalarycomponentToAttach.getClass(), salarycomponentListNewSalarycomponentToAttach.getSalarycomponentPK());
                attachedSalarycomponentListNew.add(salarycomponentListNewSalarycomponentToAttach);
            }
            salarycomponentListNew = attachedSalarycomponentListNew;
            bankingtaxdetails.setSalarycomponentList(salarycomponentListNew);
            bankingtaxdetails = em.merge(bankingtaxdetails);
            if (employeeidOld != null && !employeeidOld.equals(employeeidNew)) {
                employeeidOld.getBankingtaxdetailsList().remove(bankingtaxdetails);
                employeeidOld = em.merge(employeeidOld);
            }
            if (employeeidNew != null && !employeeidNew.equals(employeeidOld)) {
                employeeidNew.getBankingtaxdetailsList().add(bankingtaxdetails);
                employeeidNew = em.merge(employeeidNew);
            }
            for (Salarycomponent salarycomponentListNewSalarycomponent : salarycomponentListNew) {
                if (!salarycomponentListOld.contains(salarycomponentListNewSalarycomponent)) {
                    Bankingtaxdetails oldBankingtaxdetailsOfSalarycomponentListNewSalarycomponent = salarycomponentListNewSalarycomponent.getBankingtaxdetails();
                    salarycomponentListNewSalarycomponent.setBankingtaxdetails(bankingtaxdetails);
                    salarycomponentListNewSalarycomponent = em.merge(salarycomponentListNewSalarycomponent);
                    if (oldBankingtaxdetailsOfSalarycomponentListNewSalarycomponent != null && !oldBankingtaxdetailsOfSalarycomponentListNewSalarycomponent.equals(bankingtaxdetails)) {
                        oldBankingtaxdetailsOfSalarycomponentListNewSalarycomponent.getSalarycomponentList().remove(salarycomponentListNewSalarycomponent);
                        oldBankingtaxdetailsOfSalarycomponentListNewSalarycomponent = em.merge(oldBankingtaxdetailsOfSalarycomponentListNewSalarycomponent);
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
                Integer id = bankingtaxdetails.getIdBankingTaxDetails();
                if (findBankingtaxdetails(id) == null) {
                    throw new NonexistentEntityException("The bankingtaxdetails with id " + id + " no longer exists.");
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
            Bankingtaxdetails bankingtaxdetails;
            try {
                bankingtaxdetails = em.getReference(Bankingtaxdetails.class, id);
                bankingtaxdetails.getIdBankingTaxDetails();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bankingtaxdetails with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Salarycomponent> salarycomponentListOrphanCheck = bankingtaxdetails.getSalarycomponentList();
            for (Salarycomponent salarycomponentListOrphanCheckSalarycomponent : salarycomponentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bankingtaxdetails (" + bankingtaxdetails + ") cannot be destroyed since the Salarycomponent " + salarycomponentListOrphanCheckSalarycomponent + " in its salarycomponentList field has a non-nullable bankingtaxdetails field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Employees employeeid = bankingtaxdetails.getEmployeeid();
            if (employeeid != null) {
                employeeid.getBankingtaxdetailsList().remove(bankingtaxdetails);
                employeeid = em.merge(employeeid);
            }
            em.remove(bankingtaxdetails);
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

    public List<Bankingtaxdetails> findBankingtaxdetailsEntities() {
        return findBankingtaxdetailsEntities(true, -1, -1);
    }

    public List<Bankingtaxdetails> findBankingtaxdetailsEntities(int maxResults, int firstResult) {
        return findBankingtaxdetailsEntities(false, maxResults, firstResult);
    }

    private List<Bankingtaxdetails> findBankingtaxdetailsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bankingtaxdetails.class));
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

    public Bankingtaxdetails findBankingtaxdetails(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bankingtaxdetails.class, id);
        } finally {
            em.close();
        }
    }

    public int getBankingtaxdetailsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bankingtaxdetails> rt = cq.from(Bankingtaxdetails.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
