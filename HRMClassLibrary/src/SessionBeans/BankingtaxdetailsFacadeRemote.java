/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Bankingtaxdetails;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface BankingtaxdetailsFacadeRemote {

    void create(Bankingtaxdetails bankingtaxdetails);

    void edit(Bankingtaxdetails bankingtaxdetails);

    void remove(Bankingtaxdetails bankingtaxdetails);

    Bankingtaxdetails find(Object id);

    List<Bankingtaxdetails> findAll();

    List<Bankingtaxdetails> findRange(int[] range);

    int count();
    
}
