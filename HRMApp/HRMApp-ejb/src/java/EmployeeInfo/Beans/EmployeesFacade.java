/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Beans;

import EmployeeInfo.Entity.Employees;
import java.util.List;
import javax.ejb.Stateless;
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

    public int saveEmployee(Employees employee) {
        em.persist(employee);
        em.flush();
        return employee.getIdEmployees();

    }
    public Employees findEmployeesid(int id) {
        return (Employees) em.createNamedQuery("Employees.findByIdEmployees").setParameter("idEmployees", id).getSingleResult();
    }
   
   
}
