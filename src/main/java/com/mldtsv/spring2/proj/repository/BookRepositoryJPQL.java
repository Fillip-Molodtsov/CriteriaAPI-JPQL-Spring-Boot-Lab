package com.mldtsv.spring2.proj.repository;

import com.mldtsv.spring2.proj.model.entity.Author;
import com.mldtsv.spring2.proj.model.entity.Book;
import com.mldtsv.spring2.proj.model.entity.Genre;
import com.mldtsv.spring2.proj.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

@Repository
public interface BookRepositoryJPQL extends JpaRepository<Book, Long> {
    @Query("select b from Book b left join fetch b.authors a where a = :author")
    Set<Book> findAllByAuthor(@Param("author") Author author);

    @Query("select b from Book b " +
            "where (select count(a1) from Book b1 " +
            "       join b1.authors a1 " +
            "       where b1 = b and a1 in :authors) = :authorsAmount " +
            "and (select count(a2) from Book b2 " +
            "     join b2.authors a2 " +
            "     where b2 = b and a2 not in :authors) = 0")
    Set<Book> findAllByAuthorGroup(@Param("authors") Collection<Author> authors, @Param("authorsAmount") Long authorsAmount);

    @Query("select b from Book b " +
            "where exists (select a1 from Book b1 " +
            "       join b1.authors a1 " +
            "       where b1 = b and a1 in :authors)")
    Set<Book> findAllByAuthorAtLeastOne(@Param("authors") Collection<Author> authors);

    @Query("select b from Book b where b.price between :low and :high")
    Set<Book> findAllByPriceRange(@Param("low") BigDecimal low, @Param("high") BigDecimal high);

    @Query("select b from Book b where b.price  = :price")
    Set<Book> findAllByPrice(@Param("price") BigDecimal price);

    @Query("select b from Book b left join fetch b.publisher p where p = :pub")
    Set<Book> findAllByPublisher(@Param("pub") Publisher publisher);

    @Query("select b from Book b join b.publisher p where p in :pubs")
    Set<Book> findAllByPublishers(@Param("pubs") Collection<Publisher> publishers);

    @Query("select b from Book b left join fetch b.genres g where g = :genre")
    Set<Book> findAllByGenre(@Param("genre") Genre genre);

    @Query("select b from Book b " +
            "where (select count(g1) from Book b1 " +
            "       join b1.genres g1 " +
            "       where b1 = b and g1 in :genres) = :genresAmount " +
            "and (select count(g2) from Book b2 " +
            "     join b2.genres g2 " +
            "     where b2 = b and g2 not in :genres) = 0")
    Set<Book> findAllByGenreGroup(@Param("genres") Collection<Genre> genres, @Param("genresAmount") Long genresAmount);

    @Query("select b from Book b " +
            "where exists (select g1 from Book b1 " +
            "       join b1.genres g1 " +
            "       where b1 = b and g1 in :genres)")
    Set<Book> findAllByGenreAtLeastOne(@Param("genres") Collection<Genre> genres);

    @Query("select b from Book b where b.pages between :low and :high")
    Set<Book> findAllByPageRange(@Param("low") Integer low, @Param("high") Integer high);

    @Query("select b from Book b where b.pages  = :pages")
    Set<Book> findAllByPages(@Param("pages") Integer pages);

    @Query("select b from Book b where lower(b.title) like lower(concat('%',:input,'%')) or " +
            "lower(b.description) like lower(concat('%',:input,'%')) or "+
            "exists (select g1 from Book b1 join b1.genres g1 where b1 = b and  lower(g1.name) like lower(concat('%',:input,'%'))) or " +
            "exists (select a2 from Book b2 join b2.authors a2 where b2 = b and lower(a2.fullName) like lower(concat('%',:input,'%'))) or " +
            "exists (select p3 from Book b3 join b3.publisher p3 where lower(p3.name) like lower(concat('%',:input,'%')))"
    )
    Set<Book> findAllByTextSearch(@Param("input") String input);
}
