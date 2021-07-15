/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Employment;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface EmploymentFacadeRemote {

    void create(Employment employment);

    void edit(Employment employment);

    void remove(Employment employment);

    Employment find(Object id);

    List<Employment> findAll();

    List<Employment> findRange(int[] range);

    int count();
    
}
