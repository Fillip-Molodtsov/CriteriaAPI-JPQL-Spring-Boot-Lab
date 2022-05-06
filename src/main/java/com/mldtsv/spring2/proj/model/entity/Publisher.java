package com.mldtsv.spring2.proj.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisher_seq")
    @SequenceGenerator(name = "publisher_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Publisher publisher = (Publisher) o;
        return id != null && Objects.equals(id, publisher.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}