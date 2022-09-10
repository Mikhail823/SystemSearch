package com.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.persistence.Index;
import java.io.Serializable;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name="field",
        indexes = {@Index(name = "selector_INDX", columnList = "selector")})
public class Field implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String selector;

    private float weight;

    public Field(){}

    public Field(String name, String selector, float weight){
        this.name = name;
        this.selector = selector;
        this.weight = weight;
    }
}
