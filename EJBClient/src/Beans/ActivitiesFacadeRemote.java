/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Activities;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface ActivitiesFacadeRemote {

    void create(Activities activities);

    void edit(Activities activities);

    void remove(Activities activities);

    Activities find(Object id);

    List<Activities> findAll();

    List<Activities> findRange(int[] range);

    int count();
    
}
