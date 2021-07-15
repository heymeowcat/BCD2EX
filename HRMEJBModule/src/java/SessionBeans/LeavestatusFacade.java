/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Leavestatus;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class LeavestatusFacade extends AbstractFacade<Leavestatus> implements SessionBeans.LeavestatusFacadeRemote {

    @PersistenceContext(unitName = "HRMEJBModulePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LeavestatusFacade() {
        super(Leavestatus.class);
    }
    
}
