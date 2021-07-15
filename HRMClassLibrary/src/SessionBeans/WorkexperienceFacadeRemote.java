/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Workexperience;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface WorkexperienceFacadeRemote {

    void create(Workexperience workexperience);

    void edit(Workexperience workexperience);

    void remove(Workexperience workexperience);

    Workexperience find(Object id);

    List<Workexperience> findAll();

    List<Workexperience> findRange(int[] range);

    int count();
    
}
