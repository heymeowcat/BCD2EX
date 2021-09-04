/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Beans;

import EmployeeInfo.Entity.Workshifts;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class WorkshiftsFacade extends AbstractFacade<Workshifts> {

    @PersistenceContext(unitName = "HRMApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WorkshiftsFacade() {
        super(Workshifts.class);
    }

    public Workshifts findWorkShiftbyName(String shiftname) {
        return (Workshifts) em.createNamedQuery("Workshifts.findByShiftName").setParameter("shiftName", shiftname).getSingleResult();

    }

}
