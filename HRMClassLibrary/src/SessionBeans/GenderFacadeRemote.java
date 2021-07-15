/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Gender;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface GenderFacadeRemote {

    void create(Gender gender);

    void edit(Gender gender);

    void remove(Gender gender);

    Gender find(Object id);

    List<Gender> findAll();

    List<Gender> findRange(int[] range);

    int count();
    
}
