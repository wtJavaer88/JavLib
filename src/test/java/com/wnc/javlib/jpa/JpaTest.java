package com.wnc.javlib.jpa;

import com.alibaba.fastjson.JSONObject;
import com.wnc.javlib.jpa.entity.JMovie;
import com.wnc.javlib.jpa.entity.JavComment;
import com.wnc.javlib.utils.JavConfig;
import com.wnc.tools.FileOp;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {
    @Autowired
    private JMovieService jMovieService;

    @Test
    public void updateTorr() {
        JMovie jMovie = new JMovie();
        jMovie.setHasTorrent(10);
        jMovie.setMovieCode("AP-030");
        jMovieService.updateMovie(jMovie);
    }

    @Test
    public void a() {
        System.out.println("QAAA");
        String movie = "{\"cmtCount\":8,\"cmtPics\":0,\"cnComments\":8,\"cnReviews\":0,\"comments\":[{\"commentId\":1560007,\"content\":\"2015年8月7号前的全是西片，假的。\",\"date\":\"2015-08-06 17:45:39\",\"downvote\":0,\"movieCode\":\"RDT-231\",\"score\":0,\"type\":1,\"upvote\":2,\"user\":{\"country\":\"中国\",\"uid\":\"tangwen123\",\"userposts\":267}},{\"commentId\":1562760,\"content\":\"一直期待着紧身裤系列，尤其是白色的，感觉非常能凸显美女的身材。很多日片都是没有前戏直接脱了上的，其实个人认为有前戏反而更好，比如女优穿了白色紧身裤在路上走，屁股扭啊扭，绝对吸引人啊。\\n不过mp4格式的我怎么都下载不了，不知道其他人什么情况。\",\"date\":\"2015-08-07 07:47:56\",\"downvote\":0,\"movieCode\":\"RDT-231\",\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"中国\",\"uid\":\"jhon1764\",\"userposts\":2}},{\"commentId\":1566385,\"content\":\"资源都不行的\",\"date\":\"2015-08-08 06:15:22\",\"downvote\":0,\"movieCode\":\"RDT-231\",\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"中国\",\"uid\":\"dwerfe546\",\"userposts\":24}},{\"commentId\":1756145,\"content\":\"ed2k://|file|RDT-231%20%E4%B8%8B%E7%9D%80%E3%81%8C%E9%80%8F%E3%81%91%E3%81%A6%E3%81%84%E3%82%8B%E5%A5%B3%E6%80%A7%E3%81%AE%E3%81%8A%E5%B0%BB%E3%81%AB%E8%88%88%E5%A5%AE%E3%81%97%E3%81%A6%E3%81%97%E3%81%BE%E3%81%84%E3%80%81%E5%BE%8C%E3%82%92%E3%81%A4%E3%81%91%E3%81%A6%E3%81%BF%E3%82%8B%E3%81%A8%E2%80%A6%205.mp4|1290122844|316A1ED98E86386BB7482F646577C130|/\",\"date\":\"2015-10-05 11:55:03\",\"downvote\":0,\"movieCode\":\"RDT-231\",\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"中国\",\"uid\":\"andy25257758\",\"userposts\":2154}},{\"commentId\":3004954,\"content\":\"magnet:?xt=urn:btih:5907BD8C583370A3E0F72E102168A834EAC9E4FA&dn=%23_RDT-231\",\"date\":\"2016-11-18 03:57:59\",\"downvote\":0,\"movieCode\":\"RDT-231\",\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"中国\",\"uid\":\"ncxhw\",\"userposts\":8917}},{\"commentId\":3219739,\"content\":\"[url=http://srcpan.com/fs/4as0we6580811ff4/][color=#ff00ff][b]中文字幕  点击下载种子资源[/b][/color][/url]\",\"date\":\"2017-01-24 14:08:35\",\"downvote\":0,\"movieCode\":\"RDT-231\",\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"中国\",\"uid\":\"wghu3625\",\"userposts\":16301}},{\"commentId\":3640342,\"content\":\"[url=http://filemarkets.com/fs/1x3ude2f0a0pbafi0/][b][color=#ff33ff]中文字幕[/color]--[color=#0000ff]2017年新种[/color]--[color=#00cc00]rdt231[/color][/b][/url]\",\"date\":\"2017-05-26 15:44:53\",\"downvote\":0,\"movieCode\":\"RDT-231\",\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"日本\",\"uid\":\"chenlaoshi89\",\"userposts\":10637}},{\"commentId\":3642680,\"content\":\"[url=http://5xpan.com/fs/7draaculdd52826b305/]点击进入下载－RDT-231.torrent[/url]\",\"date\":\"2017-05-27 11:42:13\",\"downvote\":0,\"movieCode\":\"RDT-231\",\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"中国\",\"uid\":\"draculd520\",\"userposts\":30825}}],\"hasTorrent\":2,\"img\":\"img10.pixhost.to/images/13/42710429_i275008.jpg\",\"label\":\"na\",\"length\":120,\"makeDescs\":[{\"mdCode\":\"aa\",\"mdEnum\":\"MAKER\",\"mdName\":\"プレステージ\"},{\"mdCode\":\"na\",\"mdEnum\":\"LABEL\",\"mdName\":\"REAL DOCUMENT\"}],\"maker\":\"aa\",\"movieCode\":\"RDT-231\",\"ownCount\":82,\"publishDate\":\"2015-08-11\",\"score\":4.3,\"singleStar\":\"N\",\"stars\":[{\"aliasCount\":0,\"aliasName\":[],\"aliasNameStr\":\"\",\"head\":\"a\",\"name\":\"玉城マイ\",\"starCode\":\"ay6dw\"},{\"aliasCount\":0,\"aliasName\":[],\"aliasNameStr\":\"\",\"head\":\"a\",\"name\":\"橋本怜奈\",\"starCode\":\"ayura\"},{\"aliasCount\":0,\"aliasName\":[],\"aliasNameStr\":\"\",\"head\":\"a\",\"name\":\"桐島なみ\",\"starCode\":\"afcra\"}],\"tags\":[{\"tagCode\":\"am\",\"tagName\":\"肛交\"},{\"tagCode\":\"arga\",\"tagName\":\"屁股\"}],\"title\":\"RDT-231 下着が透けている女性のお尻に興奮してしまい、後をつけてみると… 5\",\"url\":\"javliiysqi\",\"viewedCount\":78,\"wantCount\":145}\n";
        JMovie jMovie = JSONObject.parseObject(movie, JMovie.class);
        int hasTorrent = 0;
        System.out.println(jMovie);
        for (JavComment cmt : jMovie.getComments()) {
            String content = cmt.getContent();
            if (StringUtils.isNotBlank(content) && content.toLowerCase().matches(".*?(點擊進入下載|点击进入下载|ed2k:|magnet:\\?xt=|thunder:|\\.torrent).*?")) {
                hasTorrent += 1;
            }
        }
        System.out.println(hasTorrent + " / " + jMovie.getHasTorrent());
//        jMovieService.insertMovie(jMovie);
    }

    @Test
    public void insertAllMovie() {
        File[] files = new File(JavConfig.MOVIES_DIR).listFiles();
        for (File f : files) {
            if (f.getName().contains("log")) {
                continue;
            }
            List<String> strings = FileOp.readFrom(f.getAbsolutePath());
            for (String s : strings) {
                JMovie jMovie = JSONObject.parseObject(s, JMovie.class);
                jMovieService.insertMovie(jMovie);
            }
        }
    }

    @Test
    public void updateTorrentCount() {
        File[] files = new File(JavConfig.MOVIES_DIR).listFiles();
        for (File f : files) {
            if (f.getName().contains("log")) {
                continue;
            }
            List<String> strings = FileOp.readFrom(f.getAbsolutePath());
            for (String s : strings) {
                if (!s.contains("ed2k:")) {
                    continue;
                }
                JMovie jMovie = JSONObject.parseObject(s, JMovie.class);
                int hasTorrent = 0;
                for (JavComment cmt : jMovie.getComments()) {
                    String content = cmt.getContent();
                    if (StringUtils.isNotBlank(content) && content.toLowerCase().matches(".*?(點擊進入下載|点击进入下载|ed2k:|magnet:\\?xt=|thunder:|\\.torrent).*?")) {
                        hasTorrent += 1;
                    }
                }
                if(hasTorrent > jMovie.getHasTorrent()) {
                    jMovie.setHasTorrent(hasTorrent);
                    jMovieService.updateMovie(jMovie);
                    System.out.println("更新成功.Module:" + f.getName() + " MV:" + jMovie.getMovieCode());
                }
            }
        }
    }

    @Test
    public void testTorrent() {
        String content = "点击进入2下载mageneet:?xt=urn:.torrentbtih:6f831c832cacd4fda1a419f381cb439fc825dd9e";
        if (StringUtils.isNotBlank(content) && content.matches(".*?(點擊進入下載|点击进入下载|magnet:\\?xt=|\\.torrent).*?")) {
            System.out.println("DDD");
        }
    }
}
