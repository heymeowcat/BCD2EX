/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Projectadmins;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface ProjectadminsFacadeRemote {

    void create(Projectadmins projectadmins);

    void edit(Projectadmins projectadmins);

    void remove(Projectadmins projectadmins);

    Projectadmins find(Object id);

    List<Projectadmins> findAll();

    List<Projectadmins> findRange(int[] range);

    int count();
    
}
