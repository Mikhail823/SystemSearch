package com.service;

import com.service.responcse.ResponseService;
import org.springframework.stereotype.Component;


public interface IndexingService {

    ResponseService startIndexingAll();
    ResponseService stopIndexing();
    ResponseService startIndexingOne(String url);
}
