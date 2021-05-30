package com.asgarov.chapter3.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Person subject;

    @OneToOne
    private Person observer;

    @OneToOne
    private Skill skill;
    private Integer ranking;
}
