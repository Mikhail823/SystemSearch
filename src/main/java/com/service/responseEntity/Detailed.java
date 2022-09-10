package com.service.responseEntity;


import com.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Detailed {

    String url;
    String name;
    Status status;
    long statusTime;
    String error;
    long pages;
    long lemmas;

}
