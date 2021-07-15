/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Salarycomponent;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface SalarycomponentFacadeRemote {

    void create(Salarycomponent salarycomponent);

    void edit(Salarycomponent salarycomponent);

    void remove(Salarycomponent salarycomponent);

    Salarycomponent find(Object id);

    List<Salarycomponent> findAll();

    List<Salarycomponent> findRange(int[] range);

    int count();
    
}
