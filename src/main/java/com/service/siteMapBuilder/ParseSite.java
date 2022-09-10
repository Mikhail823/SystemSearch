package com.service.siteMapBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class ParseSite extends RecursiveTask<String> {
    private String URL;
    private Set<String> listUrls = Collections.synchronizedSet(new HashSet<>());
    private Log log = LogFactory.getLog(ParseSite.class);



    public ParseSite(String URL) {
        this.URL = URL;
    }
    public ParseSite(){}
    @Override
    protected String compute() {

            StringBuilder result = new StringBuilder();
            result.append(URL);
        try {
            Thread.sleep(200);
            Document doc = getByConnectSite(URL);
            Elements links = doc.getElementsByTag("a");
            List<ParseSite> tasks = new ArrayList<>();

            links.forEach(e -> {
                String link = e.attr("abs:href");

                if (link.startsWith(e.baseUri())
                        && !link.equals(e.baseUri())
                        && !link.contains("#")
                        && !link.contains("pdf")
                        && !listUrls.contains(link)){
                            listUrls.add(link.replaceAll(e.baseUri(), ""));
                            ParseSite parse = new ParseSite(link);
                            parse.fork();
                            tasks.add(parse);
                }
            });

            for (ParseSite task : tasks){
               String sysn = task.join();
               if(!sysn.equals("")){
                   result.append("\n");
                   result.append(sysn);
               }
            }
        } catch (InterruptedException | IOException e) {
            log.error("Ошибка парсинга сайта " + e);
        }

        return result.toString();
    }

    protected Document getByConnectSite(String url) throws InterruptedException, IOException {
        Thread.sleep(500);
        log.info("Идет подключение к сайту......" + url);
        return Jsoup.connect(url)
                .maxBodySize(0)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .get();
    }

    public Set<String> getListUrls() {
        return listUrls;
    }
}
