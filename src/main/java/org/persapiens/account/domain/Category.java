package org.persapiens.account.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.Singular;
import lombok.EqualsAndHashCode;

@SequenceGenerator(sequenceName = "seq_category", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(of = "description")
@ToString(of = "description")
@SuperBuilder
@Getter
@Setter
public class Category implements Comparable<Category> {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String description;

    @OneToMany(mappedBy = "category")
    @Singular
    private Set<Account> accounts;

    @Override
    public int compareTo(Category o) {
        return this.description.compareTo(o.getDescription());
    }
}