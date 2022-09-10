package com;


import com.model.Site;
import com.model.Status;
import com.service.LemmaService;
import com.service.PageService;
import com.service.SiteService;
import com.service.StatisticService;
import com.service.responcse.StatisticResponseService;
import com.service.responseEntity.Detailed;
import com.service.responseEntity.Statistics;
import com.service.responseEntity.Total;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Statistic implements StatisticService {

    private static final Log log = LogFactory.getLog(Statistic.class);

    private final SiteService siteRepositoryService;
    private final LemmaService lemmaRepositoryService;
    private final PageService pageRepositoryService;

    public Statistic(SiteService siteRepositoryService,
                     LemmaService lemmaRepositoryService,
                     PageService pageRepositoryService) {
        this.siteRepositoryService = siteRepositoryService;
        this.lemmaRepositoryService = lemmaRepositoryService;
        this.pageRepositoryService = pageRepositoryService;
    }

    public StatisticResponseService getStatistic(){
        Total total = getTotal();
        List<Site> siteList = siteRepositoryService.getAllSites();
        Detailed[] detaileds = new Detailed[siteList.size()];
        for (int i = 0; i < siteList.size(); i++) {
            detaileds[i] = getDetailed(siteList.get(i));
        }
        log.info("Получение статистики.");
        return new StatisticResponseService(true, new Statistics(total, detaileds));
    }

    private Total getTotal(){
        long sites = siteRepositoryService.siteCount();
        long lemmas = lemmaRepositoryService.lemmaCount();
        long pages = pageRepositoryService.pageCount();
        boolean isIndexing = isSitesIndexing();
        return new Total(sites, pages, lemmas, isIndexing);

    }

    private Detailed getDetailed(Site site){
        String url = site.getUrl();
        String name = site.getName();
        Status status = site.getStatus();
        long statusTime = site.getStatusTime().getTime();
        String error = site.getLastError();
        long pages = pageRepositoryService.pageCount(site.getId());
        long lemmas = lemmaRepositoryService.lemmaCount(site.getId());
        return new Detailed(url, name, status, statusTime, error, pages, lemmas);
    }

    private boolean isSitesIndexing(){
        boolean is = true;
        for(Site s : siteRepositoryService.getAllSites()){
            if(!s.getStatus().equals(Status.INDEXED)){
                is = false;
                break;
            }
        }
    return is;
    }
}
