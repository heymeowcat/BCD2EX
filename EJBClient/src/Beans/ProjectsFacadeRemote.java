/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Projects;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface ProjectsFacadeRemote {

    void create(Projects projects);

    void edit(Projects projects);

    void remove(Projects projects);

    Projects find(Object id);

    List<Projects> findAll();

    List<Projects> findRange(int[] range);

    int count();
    
}
