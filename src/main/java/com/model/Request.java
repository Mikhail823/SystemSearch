package com.model;


import com.morphology.MorphologyAnalyzer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;


public class Request {
    public static Log log = LogFactory.getLog(Request.class);
    private String req;
    private List<String> reqLemmas;

    public List<String> getReqLemmas() {
        return reqLemmas;
    }

    public String getReq() {
        return req;
    }

    public Request(String req){
        this.req = req;
        reqLemmas = new ArrayList<>();
        try {
            MorphologyAnalyzer analyzer = new MorphologyAnalyzer();
            reqLemmas.addAll(analyzer.getLemmas(req));
        }catch (Exception e) {
            log.error("Ошибка морфологочиского анализа");
        }
    }
}
