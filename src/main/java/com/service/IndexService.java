package com.service;

import com.model.Index;

import java.util.List;

public interface IndexService {
    List<Index> getAllIndexingByLemmaId(int lemmaId);
    List<Index> getAllIndexingByPageId(int pageId);
    void deleteAllIndexing(List<Index> indexingList);
    Index getIndex (int lemmaId, int pageId);
    void save(Index indexing);
}
