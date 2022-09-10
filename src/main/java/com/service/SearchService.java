package com.service;

import com.model.Request;
import com.service.responcse.ResponseService;

import java.io.IOException;

public interface SearchService {
    ResponseService getResponse (Request request, String url, int offset, int limit) throws IOException;
}
