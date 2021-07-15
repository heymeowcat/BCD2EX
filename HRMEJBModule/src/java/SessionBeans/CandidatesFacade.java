/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Candidates;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class CandidatesFacade extends AbstractFacade<Candidates> implements SessionBeans.CandidatesFacadeRemote {

    @PersistenceContext(unitName = "HRMEJBModulePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CandidatesFacade() {
        super(Candidates.class);
    }
    
}
