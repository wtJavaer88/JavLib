package com.wnc.javlib.task;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.crawl.spider.SpiderHttpClient;
import com.crawl.spider.entity.Page;
import com.crawl.spider.task.AbstractPageTask;
import com.crawl.spider.task.retry.RetryConstruct;
import com.crawl.spider.task.retry.RetryConstructParam;
import com.wnc.basic.BasicDateUtil;
import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.jpa.JMovieService;
import com.wnc.javlib.jpa.entity.JMovie;
import com.wnc.javlib.service.MovieDetailImpl;
import com.wnc.javlib.utils.JavConfig;
import org.jsoup.nodes.Document;

public class MovieDetailTask extends AbstractPageTask {
    private final String urlCode;
    private final String moduleName;
    private JMovieService jMovieService = SpringContextUtils.getJMovieService();

    @RetryConstruct
    public MovieDetailTask(@RetryConstructParam("urlCode") String urlCode,@RetryConstructParam("moduleName") String moduleName) {
        super(String.format(JavConfig.MV_DETAIL_SFT, urlCode), true);
        this.urlCode = urlCode;
        this.moduleName = moduleName;
    }

    @Override
    public void handle(Page page) {
        Document doc = getDoc(page);
        if (!page.getHtml().contains("http://www.javlibrary.com")) {
            ignoreComplete = true;
            retryMonitor(url + "页面内容不符,重试");
            return;
        }
        JMovie jMovie = new MovieDetailImpl().getMovieDetail(doc);
        jMovie.setUrl(urlCode);
        jMovieService.insertMovie(jMovie);
        saveMovieDetailFile(jMovie);
    }

    private void saveMovieDetailFile(JMovie jMovie) {
        String saveFile = JavConfig.MOVIES_DIR + moduleName + ".txt";
        BasicFileUtil.writeFileString(
                saveFile, BasicDateUtil.getCurrentDateTimeString()+" "+JSONObject.toJSONString(jMovie, SerializerFeature.DisableCircularReferenceDetect) + "\r\n",
                null, true);

    }

    @Override
    protected void complete(int type, String msg) {
        super.complete(type, msg);
        SpiderHttpClient.parseCount.getAndIncrement();
        if (type == COMPLETE_STATUS_SUCCESS) {
            BasicFileUtil.writeFileString(JavConfig.MOVIES_LOG, BasicDateUtil.getCurrentDateTimeString()+" "+urlCode + "成功结束!\r\n", null,
                    true);
        } else {
            BasicFileUtil.writeFileString(JavConfig.MOVIES_ERR_LOG, BasicDateUtil.getCurrentDateTimeString()+" "+urlCode + ":" + msg + "\r\n", null, true);
        }
    }
}
