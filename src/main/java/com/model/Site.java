package com.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class Site implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "status_time")
    private Date statusTime;

    @Type(type = "text")
    @Column(name = "last_error")
    private String lastError;

    private String url;

    private String name;

    public Site(){}

    public Site(Status status, Date statusTime, String lastError, String url, String name) {
        this.status = status;
        this.statusTime = statusTime;
        this.lastError = lastError;
        this.url = url;
        this.name = name;
    }
}
