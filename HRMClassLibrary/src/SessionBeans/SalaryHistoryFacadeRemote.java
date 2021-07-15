/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.SalaryHistory;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface SalaryHistoryFacadeRemote {

    void create(SalaryHistory salaryHistory);

    void edit(SalaryHistory salaryHistory);

    void remove(SalaryHistory salaryHistory);

    SalaryHistory find(Object id);

    List<SalaryHistory> findAll();

    List<SalaryHistory> findRange(int[] range);

    int count();
    
}
