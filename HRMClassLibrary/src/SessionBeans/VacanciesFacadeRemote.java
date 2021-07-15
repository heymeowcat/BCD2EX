/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Vacancies;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface VacanciesFacadeRemote {

    void create(Vacancies vacancies);

    void edit(Vacancies vacancies);

    void remove(Vacancies vacancies);

    Vacancies find(Object id);

    List<Vacancies> findAll();

    List<Vacancies> findRange(int[] range);

    int count();
    
}
