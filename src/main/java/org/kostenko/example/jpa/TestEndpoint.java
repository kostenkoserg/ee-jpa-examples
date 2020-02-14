package org.kostenko.example.jpa;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.kostenko.example.jpa.relationship.Author;
import org.kostenko.example.jpa.relationship.Book;

/**
 *
 * @author kostenko
 */
@Stateless
@Path("/test")
@ApplicationPath("/")
public class TestEndpoint extends Application {

    @PersistenceContext
    EntityManager entityManager;

    @GET
    @Path("/insert")
    public Response insert() {

        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setTitle("title");
        blogEntity.setBody("this is body");

        entityManager.persist(blogEntity);

        return Response.ok().entity("OK").build();
    }

    @GET
    @Path("/select")
    public Response select() {

        List<BlogEntity> posts = entityManager.createQuery("FROM BlogEntity where title = :title", BlogEntity.class)
                .setParameter("title", "title")
                .getResultList();

        if (posts.isEmpty()) {
            return Response.ok().entity("No etities found").build();
        } else {
            return Response.ok().entity(posts.toString()).build();
        }
    }

    @GET
    @Path("/relationa")
    @Produces(MediaType.APPLICATION_JSON)
    public Response relation() {
        Author author = new Author();
        author.setName("A Name");
        entityManager.persist(author);
        return Response.ok().entity(entityManager.createQuery("from Author").getResultList()).build();
    }

    @GET
    @Path("/relationb/{id}")
    @Produces(MediaType.APPLICATION_JSON)    
    public Response relationq(@PathParam("id") Long id) {
        Book book = new Book();
        book.setName("Book Name");
        book.setAuthorId(id);
        entityManager.persist(book);
        return Response.ok().entity(entityManager.createQuery("from Book").getResultList()).build();
    }

}
