package org.kostenko.example.jpa ;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import org.junit.Test;

/**
 *
 * @author kostenko
 */
public class OraTest {

    @Test
    public void countOver() throws Exception {
        EntityManager em = Persistence.createEntityManagerFactory("myDSTestOra").createEntityManager();

        em.getTransaction().begin();
        OraBlogEntity ora = new OraBlogEntity();
        ora.setTitle("khjhjh");

        em.persist(ora);
        em.getTransaction().commit();

        List<Tuple> ll = em.createQuery("SELECT b.id,  function('countover') FROM OraBlogEntity b", Tuple.class).getResultList();
        
        System.out.println(
                
        );
        
        
        
    }
}
