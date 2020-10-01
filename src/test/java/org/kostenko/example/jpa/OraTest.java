package org.kostenko.example.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
        this.generateTestData(em);
        
        Query query =  em.createQuery("SELECT b as post, function('countover') as cnt FROM OraBlogEntity b", Tuple.class);
        query.setFirstResult(10);
        query.setMaxResults(5);
        List<Tuple> tpList = query.getResultList();
        for (Tuple tp : tpList) {
            System.out.println(tp.get("post"));
            System.out.println("Total:" + tp.get("cnt"));
        }
    }

    private void generateTestData(EntityManager em) {
        em.getTransaction().begin();
        for (int i = 0; i < 1000; i++) {
            OraBlogEntity ora = new OraBlogEntity();
            ora.setTitle("title" + i);
            ora.setBody("body" + i);
            em.persist(ora);
        }
        em.getTransaction().commit();
    }
}
