/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Employees;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface EmployeesFacadeRemote {

    void create(Employees employees);

    void edit(Employees employees);

    void remove(Employees employees);

    Employees find(Object id);

    List<Employees> findAll();

    List<Employees> findRange(int[] range);

    int count();
    
}
