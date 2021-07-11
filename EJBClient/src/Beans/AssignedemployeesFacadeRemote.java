/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Assignedemployees;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface AssignedemployeesFacadeRemote {

    void create(Assignedemployees assignedemployees);

    void edit(Assignedemployees assignedemployees);

    void remove(Assignedemployees assignedemployees);

    Assignedemployees find(Object id);

    List<Assignedemployees> findAll();

    List<Assignedemployees> findRange(int[] range);

    int count();
    
}
