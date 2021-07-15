/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Contactdetails;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface ContactdetailsFacadeRemote {

    void create(Contactdetails contactdetails);

    void edit(Contactdetails contactdetails);

    void remove(Contactdetails contactdetails);

    Contactdetails find(Object id);

    List<Contactdetails> findAll();

    List<Contactdetails> findRange(int[] range);

    int count();
    
}
