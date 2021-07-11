/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Workshifts;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface WorkshiftsFacadeRemote {

    void create(Workshifts workshifts);

    void edit(Workshifts workshifts);

    void remove(Workshifts workshifts);

    Workshifts find(Object id);

    List<Workshifts> findAll();

    List<Workshifts> findRange(int[] range);

    int count();
    
}
