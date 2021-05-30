package com.asgarov.chapter6;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"working_title", "pages"})})
public class Book {
    @Id
//    @SequenceGenerator(name = "seq1", sequenceName = "HIB_SEQ")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
    @TableGenerator(name = "tablegen", table = "ID_TABLE", pkColumnName = "ID", valueColumnName = "NEXT_ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tablegen")
    private int id;

    @Column(name = "working_title", length = 200, nullable = false)
    private String title;
    private int pages;

    public Book(String title, int pages) {
        this.title = title;
        this.pages = pages;
    }
}

