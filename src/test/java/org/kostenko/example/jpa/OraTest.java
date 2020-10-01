package org.kostenko.example.jpa;

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

        em.createQuery("SELECT b.id as id, function('count(b) over()') FROM OraBlogEntity b", Tuple.class).getResultList();

    }
}
