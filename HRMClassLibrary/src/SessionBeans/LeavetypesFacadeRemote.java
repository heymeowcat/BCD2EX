/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Leavetypes;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface LeavetypesFacadeRemote {

    void create(Leavetypes leavetypes);

    void edit(Leavetypes leavetypes);

    void remove(Leavetypes leavetypes);

    Leavetypes find(Object id);

    List<Leavetypes> findAll();

    List<Leavetypes> findRange(int[] range);

    int count();
    
}
