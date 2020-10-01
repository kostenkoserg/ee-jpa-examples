package org.kostenko.example.jpa.relationship;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author kostenko
 */
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JoinColumn(name = "author", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY)
    private Author author;
    
    @Column(name = "author" /*,insertable = false, updatable = false*/)
    private long authorId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        setAuthorId(author.getId());
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", name=" + name + ", authorId=";
    }

}
