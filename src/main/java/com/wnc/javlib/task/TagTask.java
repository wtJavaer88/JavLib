package com.wnc.javlib.task;

import com.alibaba.fastjson.JSONObject;
import com.crawl.spider.SpiderHttpClient;
import com.crawl.spider.entity.Page;
import com.crawl.spider.task.AbstractPageTask;
import com.crawl.spider.task.retry.RetryConstruct;
import com.crawl.spider.task.retry.RetryConstructParam;
import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.utils.JavConfig;
import com.wnc.javlib.utils.JavSpiderUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TagTask extends AbstractPageTask {
    private String tagName;
    private String tagCode;
    private int curPage;

    @RetryConstruct
    public TagTask(@RetryConstructParam("tagName") String tagName, @RetryConstructParam("tagCode")String tagCode,
                   @RetryConstructParam("curPage") int curPage) {
        this.tagName = tagName;
        this.tagCode = tagCode;
        this.curPage = curPage;
        this.url = String.format(JavConfig.TAG_MOVIE_SFT, tagCode, curPage);
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

        Elements select = doc.select(".videos > .video > a");
        for (Element element : select) {
            HrefDom hrefDom = new HrefDom(element);
            System.out.println(hrefDom.getName());
            writeMovie(hrefDom);
            SpiderHttpClient.parseCount.getAndIncrement();
        }

        log(tagName + "完成第" + curPage + "页任务");
        if (maxPage > 1 && curPage < maxPage) {
            SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new TagTask(tagName, tagCode, curPage + 1));
            ignoreComplete = true;
        }
    }

    @Override
    protected void complete(int type, String msg) {
        super.complete(type, msg);

        if (type == COMPLETE_STATUS_SUCCESS) {
            log(tagName + "完成所有任务");
        } else {
            BasicFileUtil.writeFileString(JavConfig.TAGS_ERR_LOG, tagName + msg + "\r\n", null, true);
        }
    }

    private void log(String msg) {
        BasicFileUtil.writeFileString(JavConfig.TAGS_LOG, msg + "\r\n", null, true);
    }

    private void writeMovie(HrefDom hrefDom) {
        BasicFileUtil.writeFileString(JavConfig.TAG_MOVIE_DIR + tagName + ".txt",
                JSONObject.toJSONString(hrefDom) + "\r\n", null, true);
    }
}
