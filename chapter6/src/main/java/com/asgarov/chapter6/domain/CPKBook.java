package com.asgarov.chapter6.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CPKBook {

    @Id
    private ISBN id;

    private String name;
}
