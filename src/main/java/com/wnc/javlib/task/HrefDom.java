package com.wnc.javlib.task;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;

public class HrefDom {
    private String url;
    private String name;

    public HrefDom(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public HrefDom(Element e) {
        this.name = e.text();
        this.url = e.absUrl("href");
        if (StringUtils.isBlank(url)) {
            this.url = e.attr("href");
        }
        if (StringUtils.isBlank(name)) {
            this.name = e.text();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
