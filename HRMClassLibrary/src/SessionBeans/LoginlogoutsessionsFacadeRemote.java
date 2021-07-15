/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Loginlogoutsessions;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface LoginlogoutsessionsFacadeRemote {

    void create(Loginlogoutsessions loginlogoutsessions);

    void edit(Loginlogoutsessions loginlogoutsessions);

    void remove(Loginlogoutsessions loginlogoutsessions);

    Loginlogoutsessions find(Object id);

    List<Loginlogoutsessions> findAll();

    List<Loginlogoutsessions> findRange(int[] range);

    int count();
    
}
