package org.kostenko.example.jpa;

import java.util.ArrayList;
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
    
    @Test
    public void transformSelectToSafeIn() throws Exception {
        EntityManager em = Persistence.createEntityManagerFactory("myDSTestOra").createEntityManager();
        
        List<Long> idsList = new ArrayList<>();
        for (int i = 0; i < 1199; i++) {
            idsList.add((long)i);
        }
        
        Query q =  
                em.createQuery("SELECT b FROM OraBlogEntity b WHERE b.id IN (:idsList)", OraBlogEntity.class)
                .setParameter("idsList", idsList);
        
        List<OraBlogEntity> blogEntitys = q.getResultList();
        System.out.println(blogEntitys.size());
    }

    private void generateTestData(EntityManager em) {
        em.getTransaction().begin();
        for (int i = 0; i < 1001; i++) {
            OraBlogEntity ora = new OraBlogEntity();
            ora.setTitle("title" + i);
            ora.setBody("body" + i);
            em.persist(ora);
        }
        em.getTransaction().commit();
    }
}
