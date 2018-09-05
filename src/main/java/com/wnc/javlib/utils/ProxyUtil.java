package com.wnc.javlib.utils;

import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Proxy;
import com.wnc.basic.BasicNumberUtil;
import com.wnc.string.PatternUtil;
import common.spider.HttpClientUtil;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.List;

public class ProxyUtil {
    public void initProxyPool() throws IOException, InterruptedException {
        if (ProxyPool.proxyQueue.size() < 200) {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            if (ProxyPool.proxyQueue.size() < 200) {
                                getProxy();
                            }
                            Thread.sleep(60 * 1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    private void getProxy() throws IOException {
        HttpGet httpGet = new HttpGet("http://118.126.116.16:8080/sboot1/proxy/fatest200");
        String content = HttpClientUtil.getWebPage(httpGet, "UTF-8");
        List<String> get61Proxies = PatternUtil.getAllPatternGroup(content, "\\d+.\\d+.\\d+.\\d+:\\d+");
        for (String string : get61Proxies) {

            String ip = PatternUtil.getFirstPattern(string, "\\d+.\\d+.\\d+.\\d+");
            int port = BasicNumberUtil.getNumber(PatternUtil.getLastPattern(string, "\\d+"));
            System.out.println(ip + " / " + port);
            ProxyPool.proxyQueue.add(new Proxy(ip, port, 1000));
        }
        System.out.println(ProxyPool.proxyQueue.size());
    }

}
