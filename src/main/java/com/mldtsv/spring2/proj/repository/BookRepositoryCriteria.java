package com.mldtsv.spring2.proj.repository;

import com.mldtsv.spring2.proj.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepositoryCriteria extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
