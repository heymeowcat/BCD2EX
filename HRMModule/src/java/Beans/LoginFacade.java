/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Employees;
import Entity.Login;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class LoginFacade extends AbstractFacade<Login> implements Beans.LoginFacadeRemote {

    @PersistenceContext(unitName = "HRMModulePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginFacade() {
        super(Login.class);
    }

    @Override
    public Login loginProcess(String username, String password) {
         return (Login)em.createQuery("SELECT l FROM Login l WHERE l.username ='" + username + "' AND l.password='" + password + "'  ").getResultList();
    }
    
    

}
