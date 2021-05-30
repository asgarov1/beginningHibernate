package com.asgarov.chapter6.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class ISBN implements Serializable {
    @Column(name="group_number")
    private int group;

    private int publisher;
    private int title;
    private int checkdigit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISBN)) return false;
        ISBN isbn = (ISBN) o;
        return getGroup() == isbn.getGroup() && getPublisher() == isbn.getPublisher() && getTitle() == isbn.getTitle() && getCheckdigit() == isbn.getCheckdigit();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroup(), getPublisher(), getTitle(), getCheckdigit());
    }
}
