/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Login;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface LoginFacadeRemote {

    void create(Login login);

    void edit(Login login);

    void remove(Login login);

    Login find(Object id);

    List<Login> findAll();

    List<Login> findRange(int[] range);

    int count();
    
}
