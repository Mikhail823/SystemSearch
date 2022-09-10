package com;


import com.model.Field;
import com.model.Site;
import com.model.Status;
import com.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadIndexing {

    private final static Log log = LogFactory.getLog(ThreadIndexing.class);
    private final SettingSearch settingSearch;

    private final FieldService fieldService;
    private final SiteService siteService;
    private final IndexService indexService;
    private final PageService pageService;
    private final LemmaService lemmaService;

    public ThreadIndexing(SettingSearch settingSearch,
                          FieldService fieldService,
                          SiteService siteService,
                          IndexService indexService,
                          PageService pageService,
                          LemmaService lemmaService) {
        this.settingSearch = settingSearch;
        this.fieldService = fieldService;
        this.siteService = siteService;
        this.indexService = indexService;
        this.pageService = pageService;
        this.lemmaService = lemmaService;
    }
    int countProc = Runtime.getRuntime().availableProcessors();
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(countProc);

    public boolean allSiteIndexing() throws InterruptedException {
        fieldInit();
        boolean isIndexing;
        List<Site> siteList = getSiteListFromConfig();
        for (Site site : siteList) {
            isIndexing = startSiteIndexing(site);
            if (!isIndexing){
                stopSiteIndexing();
                return false;
            }
        }
        return true;
    }

    public String checkedSiteIndexing(String url) throws InterruptedException {
        List<Site> siteList = siteService.getAllSites();
        String baseUrl = "";
        for(Site site : siteList) {
            if(site.getStatus() != Status.INDEXED) {
                return "false";
            }
            if(url.contains(site.getUrl())){
                baseUrl = site.getUrl();
            }
        }
        if(baseUrl.isEmpty()){
            return "not found";
        } else {
            Site site = siteService.getSite(baseUrl);
            site.setUrl(url);
            IndexingSite indexing = new IndexingSite(
                    site,
                    settingSearch,
                    fieldService,
                    siteService,
                    indexService,
                    pageService,
                    lemmaService,
                    false);
            executor.execute(indexing);
            site.setUrl(baseUrl);
            siteService.save(site);
            return "true";
        }
    }


    private void fieldInit() {
        Field fieldTitle = new Field("title", "title", 1.0f);
        Field fieldBody = new Field("body", "body", 0.8f);
        if (fieldService.getFieldByName("title") == null) {
            fieldService.save(fieldTitle);
            fieldService.save(fieldBody);
        }
    }

    private boolean startSiteIndexing(Site site) throws InterruptedException {
        Site site1 = siteService.getSite(site.getUrl());
        if (site1 == null) {
            siteService.save(site);
            IndexingSite indexing = new IndexingSite(
                    siteService.getSite(site.getUrl()),
                    settingSearch,
                    fieldService,
                    siteService,
                    indexService,
                    pageService,
                    lemmaService,
                    true);
            executor.execute(indexing);
            return true;
        } else {
            if (!site1.getStatus().equals(Status.INDEXING)){
                IndexingSite indexing = new IndexingSite(
                        siteService.getSite(site.getUrl()),
                        settingSearch,
                        fieldService,
                        siteService,
                        indexService,
                        pageService,
                        lemmaService,
                        true);
                executor.execute(indexing);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean stopSiteIndexing(){
        boolean isThreadAlive = false;
        if(executor.getActiveCount() == 0){
            return false;
        }

        executor.shutdownNow();
        try {
            isThreadAlive = executor.awaitTermination(5,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.error("Ошибка закрытия потоков: " + e);
        }
        if (isThreadAlive){
            List<Site> siteList = siteService.getAllSites();
            for(Site site : siteList) {
                site.setStatus(Status.FAILED);
                siteService.save(site);
            }
        }
        return isThreadAlive;
    }

    private List<Site> getSiteListFromConfig() {
        List<Site> siteList = new ArrayList<>();
        List<HashMap<String, String>> sites = settingSearch.getSite();
        for (HashMap<String, String> map : sites) {
            String url = "";
            String name = "";
            for (Map.Entry<String, String> siteInfo : map.entrySet()) {
                if (siteInfo.getKey().equals("name")) {
                    name = siteInfo.getValue();
                }
                if (siteInfo.getKey().equals("url")) {
                    url = siteInfo.getValue();
                }
            }
            Site site = new Site();
            site.setUrl(url);
            site.setName(name);
            site.setStatus(Status.FAILED);
            siteList.add(site);
        }
        return siteList;
    }
}
