package com.controllers;

import com.service.IndexService;
import com.service.IndexingService;
import com.service.responcse.ResponseService;
import com.validationerrors.SearchValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexingController {

    private final IndexingService index;
    @Autowired
    public IndexingController(IndexingService index) {
        this.index = index;
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<Object> startIndexingAll() {
        ResponseService response = index.startIndexingAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<Object> stopIndexingAll() {
        ResponseService response = index.stopIndexing();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/indexPage")
    public ResponseEntity<Object> startIndexingOne(
            @RequestParam(name="url", required=false, defaultValue=" ") String url) {
        ResponseService response = index.startIndexingOne(url);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public SearchValidationError handleException(Exception ex){
        return new SearchValidationError(ex.getMessage());
    }
}
