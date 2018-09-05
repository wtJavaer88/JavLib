package com.wnc.javlib.spy;

import com.crawl.spider.SpiderHttpClient;
import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.task.MovieDetailTask;
import com.wnc.javlib.utils.JavConfig;
import com.wnc.string.PatternUtil;
import com.wnc.tools.FileOp;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JavSpy {
    public Set<String> movieSeekSet = Collections.synchronizedSet(new HashSet<String>());

    public void getAll() {
        List<String> strings1 = getStrings1();
        for (String s : strings1) {
            movieSeekSet.add(s.replace("成功结束!", ""));
        }
        File[] files = new File(JavConfig.TAG_MOVIE_DIR).listFiles();
        for (File moduleFile : files) {
            if (moduleFile.getName().contains("log")) {
                continue;
            }
            List<String> strings = FileOp.readFrom(moduleFile.getAbsolutePath());
            for (String s : strings) {
                String urlCode = PatternUtil.getLastPatternGroup(s, "\"url\":\"\\./\\?v=(.*?)\"");
                String moduleName = moduleFile.getName().replace(".txt", "");
                if (movieSeekSet.add(urlCode)) {
                    SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new MovieDetailTask(urlCode, moduleName));
                } else {
                    System.out.println("in task queue..." + urlCode);
                }
            }
        }
    }

    private List<String> getStrings1() {
        if(BasicFileUtil.isExistFile(JavConfig.MOVIES_LOG)) {
            return FileOp.readFrom(JavConfig.MOVIES_LOG);
        }
        return new ArrayList<String>();
    }
}
