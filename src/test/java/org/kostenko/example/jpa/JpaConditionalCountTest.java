package org.kostenko.example.jpa;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Test;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * @author kostenko
 */
public class JpaConditionalCountTest {

    private static EntityManager em;

    @BeforeClass
    public static void init() {
        em = Persistence.createEntityManagerFactory("myDSTest").createEntityManager();
        em.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            BlogEntity entity = new BlogEntity();
            entity.setTitle("Title #" + i);
            entity.setBody("Body #" + i);
            em.persist(entity);
        }
        em.getTransaction().commit();
    }

    @Test
    public void shouldSelectConditionalCountBySql() throws Exception {
        Number result = (Number) em.createNativeQuery("SELECT SUM(CASE WHEN id > 5 THEN 1 ELSE 0 END) FROM blogentity").getSingleResult();
        Assert.assertEquals(5, result.intValue());
    }

    @Test
    public void shouldSelectConditionalCountByJpql() throws Exception {
        Number result = (Number) em.createQuery("SELECT SUM(CASE WHEN b.id > 5 THEN 1 ELSE 0 END) FROM BlogEntity b").getSingleResult();
        Assert.assertEquals(5, result.intValue());
    }

    @Test
    public void shouldSelectConditionalCountByCriteriaApi() throws Exception {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Number> query = cb.createQuery(Number.class);
        Root<BlogEntity> blogEntity = query.from(BlogEntity.class);

        query.select(
                cb.sum(
                        cb.<Number>selectCase()
                                .when(cb.gt(blogEntity.get("id"), 5), 1)
                                .otherwise(0)
                )
        );

        Number result = em.createQuery(query).getSingleResult();
        Assert.assertEquals(5, result.intValue());
    }
}
