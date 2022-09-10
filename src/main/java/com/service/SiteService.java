package com.service;

import com.model.Site;

import java.util.List;

public interface SiteService {
    Site getSite (String url);
    Site getSite (int siteId);
    void save(Site site);
    long siteCount();
    List<Site> getAllSites();
}
