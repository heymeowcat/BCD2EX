/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Leaveactions;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface LeaveactionsFacadeRemote {

    void create(Leaveactions leaveactions);

    void edit(Leaveactions leaveactions);

    void remove(Leaveactions leaveactions);

    Leaveactions find(Object id);

    List<Leaveactions> findAll();

    List<Leaveactions> findRange(int[] range);

    int count();
    
}
