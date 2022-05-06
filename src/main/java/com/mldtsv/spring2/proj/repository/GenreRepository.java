package com.mldtsv.spring2.proj.repository;

import com.mldtsv.spring2.proj.model.entity.Author;
import com.mldtsv.spring2.proj.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Set<Genre> findAllByNameIn(Collection<@NotEmpty String> fullName);
    Optional<Genre> findByName(String name);
}
