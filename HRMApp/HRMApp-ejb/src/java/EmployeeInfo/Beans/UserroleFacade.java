/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Beans;

import EmployeeInfo.Entity.Gender;
import EmployeeInfo.Entity.Userrole;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class UserroleFacade extends AbstractFacade<Userrole> {

    @PersistenceContext(unitName = "HRMApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserroleFacade() {
        super(Userrole.class);
    }

    public Userrole findUserRolebyName(String role) {
        return (Userrole) em.createNamedQuery("Userrole.findByUserRole").setParameter("userRole", role).getSingleResult();
    }

    
}
