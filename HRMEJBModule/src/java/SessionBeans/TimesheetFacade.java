/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Timesheet;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class TimesheetFacade extends AbstractFacade<Timesheet> implements SessionBeans.TimesheetFacadeRemote {

    @PersistenceContext(unitName = "HRMEJBModulePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TimesheetFacade() {
        super(Timesheet.class);
    }
    
}
