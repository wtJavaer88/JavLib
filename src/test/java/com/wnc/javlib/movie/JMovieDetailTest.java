package com.wnc.javlib.movie;

import com.crawl.spider.SpiderHttpClient;
import com.wnc.javlib.task.MovieDetailTask;
import com.wnc.javlib.utils.JavConfig;
import com.wnc.javlib.utils.ProxyUtil;
import com.wnc.string.PatternUtil;
import com.wnc.tools.FileOp;
import com.wnc.tools.SoupUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JMovieDetailTest {
    public Set<String> movieSeekSet = Collections.synchronizedSet(new HashSet<String>());

    @Test
    public void testOne() throws IOException, InterruptedException {
//        new ProxyUtil().initProxyPool();
        new MovieDetailTask("javli7iz2u","美少女").run();
    }

    @Test
    public void testAll() throws IOException, InterruptedException {
        List<String> strings1 = FileOp.readFrom(JavConfig.MOVIES_LOG);
        for(String s : strings1) {
            movieSeekSet.add(s.replace("成功结束!",""));
        }
        new ProxyUtil().initProxyPool();
        for(File moduleFile : new File(JavConfig.TAG_MOVIE_DIR).listFiles()) {
            if(moduleFile.getName().contains("log")){
                continue;
            }
            List<String> strings = FileOp.readFrom(moduleFile.getAbsolutePath());
            for(String s : strings) {
                String urlCode = PatternUtil.getLastPatternGroup(s, "\"url\":\"\\./\\?v=(.*?)\"");
                String moduleName = moduleFile.getName().replace(".txt", "");
                if(movieSeekSet.add(urlCode)) {
                    SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new MovieDetailTask(urlCode, moduleName));
                }
                else{
                    System.out.println("in task queue..."+urlCode);
                }
            }
        }
        Thread.sleep(100000000000000000L);
    }
}
