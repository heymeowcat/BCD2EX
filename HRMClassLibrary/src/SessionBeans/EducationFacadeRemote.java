/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Education;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface EducationFacadeRemote {

    void create(Education education);

    void edit(Education education);

    void remove(Education education);

    Education find(Object id);

    List<Education> findAll();

    List<Education> findRange(int[] range);

    int count();
    
}
