/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Beans;

import EmployeeInfo.Entity.Jobcategories;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class JobcategoriesFacade extends AbstractFacade<Jobcategories> {

    @PersistenceContext(unitName = "HRMApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JobcategoriesFacade() {
        super(Jobcategories.class);
    }
    
}
