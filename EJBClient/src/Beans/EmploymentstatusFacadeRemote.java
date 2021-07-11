/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Employmentstatus;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface EmploymentstatusFacadeRemote {

    void create(Employmentstatus employmentstatus);

    void edit(Employmentstatus employmentstatus);

    void remove(Employmentstatus employmentstatus);

    Employmentstatus find(Object id);

    List<Employmentstatus> findAll();

    List<Employmentstatus> findRange(int[] range);

    int count();
    
}
