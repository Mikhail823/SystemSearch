package com.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Index;
import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "page",
        indexes = {@Index(name = "Path_INDX", columnList = "path")})
public class Page implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String path;

    private int code;
    @Type(type = "text")
    private String content;

    @Column(name = "site_id")
    private int siteId;

    public Page(){}

    public Page(String path, int code, String content) {
        this.path = path;
        this.code = code;
        this.content = content;
    }
}
