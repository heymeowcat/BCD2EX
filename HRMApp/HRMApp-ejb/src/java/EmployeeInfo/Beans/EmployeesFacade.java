/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Beans;

import EmployeeInfo.Entity.Employees;
import EmployeeInfo.Entity.Userrole;
import Interceptors.EmployeeTrackingInterceptor;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class EmployeesFacade extends AbstractFacade<Employees> {

    @PersistenceContext(unitName = "HRMApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeesFacade() {
        super(Employees.class);
    }

    public List getAllEmployees() {
        return em.createNamedQuery("Employees.findAll").getResultList();
    }

    @Interceptors(EmployeeTrackingInterceptor.class)
    public int saveEmployee(Employees employee) {
        em.persist(employee);
        em.flush();
        return employee.getIdEmployees();

    }

    public Employees findEmployeesid(int id) {
        return (Employees) em.createNamedQuery("Employees.findByIdEmployees").setParameter("idEmployees", id).getSingleResult();
    }

    public List<Employees> searchEmployees(String searchName, Userrole searchRole) {
        return em.createNamedQuery("Employees.search").setParameter("firstName",searchName).setParameter("searchRole", searchRole).getResultList();
    }

    
    public List<Employees> searchEmployeesFirstName(String searchName) {
        return em.createNamedQuery("Employees.findByFirstName").setParameter("firstName",searchName).getResultList();
    }


}
