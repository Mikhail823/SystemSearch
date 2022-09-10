package com.service.imp;

import com.model.Page;
import com.repository.PageRepository;
import com.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    @Autowired
    public PageServiceImpl(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Override
    public Page getPage(String pagePath) {
        return pageRepository.findByPath(pagePath);
    }

    @Override
    public void save(Page page) {
        pageRepository.save(page);
    }

    @Override
    public Optional<Page> findPageById(int id) {
        return pageRepository.findById(id);
    }

    @Override
    public Optional<Page> findPageByPageIdAndSiteId(int pageId, int siteId) {
        return pageRepository.findByIdAndSiteId(pageId, siteId);
    }

    @Override
    public long pageCount() {
        return pageRepository.count();
    }

    @Override
    public long pageCount(int siteId) {
        return pageRepository.count(siteId);
    }

    @Override
    public void deletePage(Page page) {

    }
}
