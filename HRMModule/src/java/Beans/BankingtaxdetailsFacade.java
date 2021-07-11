/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Bankingtaxdetails;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class BankingtaxdetailsFacade extends AbstractFacade<Bankingtaxdetails> implements Beans.BankingtaxdetailsFacadeRemote {

    @PersistenceContext(unitName = "HRMModulePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BankingtaxdetailsFacade() {
        super(Bankingtaxdetails.class);
    }
    
}
