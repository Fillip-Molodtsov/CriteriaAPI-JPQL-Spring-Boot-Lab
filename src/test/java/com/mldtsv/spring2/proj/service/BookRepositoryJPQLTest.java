package com.mldtsv.spring2.proj.service;

import com.mldtsv.spring2.proj.model.BookField;
import com.mldtsv.spring2.proj.model.MultiQueryParam;
import com.mldtsv.spring2.proj.model.QueryParam;
import com.mldtsv.spring2.proj.model.SingleQueryParam;
import com.mldtsv.spring2.proj.model.entity.Genre;
import com.mldtsv.spring2.proj.model.entity.Publisher;
import com.mldtsv.spring2.proj.repository.AuthorRepository;
import com.mldtsv.spring2.proj.repository.GenreRepository;
import com.mldtsv.spring2.proj.repository.PublisherRepository;
import com.mldtsv.spring2.proj.service.impl.BookServiceCriteria;
import com.mldtsv.spring2.proj.service.impl.BookServiceJPQL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
*  Test based on data stored in a local DB
* */
@SpringBootTest
public class BookRepositoryJPQLTest {
    @Autowired
    private BookServiceJPQL bookServiceJPQL;
    @Autowired
    private BookServiceCriteria bookServiceCriteria;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void test() {
        var res = bookServiceJPQL.findAllByAuthor("Jordan");
        assertEquals(res.size(), 0);
    }

    @Test
    public void test2() {
        var res = bookServiceJPQL.findAllByAuthorGroup(new LinkedHashSet<>(Arrays.asList("Tom", "Jerry")));
        assertEquals(res.size(), 1);
    }

    @Test
    public void test3() {
        var res = bookServiceJPQL.findAllByAuthorAtLeastOne(new LinkedHashSet<>(Arrays.asList("Tom", "Jerry")));
        assertEquals(res.size(), 2);
    }

    @Test
    public void test4() {
        var res = bookServiceJPQL.findAllByTextSearch("J");
        assertEquals(res.size(), 1);
    }

    @Test
    public void test5() {
        var res = bookServiceJPQL.findAllByTextSearch("xpotter");
        assertEquals(res.size(), 1);
    }

    @Test
    public void test6() {
        var res = bookServiceCriteria.findAllByAuthor("Tom");
        assertEquals(res.size(), 2);
    }

    @Test
    public void test7() {
        var res = bookServiceCriteria.findAllByAuthorAtLeastOne(new LinkedHashSet<>(Arrays.asList("Tom", "Jerry")));
        assertEquals(2, res.size());
    }

    @Test
    public void test8() {
        var res = bookServiceCriteria.findAllByAuthorGroup(new LinkedHashSet<>(Arrays.asList("Tom", "Jerry")));
        assertEquals( 1, res.size());
    }

    @Test
    public void test9() {
        var res = bookServiceCriteria.findAllByTextSearch("Row");
        assertEquals(res.size(), 1);
    }

    @Test
    public void test10() {
        var publisher = publisherRepository.findAll().toArray(Publisher[]::new);
        var genres = new Genre[]{genreRepository.findByName("Comedy").get()};
        Collection<QueryParam>  list= List.of(new QueryParam[]{
                new SingleQueryParam(BookField.DESCRIPTION, "xpotter"),
                new MultiQueryParam(BookField.PUBLISHER, publisher),
                new MultiQueryParam(BookField.GENRES, genres)
        });
        var res = bookServiceCriteria.customSearch(true, list);
        assertEquals(res.size(), 0);
    }

    @Test
    public void test11() {
        var publisher = publisherRepository.findAll().toArray(Publisher[]::new);
        var genres = new Genre[]{genreRepository.findByName("Comedy").get()};
        Collection<QueryParam>  list= List.of(new QueryParam[]{
                new SingleQueryParam(BookField.DESCRIPTION, "xpotter"),
                new MultiQueryParam(BookField.PUBLISHER, publisher),
                new MultiQueryParam(BookField.GENRES, genres)
        });
        var res = bookServiceCriteria.customSearch(false, list);
        assertEquals(res.size(), 3);
    }

    @Test
    public void test12() {
        Collection<QueryParam>  list= List.of();
        var res = bookServiceCriteria.customSearch(false, list);
        assertEquals(res.size(), 3);
    }
}
