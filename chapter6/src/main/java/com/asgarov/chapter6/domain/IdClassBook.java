package com.asgarov.chapter6.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@IdClass(IdClassBook.EmbeddedISBN.class)
public class IdClassBook {

    @Id
    int group;
    @Id
    int publisher;
    @Id
    int title;
    @Id
    int checkdigit;
    String name;

    @Getter
    @Setter
    @NoArgsConstructor
    static class EmbeddedISBN implements Serializable {
        @Column(name="group_number")
        private int group;

        private int publisher;
        private int title;
        private int checkdigit;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IdClassBook.EmbeddedISBN that = (IdClassBook.EmbeddedISBN) o;
            return group == that.group && publisher == that.publisher && title == that.title && checkdigit == that.checkdigit;
        }

        @Override
        public int hashCode() {
            return Objects.hash(group, publisher, title, checkdigit);
        }
    }

}
