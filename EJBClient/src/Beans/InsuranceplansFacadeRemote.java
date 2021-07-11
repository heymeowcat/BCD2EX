/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Insuranceplans;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface InsuranceplansFacadeRemote {

    void create(Insuranceplans insuranceplans);

    void edit(Insuranceplans insuranceplans);

    void remove(Insuranceplans insuranceplans);

    Insuranceplans find(Object id);

    List<Insuranceplans> findAll();

    List<Insuranceplans> findRange(int[] range);

    int count();
    
}
