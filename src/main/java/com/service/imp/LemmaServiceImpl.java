package com.service.imp;

import com.model.Index;
import com.model.Lemma;
import com.repository.LemmaRepository;
import com.service.LemmaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LemmaServiceImpl implements LemmaService {

    private final LemmaRepository lemmaRepository;

    public LemmaServiceImpl(LemmaRepository lemmaRepository) {
        this.lemmaRepository = lemmaRepository;
    }

    @Override
    public List<Lemma> getLemma(String lemmaName) {
        List<Lemma> lemmas = null;
        try{
            lemmas = lemmaRepository.findByLemma(lemmaName);
        } catch (Exception e) {
            System.out.println(lemmaName);
            e.printStackTrace();
        }
        return lemmas;
    }

    @Override
    public void save(Lemma lemma) {
        lemmaRepository.save(lemma);
    }

    @Override
    public long lemmaCount() {
        return lemmaRepository.count();
    }

    @Override
    public long lemmaCount(int siteId) {
        return lemmaRepository.count(siteId);
    }

    @Override
    public void deleteAllLemmas(List<Lemma> lemmaList) {
        lemmaRepository.deleteAll(lemmaList);
    }

    @Override
    public List<Lemma> findLemmasByIndex(List<Index> indexingList) {
        int[] lemmaIdList = new int[indexingList.size()];
        for (int i = 0; i < indexingList.size(); i++) {
            lemmaIdList[i] = indexingList.get(i).getLemmaId();
        }
        return lemmaRepository.findById(lemmaIdList);
    }
}
