package org.kostenko.example.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.Test;
import org.kostenko.example.jpa.relationship.Author;
import org.kostenko.example.jpa.relationship.Book;

/**
 *
 * @author kostenko
 */
public class ManyToOneTest {
    
    @Test
    public void relationManyToOneTest() {
        
        EntityManager em = Persistence.createEntityManagerFactory("myDSTest").createEntityManager();
        
        this.generateTestData(em);
        
        List <Book> books =
            em.createQuery("FROM Book", Book.class).getResultList();
            
        // lazy loading check
        for (Book b : books) {
            System.out.println("Bookd:" + b.getName());
            System.out.println("AuthorId:" + b.getAuthorId());
            
            System.out.println("-------------------------------");
            System.out.println("Author:" + b.getAuthor().getId());
            System.out.println("Author:" + b.getAuthor().getName());
        }
        
        // JPQL with direct id reference
        books = em.createQuery("FROM Book where authorId = 1", Book.class).getResultList();
        
        // JPQL with direct author.id reference
        books = em.createQuery("FROM Book where author.id = 1", Book.class).getResultList();
    }

    private void generateTestData(EntityManager em) {
        em.getTransaction().begin();

        Author author = new Author();
        author.setName("A Name");
        em.persist(author);

        Book book = new Book();
        book.setName("Book Name");
        //book.setAuthorId(author.getId());
        book.setAuthor(author);
        em.persist(book);
        em.getTransaction().commit();
        em.clear();
    }
}
