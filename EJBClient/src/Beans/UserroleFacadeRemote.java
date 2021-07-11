/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Userrole;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface UserroleFacadeRemote {

    void create(Userrole userrole);

    void edit(Userrole userrole);

    void remove(Userrole userrole);

    Userrole find(Object id);

    List<Userrole> findAll();

    List<Userrole> findRange(int[] range);

    int count();
    
}
