package com;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "config")
public class SettingSearch {

    private String webinterfacelogin;
    private String webinterfacepassword;
    private String prefix;
    private String agent;
    private String webinterface;
    private List<HashMap<String, String>> site;

    public String getWebinterfacelogin() {
        return webinterfacelogin;
    }

    public void setWebinterfacelogin(String webinterfacelogin) {
        this.webinterfacelogin = webinterfacelogin;
    }

    public String getWebinterfacepassword() {
        return webinterfacepassword;
    }

    public void setWebinterfacepassword(String webinterfacepassword) {
        this.webinterfacepassword = webinterfacepassword;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getWebinterface() {
        return webinterface;
    }

    public void setWebinterface(String webinterface) {
        this.webinterface = webinterface;
    }

    public List<HashMap<String, String>> getSite() {
        return site;
    }

    public void setSite(List<HashMap<String, String>> site) {
        this.site = site;
    }
}
