/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Leave;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface LeaveFacadeRemote {

    void create(Leave leave);

    void edit(Leave leave);

    void remove(Leave leave);

    Leave find(Object id);

    List<Leave> findAll();

    List<Leave> findRange(int[] range);

    int count();
    
}
