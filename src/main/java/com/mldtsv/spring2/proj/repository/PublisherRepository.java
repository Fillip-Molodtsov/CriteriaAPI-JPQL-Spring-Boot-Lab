package com.mldtsv.spring2.proj.repository;

import com.mldtsv.spring2.proj.model.entity.Author;
import com.mldtsv.spring2.proj.model.entity.Genre;
import com.mldtsv.spring2.proj.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Set<Publisher> findAllByNameIn(Collection<@NotEmpty String> fullName);
    Optional<Publisher> findByName(String name);
}
