package com.service;

import com.model.Index;
import com.model.Lemma;

import java.util.List;

public interface LemmaService {
    List<Lemma> getLemma (String lemmaName);
    void save(Lemma lemma);
    long lemmaCount();
    long lemmaCount(int siteId);
    void deleteAllLemmas(List<Lemma> lemmaList);
    List<Lemma> findLemmasByIndex(List<Index> indexingList);
}
