package com.repository;

import com.model.Index;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IdexRepository extends CrudRepository<Index, Integer> {
    List<Index> findByLemmaId (int lemmaId);
    List<Index> findByPageId (int pageId);
    Index findByLemmaIdAndPageId (int lemmaId, int pageId);
}
