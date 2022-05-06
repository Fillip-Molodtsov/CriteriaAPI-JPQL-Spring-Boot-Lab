package com.mldtsv.spring2.proj.service.impl;

import com.mldtsv.spring2.proj.model.entity.Book;
import com.mldtsv.spring2.proj.repository.AuthorRepository;
import com.mldtsv.spring2.proj.repository.BookRepositoryJPQL;
import com.mldtsv.spring2.proj.repository.GenreRepository;
import com.mldtsv.spring2.proj.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
@Service
public class BookServiceJPQL {
    private BookRepositoryJPQL repositoryJPQL;
    private AuthorRepository authorRepository;
    private GenreRepository genreRepository;
    private PublisherRepository publisherRepository;

    public Collection<Book> findAllByAuthor(String name) {
        var author = authorRepository.findByFullName(name);
        return author.map(a -> repositoryJPQL.findAllByAuthor(a))
                .orElse(Collections.emptySet());
    }

    public Collection<Book> findAllByAuthorGroup(Set<String> names) {
        var authors = authorRepository.findAllByFullNameIn(names);
        return repositoryJPQL.findAllByAuthorGroup(authors, (long) authors.size());
    }

    public Collection<Book> findAllByAuthorAtLeastOne(Set<String> names) {
        var authors = authorRepository.findAllByFullNameIn(names);
        return repositoryJPQL.findAllByAuthorAtLeastOne(authors);
    }

    public Collection<Book> findAllByPriceRange( BigDecimal low,  BigDecimal high){
        return repositoryJPQL.findAllByPriceRange(low, high);
    }

    public Collection<Book> findAllByPrice(BigDecimal price){
        return repositoryJPQL.findAllByPrice(price);
    }

    public Collection<Book> findAllByPublisher(String name) {
        var pub = publisherRepository.findByName(name);
        return pub.map(p -> repositoryJPQL.findAllByPublisher(p))
                .orElse(Collections.emptySet());
    }

    public Collection<Book> findAllByPublishers(Set<String> names) {
        var publishers = publisherRepository.findAllByNameIn(names);
        return repositoryJPQL.findAllByPublishers(publishers);
    }

    public Collection<Book> findAllByGenre(String name) {
        var genre = genreRepository.findByName(name);
        return genre.map(g -> repositoryJPQL.findAllByGenre(g))
                .orElse(Collections.emptySet());
    }

    public Collection<Book> findAllByGenreGroup(Set<String> names) {
        var genres = genreRepository.findAllByNameIn(names);
        return repositoryJPQL.findAllByGenreGroup(genres, (long) genres.size());
    }

    public Collection<Book> findAllByGenreAtLeastOne(Set<String> names) {
        var genres = genreRepository.findAllByNameIn(names);
        return repositoryJPQL.findAllByGenreAtLeastOne(genres);
    }

    public Collection<Book> findAllByPageRange( Integer low, Integer high){
        return repositoryJPQL.findAllByPageRange(low, high);
    }

    public Collection<Book> findAllByPages(Integer pages){
        return repositoryJPQL.findAllByPages(pages);
    }

    public Collection<Book> findAllByTextSearch(String input) {
        return repositoryJPQL.findAllByTextSearch(input);
    }
}
