/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Insuranceplans;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class InsuranceplansFacade extends AbstractFacade<Insuranceplans> implements Beans.InsuranceplansFacadeRemote {

    @PersistenceContext(unitName = "HRMModulePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InsuranceplansFacade() {
        super(Insuranceplans.class);
    }
    
}
