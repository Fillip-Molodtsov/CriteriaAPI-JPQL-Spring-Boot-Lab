package com.mldtsv.spring2.proj.model.entity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Author.class)
public abstract class Author_ {
    public static volatile SingularAttribute<Author, Long> id;
    public static volatile SingularAttribute<Author, String> fullName;
}
