/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Beans;

import EmployeeInfo.Entity.Gender;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class GenderFacade extends AbstractFacade<Gender> {

    @PersistenceContext(unitName = "HRMApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenderFacade() {
        super(Gender.class);
    }

    public Gender findGenderId(String gender) {
        return (Gender) em.createNamedQuery("Gender.findByGenderName").setParameter("genderName", gender).getSingleResult();
//        Query query = em.createQuery("SELECT g FROM Gender g WHERE g.genderName = :genderName");
//        query.setParameter("genderName", "");
//        Gender result = (Gender) query.getSingleResult();
//        return result;
    }

}
