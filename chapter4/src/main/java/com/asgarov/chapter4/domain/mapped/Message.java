package com.asgarov.chapter4.domain.mapped;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "mapped_message")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "email")
@Table(name = "mapped_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @OneToOne
    private Email email;

    public Message(String content) {
        this.content = content;
    }
}
