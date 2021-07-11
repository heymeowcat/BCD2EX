/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Organization;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface OrganizationFacadeRemote {

    void create(Organization organization);

    void edit(Organization organization);

    void remove(Organization organization);

    Organization find(Object id);

    List<Organization> findAll();

    List<Organization> findRange(int[] range);

    int count();
    
}
