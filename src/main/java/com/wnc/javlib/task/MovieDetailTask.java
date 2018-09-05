package com.wnc.javlib.task;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.crawl.spider.SpiderHttpClient;
import com.crawl.spider.entity.Page;
import com.crawl.spider.task.AbstractPageTask;
import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.jpa.JMovieService;
import com.wnc.javlib.jpa.entity.JMovie;
import com.wnc.javlib.service.MovieDetailImpl;
import com.wnc.javlib.utils.JavConfig;
import org.jsoup.nodes.Document;

import java.util.concurrent.ConcurrentHashMap;

public class MovieDetailTask extends AbstractPageTask {
    static {
        retryMap.put(MovieDetailTask.class, new ConcurrentHashMap<String, Integer>());
    }

    private final String urlCode;
    private final String moduleName;
    private boolean ignoreComplete = false;
    private JMovieService jMovieService = SpringContextUtils.getJMovieService();


    public MovieDetailTask(String urlCode, String moduleName) {
        super(String.format(JavConfig.MV_DETAIL_SFT, urlCode), true);
        this.urlCode = urlCode;
        this.moduleName = moduleName;
        MAX_RETRY_TIMES = 20;
    }

    @Override
    public void run() {
        super.run();
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
                saveFile, JSONObject.toJSONString(jMovie, SerializerFeature.DisableCircularReferenceDetect) + "\r\n",
                null, true);

        SpiderHttpClient.parseCount.getAndIncrement();
    }

    @Override
    public void retry() {
        SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new MovieDetailTask(urlCode, moduleName));
    }

    @Override
    protected void complete(int type, String msg) {
        if (ignoreComplete) {
            return;
        }
        super.complete(type, msg);
        SpiderHttpClient.parseCount.getAndIncrement();
        if (type == COMPLETE_STATUS_SUCCESS) {
            BasicFileUtil.writeFileString(JavConfig.MOVIES_LOG, urlCode + "成功结束!\r\n", null,
                    true);
        } else {
            BasicFileUtil.writeFileString(JavConfig.MOVIES_ERR_LOG, urlCode + ":" + msg + "\r\n", null, true);
        }
    }

    @Override
    protected void errLog404(Page page) {
        super.errLog404(page);
        if (!page.getHtml().contains("http://www.javlibrary.com")) {
            ignoreComplete = true;
            retryMonitor(url + "重试404");
        }
    }

    @Override
    protected void errLogExp(Exception ex) {
        super.errLogExp(ex);
        System.err.println(url);
        ex.printStackTrace();
    }
}
