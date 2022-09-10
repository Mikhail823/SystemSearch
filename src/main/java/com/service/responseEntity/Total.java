package com.service.responseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Total {

    long sites;
    long pages;
    long lemmas;
    boolean isIndexing;

}
