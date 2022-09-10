package com.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {
    private String uri;
    private String title;
    private String snippet;
    private double relevance;

}
