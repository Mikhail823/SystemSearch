package com.service.siteMapBuilder;

import com.service.siteMapBuilder.ParseSite;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class SiteMapBuilder {
    private final String url;
    private List<String> listSite;

    public SiteMapBuilder(String url) {
        this.url = url;
    }

    public void builtSiteMap() {
        int coreCount = Runtime.getRuntime().availableProcessors();
        String text = new ForkJoinPool(coreCount).invoke(new ParseSite(url));
        listSite = stringToList(text);
    }

    private List<String> stringToList (String text) {
        return Arrays.stream(text.split("\n")).collect(Collectors.toList());
    }

    public List<String> getListSite(){
        return listSite;
    }
}
