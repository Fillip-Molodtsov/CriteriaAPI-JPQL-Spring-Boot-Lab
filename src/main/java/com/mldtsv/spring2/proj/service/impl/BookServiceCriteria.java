package com.mldtsv.spring2.proj.service.impl;

import com.mldtsv.spring2.proj.model.QueryParam;
import com.mldtsv.spring2.proj.model.entity.Book;
import com.mldtsv.spring2.proj.repository.AuthorRepository;
import com.mldtsv.spring2.proj.repository.BookRepositoryCriteria;
import com.mldtsv.spring2.proj.repository.GenreRepository;
import com.mldtsv.spring2.proj.repository.PublisherRepository;
import com.mldtsv.spring2.proj.repository.specs.CriteriaSpecs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
@Service
public class BookServiceCriteria {
    private BookRepositoryCriteria repositoryCriteria;
    private AuthorRepository authorRepository;
    private GenreRepository genreRepository;
    private PublisherRepository publisherRepository;


    public Collection<Book> findAllByAuthor(String name) {
        var author = authorRepository.findByFullName(name);
        return author.map(a -> repositoryCriteria.findAll(CriteriaSpecs.specificAuthor(a)))
                .orElse(Collections.emptyList());
    }

    public Collection<Book> findAllByAuthorGroup(Set<String> names) {
        var authors = authorRepository.findAllByFullNameIn(names);
        return repositoryCriteria.findAll(CriteriaSpecs.findAllByAuthorGroup(authors, (long) authors.size()));
    }

    public Collection<Book> findAllByAuthorAtLeastOne(Set<String> names) {
        var authors = authorRepository.findAllByFullNameIn(names);
        return repositoryCriteria.findAll(CriteriaSpecs.findAllByAuthorAtLeastOne(authors));
    }

    public Collection<Book> findAllByPriceRange(BigDecimal low, BigDecimal high){
        return repositoryCriteria.findAll(CriteriaSpecs.findAllByPriceRange(low, high));
    }

    public Collection<Book> findAllByPrice(BigDecimal price){
        return repositoryCriteria.findAll(CriteriaSpecs.findAllByPrice(price));
    }

    public Collection<Book> findAllByPublisher(String name) {
        var pub = publisherRepository.findByName(name);
        return pub.map(p -> repositoryCriteria.findAll(CriteriaSpecs.findAllByPublisher(p)))
                .orElse(Collections.emptyList());
    }

    public Collection<Book> findAllByPublishers(Set<String> names) {
        var publishers = publisherRepository.findAllByNameIn(names);
        return repositoryCriteria.findAll(CriteriaSpecs.findAllByPublishers(publishers));
    }

    public Collection<Book> findAllByGenre(String name) {
        var genre = genreRepository.findByName(name);
        return genre.map(g -> repositoryCriteria.findAll(CriteriaSpecs.findAllByGenre(g)))
                .orElse(Collections.emptyList());
    }

    public Collection<Book> findAllByGenreGroup(Set<String> names) {
        var genres = genreRepository.findAllByNameIn(names);
        return repositoryCriteria.findAll(CriteriaSpecs.findAllByGenreGroup(genres, (long) genres.size()));
    }

    public Collection<Book> findAllByGenreAtLeastOne(Set<String> names) {
        var genres = genreRepository.findAllByNameIn(names);
        return repositoryCriteria.findAll(CriteriaSpecs.findAllByGenreAtLeastOne(genres));
    }

    public Collection<Book> findAllByPageRange( Integer low, Integer high){
        return repositoryCriteria.findAll(CriteriaSpecs.findAllByPageRange(low, high));
    }

    public Collection<Book> findAllByPages(Integer pages){
        return repositoryCriteria.findAll(CriteriaSpecs.findAllByPages(pages));
    }

    public Collection<Book> findAllByTextSearch(String input) {
        return repositoryCriteria.findAll(CriteriaSpecs.findAllByTextSearch(input));
    }

    public Collection<Book> customSearch(Boolean isAnd, Collection<QueryParam> params) {
        if(params.isEmpty()) return repositoryCriteria.findAll();
        return repositoryCriteria.findAll(CriteriaSpecs.customSearch(isAnd, params));
    }
}
