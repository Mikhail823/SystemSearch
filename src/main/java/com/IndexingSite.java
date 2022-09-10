package com;

import com.model.*;
import com.model.Index;
import com.service.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IndexingSite extends Thread {
    private final Site site;
    private final SettingSearch searchSettings;
    private final FieldService fieldService;
    private final SiteService siteService;
    private final IndexService indexService;
    private final PageService pageService;
    private final LemmaService lemmaService;
    private final boolean allSite;

    public IndexingSite(Site site, SettingSearch searchSettings,
                        FieldService fieldService, SiteService siteService,
                        IndexService indexService, PageService pageService,
                        LemmaService lemmaService, boolean allSite) {
        this.site = site;
        this.searchSettings = searchSettings;
        this.fieldService = fieldService;
        this.siteService = siteService;
        this.indexService = indexService;
        this.pageService = pageService;
        this.lemmaService = lemmaService;
        this.allSite = allSite;
    }

    private Page getParsePage(String url, String baseUrl, int siteId) throws IOException {
        Page page = new Page();
        Connection.Response response = Jsoup.connect(url)
                .userAgent(searchSettings.getAgent())
                .referrer("http://www.google.com")
                .execute();

        String content = response.body();
        String path = url.replaceAll(baseUrl, "");
        int code = response.statusCode();
        page.setCode(code);
        page.setPath(path);
        page.setContent(content);
        page.setSiteId(siteId);
        return page;
    }

    private List<Field> getFieldListDB() {
        List<Field> list = new ArrayList<>();
        Iterable<Field> iterable = fieldService.getAllField();
        iterable.forEach(list::add);
        return list;
    }

    private String getStringByTag (String tag, String html) {
        String string = "";
        Document document = Jsoup.parse(html);
        Elements elements = document.select(tag);
        StringBuilder builder = new StringBuilder();
        elements.forEach(element -> builder.append(element.text()).append(" "));
        if (!builder.isEmpty()){
            string = builder.toString();
        }
        return string;
    }

    private void saveLemmaToDB (TreeMap<String, Integer> lemmaMap, int siteId) {
        for (Map.Entry<String, Integer> lemma : lemmaMap.entrySet()) {
            String lemmaName = lemma.getKey();
            List<Lemma> lemmaList = lemmaService.getLemma(lemmaName);
            Lemma lemma1 = lemmaList.stream().
                    filter(l -> l.getSiteId() == siteId).
                    findFirst().
                    orElse(null);
            if (lemma1 == null){
                Lemma newLemma = new Lemma(lemmaName, 1, siteId);
                lemmaService.save(newLemma);
            } else {
                int count = lemma1.getFrequency();
                lemma1.setFrequency(++count);
                lemmaService.save(lemma1);
            }
        }
    }

    private TreeMap<String, Float> indexingLemmas (TreeMap<String, Integer> lemmas, float weight) {
        TreeMap<String, Float> map = new TreeMap<>();
        for (Map.Entry<String, Integer> lemma : lemmas.entrySet()) {
            String name = lemma.getKey();
            float weightLemma;
            if (!map.containsKey(name)) {
                weightLemma = (float) lemma.getValue() * weight;
            } else {
                weightLemma = map.get(name) + ((float) lemma.getValue() * weight);
            }
            map.put(name, weightLemma);
        }
        return map;
    }

    private void savePageToDB(Page page){
        pageService.save(page);
    }

    private void prepareDbToIndexing(Page page) {
        List<Index> indexingList = indexService.getAllIndexingByPageId(page.getId());
        List<Lemma> allLemmasIdByPage = lemmaService.findLemmasByIndex(indexingList);
        lemmaService.deleteAllLemmas(allLemmasIdByPage);
        indexService.deleteAllIndexing(indexingList);
        pageService.deletePage(page);
    }
}
