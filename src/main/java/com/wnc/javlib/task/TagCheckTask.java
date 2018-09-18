package com.wnc.javlib.task;

import com.alibaba.fastjson.JSONObject;
import com.crawl.spider.SpiderHttpClient;
import com.crawl.spider.entity.Page;
import com.crawl.spider.task.AbstractPageTask;
import com.crawl.spider.task.retry.RetryConstruct;
import com.crawl.spider.task.retry.RetryConstructParam;
import com.wnc.basic.BasicDateUtil;
import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.jpa.JMovieService;
import com.wnc.javlib.jpa.entity.JMovie;
import com.wnc.javlib.utils.JavConfig;
import com.wnc.javlib.utils.JavSpiderUtils;
import com.wnc.string.PatternUtil;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TagCheckTask extends AbstractPageTask {
    public static String LAST_SPY_DAY = "2018-09-06";
    private JMovieService jMovieService = SpringContextUtils.getJMovieService();
    private String tagName;
    private String tagCode;
    private int curPage;

    @RetryConstruct
    public TagCheckTask(@RetryConstructParam("tagName") String tagName, @RetryConstructParam("tagCode")String tagCode,
                        @RetryConstructParam("curPage") int curPage) {
        this.tagName = tagName;
        this.tagCode = tagCode;
        this.curPage = curPage;
        this.url = String.format(JavConfig.TAG_CHECK_MOVIE_SFT, tagCode, curPage);
    }

    @Override
    protected void handle(Page page) throws Exception {
        Document doc = getDoc(page);
        if (doc.select("#rightcolumn > div.boxtitle").size() == 0) {
            retryMonitor(tagName + "没有获取到正确页面!" + this.url);
            ignoreComplete = true;
            return;
        }
        int maxPage = JavSpiderUtils.getMaxPage(doc);
        if (maxPage == 0) {
            // 证明可能出现了异常情况, 页面没有正常返回
            retryMonitor(tagName + "总页码为0,当前页" + curPage + ", 是否异常?" + this.url);
            ignoreComplete = true;
            return;
        }
        // 最后一页时, 此时"最大页"就是前一页, curPage比maxPage最多大1
        if (curPage - maxPage > 1) {
            retryMonitor(tagName + "总页码为" + maxPage + ",当前页" + curPage + ",差值超过1,是否异常?" + this.url);
            ignoreComplete = true;
            return;
        }

        Elements select = doc.select("#rightcolumn > table.videotextlist > tbody > tr:gt(0)");
        HrefDom hrefDom = null;
        String mvDate = "2000-01-01";
        for (Element element : select) {
            mvDate = element.select("td:eq(1)").text();
            hrefDom = new HrefDom(element.select(".video > a").first());
            writeMovie(hrefDom);
        }
        // 是否到上次爬取日期
        if(LAST_SPY_DAY.compareTo(mvDate) > 0){
            log(tagName + "到达上次结束页:" + curPage);
        }else {
            if (canContinue(maxPage)) {
                SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new TagCheckTask(tagName, tagCode, curPage + 1));
                ignoreComplete = true;
            }
        }

        log(tagName + "完成第" + curPage + "页Check任务");
    }

    private boolean canContinue(int maxPage) {
        return maxPage > 1 && curPage < maxPage;
    }

    @Override
    protected void complete(int type, String msg) {
        super.complete(type, msg);

        if (type == COMPLETE_STATUS_SUCCESS) {
            log(tagName + "完成所有Check任务");
        } else {
            BasicFileUtil.writeFileString(JavConfig.TAGS_CHECK_ERR_LOG, BasicDateUtil.getCurrentDateTimeString() + " " + tagName + msg + "\r\n", null, true);
        }
    }

    private void log(String msg) {
        BasicFileUtil.writeFileString(JavConfig.TAGS_CHECK_LOG, BasicDateUtil.getCurrentDateTimeString() + " " + msg + "\r\n", null, true);
    }

    private boolean writeMovie(HrefDom hrefDom) {
        JMovie jMovie = new JMovie();
        String mCode = PatternUtil.getFirstPatternGroup(hrefDom.getName(), "(.*?) ");
        if(StringUtils.isBlank(mCode)){
            return true;
        }
        jMovie.setMovieCode(mCode);
        jMovie.setTitle(hrefDom.getName());
        jMovie.setUrl(PatternUtil.getFirstPatternGroup(hrefDom.getUrl(), "v=(.*+)"));
        System.out.println(hrefDom.getName());
        boolean b = jMovieService.insertMonoMovie(jMovie);
        if (b) {
            BasicFileUtil.writeFileString(JavConfig.TAG_CHECK_MOVIE_DIR + tagName + ".txt",
                    BasicDateUtil.getCurrentDateTimeString() + " " + JSONObject.toJSONString(hrefDom) + "\r\n", null, true);
            SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new MovieDetailTask(jMovie.getUrl(), tagName));
            SpiderHttpClient.parseCount.getAndIncrement();
        }
        return b;
    }

}
