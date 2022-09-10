package com.service.imp;
import com.repository.IdexRepository;
import com.model.Index;
import com.service.IndexService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    private Log log = LogFactory.getLog(IndexServiceImpl.class);
    private final IdexRepository idexRepository;

    @Autowired
    public IndexServiceImpl(IdexRepository idexRepository) {
        this.idexRepository = idexRepository;
    }

    @Override
    public List<Index> getAllIndexingByLemmaId(int lemmaId) {
        return idexRepository.findByLemmaId(lemmaId);
    }

    @Override
    public List<Index> getAllIndexingByPageId(int pageId) {
        return idexRepository.findByPageId(pageId);
    }

    @Override
    public void deleteAllIndexing(List<Index> indexingList) {
       idexRepository.deleteAll(indexingList);
    }

    @Override
    public Index getIndex(int lemmaId, int pageId) {
        Index indexing = null;
        try{
            indexing = idexRepository.findByLemmaIdAndPageId(lemmaId, pageId);
        } catch (Exception e) {
            log.error("lemmaId: " + lemmaId + " + pageId: " + pageId + " not unique");
            e.printStackTrace();
        }
        return indexing;
    }

    @Override
    public void save(Index indexing) {
        idexRepository.save(indexing);
    }
}
