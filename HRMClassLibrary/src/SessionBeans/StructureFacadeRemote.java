/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import Entitiy.Structure;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author heymeowcat
 */
@Remote
public interface StructureFacadeRemote {

    void create(Structure structure);

    void edit(Structure structure);

    void remove(Structure structure);

    Structure find(Object id);

    List<Structure> findAll();

    List<Structure> findRange(int[] range);

    int count();
    
}
