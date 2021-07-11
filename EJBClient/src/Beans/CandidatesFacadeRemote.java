/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entity.Candidates;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface CandidatesFacadeRemote {

    void create(Candidates candidates);

    void edit(Candidates candidates);

    void remove(Candidates candidates);

    Candidates find(Object id);

    List<Candidates> findAll();

    List<Candidates> findRange(int[] range);

    int count();
    
}
