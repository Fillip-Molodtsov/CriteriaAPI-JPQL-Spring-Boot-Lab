package com.mldtsv.spring2.proj.repository;

import com.mldtsv.spring2.proj.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Set<Author> findAllByFullNameIn(Collection<@NotEmpty String> fullName);
    Optional<Author> findByFullName(String name);
}
