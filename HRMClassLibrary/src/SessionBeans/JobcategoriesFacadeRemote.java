/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Jobcategories;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface JobcategoriesFacadeRemote {

    void create(Jobcategories jobcategories);

    void edit(Jobcategories jobcategories);

    void remove(Jobcategories jobcategories);

    Jobcategories find(Object id);

    List<Jobcategories> findAll();

    List<Jobcategories> findRange(int[] range);

    int count();
    
}
