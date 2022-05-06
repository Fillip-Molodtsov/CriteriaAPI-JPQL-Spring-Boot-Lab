package com.mldtsv.spring2.proj.repository.specs;

import com.mldtsv.spring2.proj.model.MultiQueryParam;
import com.mldtsv.spring2.proj.model.QueryParam;
import com.mldtsv.spring2.proj.model.SingleQueryParam;
import com.mldtsv.spring2.proj.model.entity.*;
import com.mldtsv.spring2.proj.model.meta.InnerSelectOperators;
import com.mldtsv.spring2.proj.model.meta.RQB;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.mldtsv.spring2.proj.model.meta.InnerSelectOperators.EQUAL;
import static com.mldtsv.spring2.proj.model.meta.InnerSelectOperators.GREATER_THAN;

public class CriteriaSpecs {
    public static Specification<Book> specificAuthor(Author sAuthor) {
        return (r, q, cb) -> {
            var author = r.join(Book_.authors);
            return cb.equal(author, sAuthor);
        };
    }

    public static Specification<Book> findAllByAuthorGroup(Collection<Author> authors, Long authorsAmount) {
        return (r, q, cb) -> {
            var rqb = new RQB<>(r,q,cb);
            return cb.and(
                    innerSelectCount(rqb, authors, Book_.authors, true, EQUAL, authorsAmount),
                    innerSelectCount(rqb, authors, Book_.authors, false, EQUAL, 0L)
            );
        };
    }

    public static Specification<Book> findAllByAuthorAtLeastOne(Collection<Author> authors) {
        return (r, q, cb) -> {
            var rqb = new RQB<>(r,q,cb);
            return innerSelectCount(rqb, authors, Book_.authors, true, GREATER_THAN, 0L);
        };
    }

    public static Specification<Book> findAllByPriceRange(BigDecimal low, BigDecimal high) {
        return (r, q, cb) -> cb.between(r.get(Book_.price), low, high);
    }

    public static Specification<Book> findAllByPrice(BigDecimal price) {
        return (r, q, cb) -> cb.equal(r.get(Book_.price), price);
    }

    public static Specification<Book> findAllByPublisher(Publisher publisher) {
        return (r, q, cb) -> {
            var pub = r.join(Book_.publisher);
            return cb.equal(pub, publisher);
        };
    }

    public static Specification<Book> findAllByPublishers(Collection<Publisher> publishers) {
        return (r, q, cb) -> {
            var pub = r.join(Book_.publisher);
            return pub.in(publishers);
        };
    }

    public static Specification<Book> findAllByGenre(Genre genre) {
        return (r, q, cb) -> {
            var g = r.join(Book_.genres);
            return cb.equal(g, genre);
        };
    }

    public static Specification<Book> findAllByGenreGroup(Collection<Genre> genres, Long genresAmount) {
        return (r, q, cb) -> {
            var rqb = new RQB<>(r,q,cb);
            return cb.and(
                    innerSelectCount(rqb, genres, Book_.genres, true, EQUAL, genresAmount),
                    innerSelectCount(rqb, genres, Book_.genres, false, EQUAL, 0L)
            );
        };
    }

    public static Specification<Book> findAllByGenreAtLeastOne(Collection<Genre> genres) {
        return (r, q, cb) -> {
            var rqb = new RQB<>(r,q,cb);
            return innerSelectCount(rqb, genres, Book_.genres, true, GREATER_THAN, 0L);
        };
    }

    public static Specification<Book> findAllByPageRange(Integer low, Integer high) {
        return (r, q, cb) -> cb.between(r.get(Book_.pages), low, high);
    }

    public static Specification<Book> findAllByPages(Integer pages) {
        return (r, q, cb) -> cb.equal(r.get(Book_.pages), pages);
    }

    public static Specification<Book> findAllByTextSearch(String input) {
        return (r, q, cb) -> {
            var rqb = new RQB<>(r,q,cb);
            return cb.or(
                customLike(cb, r.get(Book_.title), input),
                customLike(cb, r.get(Book_.description), input),
                findLikeFieldsText(rqb, Author.class, Book_.authors, input),
                findLikeFieldsText(rqb, Publisher.class, Book_.publisher, input),
                findLikeFieldsText(rqb, Genre.class, Book_.genres, input)
            );
        };
    }

    public static Specification<Book> customSearch(Boolean isAnd, Collection<QueryParam> params) {
        return (r, q, cb) -> {
            var rqb = new RQB<>(r,q,cb);
            var predicates = params.stream().map( p -> toPredicate(rqb, p))
                    .toArray(Predicate[]::new);
            return isAnd ? cb.and(predicates) : cb.or(predicates);
        };
    }

