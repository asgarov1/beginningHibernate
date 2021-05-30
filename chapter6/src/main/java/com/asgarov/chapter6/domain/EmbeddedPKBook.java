package com.asgarov.chapter6.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class EmbeddedPKBook {
    @EmbeddedId
    private EmbeddedISBN id;

    private String name;

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
            EmbeddedISBN that = (EmbeddedISBN) o;
            return group == that.group && publisher == that.publisher && title == that.title && checkdigit == that.checkdigit;
        }

        @Override
        public int hashCode() {
            return Objects.hash(group, publisher, title, checkdigit);
        }
    }
}
