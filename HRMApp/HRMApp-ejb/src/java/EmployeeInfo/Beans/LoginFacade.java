/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Beans;

import EmployeeInfo.Entity.Login;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class LoginFacade extends AbstractFacade<Login> {

    @PersistenceContext(unitName = "HRMApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginFacade() {
        super(Login.class);
    }

    public List login(String username, String password) {

        return em.createNamedQuery("Login.loginProcess").setParameter("username", username).setParameter("password", password).getResultList();
    }

    public void saveLogin(Login login) {
        em.persist(login);
        em.flush();
    }

    public List<Login> searchLoginUsername(String username) {
        return em.createNamedQuery("Login.findByUsername").setParameter("username", username).getResultList();
    }

}
