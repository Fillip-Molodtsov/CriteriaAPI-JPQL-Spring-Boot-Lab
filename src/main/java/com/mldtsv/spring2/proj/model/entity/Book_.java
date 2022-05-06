package com.mldtsv.spring2.proj.model.entity;

import com.mldtsv.spring2.proj.model.entity.*;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {
    public static volatile SingularAttribute<Book, Long> id;
    public static volatile SingularAttribute<Book, String> title;
    public static volatile SingularAttribute<Book, String> description;
    public static volatile SingularAttribute<Book, Integer> pages;
    public static volatile SetAttribute<Book, Genre> genres;
    public static volatile SingularAttribute<Book, Publisher> publisher;
    public static volatile SingularAttribute<Book, BookType> bookType;
    public static volatile SingularAttribute<Book, BigDecimal> price;
    // NPE if you use CollectionAttribute
    public static volatile SetAttribute<Book, Author> authors;
}
