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
@Table(name="lemma",
        indexes = {@Index(name = "lemma_INDX", columnList = "lemma")})
public class Lemma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String lemma;

    private int frequency;
    @Column(name = "site_id")
    private int siteId;

    public Lemma(){}

    public Lemma(String lemma, int frequency, int siteId){
        this.lemma = lemma;
        this.frequency = frequency;
        this.siteId = siteId;
    }


}
