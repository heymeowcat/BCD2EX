/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Attendance;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface AttendanceFacadeRemote {

    void create(Attendance attendance);

    void edit(Attendance attendance);

    void remove(Attendance attendance);

    Attendance find(Object id);

    List<Attendance> findAll();

    List<Attendance> findRange(int[] range);

    int count();
    
}
