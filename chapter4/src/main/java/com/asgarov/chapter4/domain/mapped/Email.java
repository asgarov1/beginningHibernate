package com.asgarov.chapter4.domain.mapped;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "mapped_email")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "mapped_email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subject;

    @OneToOne(mappedBy = "email")
    private Message message;

    public Email(String subject) {
        this.subject = subject;
    }

}