    private static Predicate toPredicate(RQB<Book> rqb, QueryParam param) {
        var cb = rqb.getCb();
        var r = rqb.getR();

        switch (param.getBookField()) {
            case TITLE:
                if( param instanceof SingleQueryParam) {
                    var castedP = (SingleQueryParam) param;
                    return customLike(cb, r.get(Book_.title), (String) castedP.getValue());
                } else { throw new IllegalStateException();}
            case DESCRIPTION:
                if( param instanceof SingleQueryParam) {
                    var castedP = (SingleQueryParam) param;
                    return customLike(cb, r.get(Book_.description), (String) castedP.getValue());
                } else { throw new IllegalStateException();}
            case PAGES:
                if( param instanceof SingleQueryParam) {
                    var castedP = (SingleQueryParam) param;
                    return cb.equal(r.get(Book_.pages), castedP.getValue());
                } else {
                    var castedP = (MultiQueryParam) param;
                    var array = (Integer[]) castedP.getValues();
                    return cb.between(r.get(Book_.pages), array[0], array[1]);
                }
            case TYPE:
                if( param instanceof SingleQueryParam) {
                    var castedP = (SingleQueryParam) param;
                    return cb.equal(r.get(Book_.bookType), castedP.getValue());
                } else { throw new IllegalStateException();}
            case PRICE:
                if( param instanceof SingleQueryParam) {
                    var castedP = (SingleQueryParam) param;
                    return cb.equal(r.get(Book_.price), castedP.getValue());
                } else {
                    var castedP = (MultiQueryParam) param;
                    var array = (BigDecimal[]) castedP.getValues();
                    return cb.between(r.get(Book_.price), array[0], array[1]);
                }
            case PUBLISHER:
                if( param instanceof SingleQueryParam) {
                    var castedP = (SingleQueryParam) param;
                    return cb.equal(r.join(Book_.publisher), castedP.getValue());
                } else {
                    var castedP = (MultiQueryParam) param;
                    var array = (Publisher[]) castedP.getValues();
                    return r.join(Book_.publisher).in(array);
                }
            case AUTHORS:
                if( param instanceof SingleQueryParam) {
                    var castedP = (SingleQueryParam) param;
                    return cb.equal(r.join(Book_.authors), castedP.getValue());
                } else {
                    var castedP = (MultiQueryParam) param;
                    var array = (Author[]) castedP.getValues();
                    return innerSelectCount(rqb, List.of(array), Book_.authors, true, GREATER_THAN, 0L);
                }
            case GENRES:
                if( param instanceof SingleQueryParam) {
                    var castedP = (SingleQueryParam) param;
                    return cb.equal(r.join(Book_.genres), castedP.getValue());
                } else {
                    var castedP = (MultiQueryParam) param;
                    var array = (Genre[]) castedP.getValues();
                    return innerSelectCount(rqb, List.of(array), Book_.genres, true, GREATER_THAN, 0L);
                }
            default:
                return null;
        }
    }

    private static Predicate findLikeFieldsText
            (RQB<Book> rqb, Class<?> clazz, Attribute<Book, ?> joinAttr, String input) {
        var cb = rqb.getCb();
        var r = rqb.getR();
        var q = rqb.getQ();
        var subIn =  q.subquery(Long.class);
        var subInR = subIn.from(Book.class);
        var isSingularJoin = clazz.getSimpleName().equals("Publisher");
        var f =  isSingularJoin ? subInR.join((SingularAttribute<Book, ?>)joinAttr ) :
                subInR.join((SetAttribute<Book, ?>)joinAttr);
        var compAttr = clazz.getSimpleName().equals("Author") ? "fullName" : "name" ;
        subIn.select(cb.count(f))
            .where(cb.and(
                cb.equal(r, subInR),
                customLike(cb, f.get(compAttr), input)
            ));
        return cb.greaterThan(subIn, 0L);
    }

    private static Predicate customLike
            (CriteriaBuilder cb, Path<String> path, String input) {
        return cb.like(cb.lower(path), "%" + input.toLowerCase() + "%");
    }

    private static <Join> Predicate innerSelectCount
            (RQB<Book> rqb, Collection<Join> entitiesSet, SetAttribute<Book,?> attribute, Boolean isIn, InnerSelectOperators op, Long value) {
        var subIn = rqb.getQ().subquery(Long.class);
        var subInR = subIn.from(Book.class);
        var a1 = subInR.join(attribute);
        var inExpression = isIn ? a1.in(entitiesSet): rqb.getCb().not(a1.in(entitiesSet));
        subIn
            .select(rqb.getCb().count(a1))
            .where(rqb.getCb().and(
                    rqb.getCb().equal(rqb.getR(),subInR),
                    inExpression
            ));
        switch (op) {
            case GREATER_THAN:
                return rqb.getCb().greaterThan(subIn, value);
            case EQUAL:
            default:
                return rqb.getCb().equal(subIn, value);
        }
    }

}
