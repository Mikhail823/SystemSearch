package com.service;

import com.model.Page;

import java.util.Optional;

public interface PageService {
    Page getPage (String pagePath);
    void save(Page page);
    Optional<Page> findPageById(int id);
    Optional<Page> findPageByPageIdAndSiteId(int pageId, int siteId);
    long pageCount();
    long pageCount(int siteId);
    void deletePage(Page page);
}
