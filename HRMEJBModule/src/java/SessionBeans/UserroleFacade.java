/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Userrole;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class UserroleFacade extends AbstractFacade<Userrole> implements SessionBeans.UserroleFacadeRemote {

    @PersistenceContext(unitName = "HRMEJBModulePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserroleFacade() {
        super(Userrole.class);
    }
    
}
