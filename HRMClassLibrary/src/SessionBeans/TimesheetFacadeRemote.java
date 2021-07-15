/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Timesheet;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface TimesheetFacadeRemote {

    void create(Timesheet timesheet);

    void edit(Timesheet timesheet);

    void remove(Timesheet timesheet);

    Timesheet find(Object id);

    List<Timesheet> findAll();

    List<Timesheet> findRange(int[] range);

    int count();
    
}
