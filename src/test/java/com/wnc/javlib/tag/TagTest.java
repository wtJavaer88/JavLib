package com.wnc.javlib.tag;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crawl.spider.SpiderHttpClient;
import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.task.HrefDom;
import com.wnc.javlib.task.TagTask;
import com.wnc.javlib.utils.JavConfig;
import com.wnc.javlib.utils.JavSpiderUtils;
import com.wnc.javlib.utils.ProxyUtil;
import com.wnc.string.PatternUtil;
import com.wnc.tools.FileOp;
import common.spider.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class TagTest {

    @Test
    public void all() throws IOException {
        String webPage = HttpClientUtil.getWebPage("http://www.v22q.com/cn/genres.php");
        String selector = "#rightcolumn > .textbox";
        Document parse = Jsoup.parse(webPage);
        Elements select = parse.select(selector);
        for (Element element : select) {
            String text = element.select("> div.boxtitle").text();
            System.err.println(text);
            Elements select2 = element.select(".genreitem > a");
            for (Element element2 : select2) {
                String name = element2.text();
                String url = element2.attr("href");
                // System.out.println("\t" + name + " / " + url);
                String url2 = JavConfig.DOMAIN + "/" + url + "&mode=2";
                String webPage2 = HttpClientUtil.getWebPage(url2);
                Document parse2 = Jsoup.parse(webPage2);
                int pages = JavSpiderUtils.getMaxPage(parse2);
                System.out.println(name + " PAGES:" + pages);

            }
        }
    }

    @Test
    public void allSave() throws IOException {
        JSONObject result = new JSONObject();

        String webPage = HttpClientUtil.getWebPage("http://www.v22q.com/cn/genres.php");
        String selector = "#rightcolumn > .textbox";
        Document parse = Jsoup.parse(webPage);
        Elements select = parse.select(selector);
        for (Element element : select) {
            String type = element.select("> div.boxtitle").text();
            JSONArray typeArr = new JSONArray();
            System.err.println(type);
            Elements select2 = element.select(".genreitem > a");
            for (Element element2 : select2) {
                String name = element2.text();
                String url = element2.attr("href");
                // System.out.println("\t" + name + " / " + url);
                typeArr.add(new HrefDom(element2));
            }
            result.put(type, typeArr);
        }
        System.out.println(result);
        BasicFileUtil.writeFileString(JavConfig.TAGS_FILE, JSONObject.toJSONString(result, true) + "\r\n", null, false);
    }

    @Test
    public void testTagTask() throws IOException, InterruptedException {
        new ProxyUtil().initProxyPool();
        String name = "VHS";
        String url = "vl_genre.php?g=aycq";
        String code = PatternUtil.getLastPatternGroup(url, "g=(.*?)$");
        new TagTask(name, code, 1).run();
        Thread.sleep(100000000000000000L);
    }

    @Test
    public void testAllTagTask() throws IOException, InterruptedException {
        new ProxyUtil().initProxyPool();
        String join = StringUtils.join(FileOp.readFrom(JavConfig.TAGS_FILE), "");
        JSONObject parseObject = JSONObject.parseObject(join);
        for (Map.Entry<String, Object> entry : parseObject.entrySet()) {
            JSONArray list = parseObject.getJSONArray(entry.getKey());
            System.out.println(list);
            for (int i = 0; i < list.size(); i++) {
                HrefDom hrefDom = list.getObject(i, HrefDom.class);
                String code = PatternUtil.getLastPatternGroup(hrefDom.getUrl(), "g=(.*?)$");
                SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new TagTask(hrefDom.getName(), code, 1));
            }
        }
        Thread.sleep(100000000000000000L);
    }
}
