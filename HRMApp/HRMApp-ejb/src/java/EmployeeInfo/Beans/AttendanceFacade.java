/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeeInfo.Beans;

import EmployeeInfo.Entity.Attendance;
import EmployeeInfo.Entity.Employees;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author heymeowcat
 */
@Stateless
public class AttendanceFacade extends AbstractFacade<Attendance> {

    @PersistenceContext(unitName = "HRMApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttendanceFacade() {
        super(Attendance.class);
    }

    public int saveAttendance(Attendance attendance) {
        em.persist(attendance);
        em.flush();
        return attendance.getIdAttendance();

    }

    public List<Attendance> searchClockIn(String punchIn, Employees emp) {
        return em.createNamedQuery("Attendance.findempPunchIn").setParameter("punchIn", punchIn).setParameter("empid", emp).getResultList();
    }

    public void updateAttendance(Attendance updateAttendance) {
        em.merge(updateAttendance);
        em.flush();
    }

}
