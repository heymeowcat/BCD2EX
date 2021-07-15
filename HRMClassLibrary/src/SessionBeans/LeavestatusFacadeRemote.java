/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Leavestatus;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface LeavestatusFacadeRemote {

    void create(Leavestatus leavestatus);

    void edit(Leavestatus leavestatus);

    void remove(Leavestatus leavestatus);

    Leavestatus find(Object id);

    List<Leavestatus> findAll();

    List<Leavestatus> findRange(int[] range);

    int count();
    
}
