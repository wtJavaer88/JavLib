package com.wnc.javlib.tag;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crawl.spider.SpiderHttpClient;
import com.wnc.javlib.task.HrefDom;
import com.wnc.javlib.task.MovieDetailTask;
import com.wnc.javlib.task.TagCheckTask;
import com.wnc.javlib.utils.JavConfig;
import com.wnc.javlib.utils.ProxyUtil;
import com.wnc.string.PatternUtil;
import com.wnc.tools.FileOp;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagCheckTest {
    @Test
    public void testTagCheckTask() throws IOException, InterruptedException {
        new ProxyUtil().initProxyPool();
        String name = "单体作品";
        String url = "vl_genre.php?g=lq";
        String code = PatternUtil.getLastPatternGroup(url, "g=(.*?)$");
        new TagCheckTask(name, code, 1).run();
        Thread.sleep(100000000000000000L);
    }

    @Test
    public void testTagCheckTaskAll() throws IOException, InterruptedException {
        //上次爬取的日期, 数据库movie表看最近一个影片插入的时间
        TagCheckTask.LAST_SPY_DAY = "2018-09-12";
        new ProxyUtil().initProxyPool();
        String join = StringUtils.join(FileOp.readFrom(JavConfig.TAGS_FILE), "");
        JSONObject parseObject = JSONObject.parseObject(join);
        for (Map.Entry<String, Object> entry : parseObject.entrySet()) {
            JSONArray list = parseObject.getJSONArray(entry.getKey());
            System.out.println(list);
            for (int i = 0; i < list.size(); i++) {
                HrefDom hrefDom = list.getObject(i, HrefDom.class);
                String code = PatternUtil.getLastPatternGroup(hrefDom.getUrl(), "g=(.*?)$");
                System.out.println(code);
                SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new TagCheckTask(hrefDom.getName(), code, 1));
            }
        }
        Thread.sleep(100000000000000000L);
    }

    @Test
    public void testOne() throws IOException, InterruptedException {
//        new ProxyUtil().initProxyPool();
        new MovieDetailTask("javli7ijsa", "偷窥").run();
    }
}
