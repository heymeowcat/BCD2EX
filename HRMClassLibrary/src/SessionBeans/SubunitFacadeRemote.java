/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Subunit;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface SubunitFacadeRemote {

    void create(Subunit subunit);

    void edit(Subunit subunit);

    void remove(Subunit subunit);

    Subunit find(Object id);

    List<Subunit> findAll();

    List<Subunit> findRange(int[] range);

    int count();
    
}
