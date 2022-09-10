package com.service.imp;

import com.model.Site;
import com.repository.SiteRepository;
import com.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;

    @Autowired
    public SiteServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Override
    public Site getSite(String url) {
        return siteRepository.findByUrl(url);
    }

    @Override
    public Site getSite(int siteId) {
        Optional<Site> optional = siteRepository.findById(siteId);
        Site site = null;
        if(optional.isPresent()){
            site = optional.get();
        }
        return site;
    }

    @Override
    public void save(Site site) {
        siteRepository.save(site);
    }

    @Override
    public long siteCount() {
        return siteRepository.count();
    }

    @Override
    public List<Site> getAllSites() {
        List<Site> sites = new ArrayList<>();
        Iterable<Site> iterable = siteRepository.findAll();
        iterable.forEach(sites::add);
        return sites;
    }
}
