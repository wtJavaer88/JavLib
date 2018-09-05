package com.wnc.javlib.jpa;

import com.alibaba.fastjson.JSONObject;
import com.wnc.javlib.jpa.entity.JMovie;
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
    public void a() {
        System.out.println("QAAA");
        String movie = "{\"cnComments\":10,\"cnReviews\":0,\"comments\":[{\"commentId\":5759043,\"content\":\"[url=http://yfdisk.com/fs/4claetesu6n79781/]點擊進入[/url][url=http://putpan.com/fs/4claetesu6n79781/]下載－MIAE-288.torrent[/url]\",\"date\":\"2018-08-11 03:39:06\",\"downvote\":0,\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"克罗埃西亚\",\"uid\":\"ncesvagel\",\"userposts\":1523}},{\"commentId\":5759045,\"content\":\"[url=http://yfdisk.com/fs/fa0l9mbl2s4t0rd3/]點擊進入[/url][url=http://putpan.com/fs/fa0l9mbl2s4t0rd3/]下載－MIAE-288.torrent[/url]\",\"date\":\"2018-08-11 03:39:07\",\"downvote\":0,\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"法国\",\"uid\":\"pghwrivre\",\"userposts\":4686}},{\"commentId\":5759046,\"content\":\"[url=http://putpan.com/fs/6oho4sh859ea92/]點擊進入下載－miae288.torrent[/url]\",\"date\":\"2018-08-11 03:39:08\",\"downvote\":0,\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"希腊\",\"uid\":\"vinfremle\",\"userposts\":2814}},{\"commentId\":5759047,\"content\":\"[url=http://putpan.com/fs/ak4a8d4i9mbae00/]點擊進入下載－miae288.torrent[/url]\",\"date\":\"2018-08-11 03:39:08\",\"downvote\":0,\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"圣马利诺\",\"uid\":\"whgmdswdb\",\"userposts\":4165}},{\"commentId\":5759048,\"content\":\"[url=http://putpan.com/fs/3rboeabm1s9ibnd0/]點擊進入下載－miae288.torrent[/url]\",\"date\":\"2018-08-11 03:39:08\",\"downvote\":0,\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"海地\",\"uid\":\"nqieufyrtmo\",\"userposts\":4288}},{\"commentId\":5759050,\"content\":\"[url=http://putpan.com/fs/bcndcmoaall006bd2/]點擊進入下載－miae288.torrent[/url]\",\"date\":\"2018-08-11 03:39:09\",\"downvote\":0,\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"萨尔瓦多\",\"uid\":\"enlsylmdag\",\"userposts\":3828}},{\"commentId\":5759066,\"content\":\"[color=lime]----------[url=http://putpan.com/fs/easbwe558f8ff941/][color=blue]点击进入下载资源[/color][/url]----------\",\"date\":\"2018-08-11 03:39:21\",\"downvote\":0,\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"中国\",\"uid\":\"xinypan988\",\"userposts\":25676}},{\"commentId\":5759069,\"content\":\"[url=http://putpan.com/fs/6skwcr9813f0cff2/]点击进入下载－miae288.torrent[/url]\",\"date\":\"2018-08-11 03:39:39\",\"downvote\":0,\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"中国\",\"uid\":\"dfgty22\",\"userposts\":29004}},{\"commentId\":5759071,\"content\":\"[url=http://putpan.com/fs/4y3ucdff3629f8830/]点击进入下载－miae288.torrent[/url]\",\"date\":\"2018-08-11 03:39:53\",\"downvote\":0,\"score\":0,\"type\":1,\"upvote\":0,\"user\":{\"country\":\"中国\",\"uid\":\"berhuie\",\"userposts\":26364}},{\"commentId\":5759074,\"content\":\"[url=http://putpan.com/fs/fw9s8e3a0r23e9790/]点击进入下载－miae288.torrent[/url]\",\"date\":\"2018-08-11 03:40:17\",\"downvote\":0,\"score\":0,\"type\":1,\"upvote\":1,\"user\":{\"country\":\"中国\",\"uid\":\"wdrtg5688\",\"userposts\":25848}}],\"director\":\"a5ja\",\"img\":\"img21.pixhost.to/images/418/77540250_i373797.jpg\",\"label\":\"kjha\",\"length\":190,\"makeDescs\":[{\"mdCode\":\"a5ja\",\"mdEnum\":\"DIRECTOR\",\"mdName\":\"ドラゴン西川\"},{\"mdCode\":\"aqra\",\"mdEnum\":\"MAKER\",\"mdName\":\"MOODYZ\"},{\"mdCode\":\"kjha\",\"mdEnum\":\"LABEL\",\"mdName\":\"MOODYZ ACID\"}],\"maker\":\"aqra\",\"movieCode\":\"MIAE-288\",\"ownCount\":52,\"publishDate\":\"2018-08-13\",\"score\":8.0,\"singleStar\":\"N\",\"stars\":[{\"aliasName\":[],\"head\":\"\\u0000\",\"name\":\"花咲いあん\",\"starCode\":\"azhuy\"},{\"aliasName\":[],\"head\":\"\\u0000\",\"name\":\"紗々原ゆり\",\"starCode\":\"afctm\"},{\"aliasName\":[],\"head\":\"\\u0000\",\"name\":\"明里ともか\",\"starCode\":\"aeldm\"},{\"aliasName\":[\"笹本結愛\"],\"head\":\"\\u0000\",\"name\":\"浅見せな\",\"starCode\":\"aeydk\"}],\"tags\":[{\"tagCode\":\"bq\",\"tagName\":\"打手枪\"},{\"tagCode\":\"ia\",\"tagName\":\"美容院\"},{\"tagCode\":\"oa\",\"tagName\":\"淫语\"},{\"tagCode\":\"nm\",\"tagName\":\"荡妇\"},{\"tagCode\":\"arga\",\"tagName\":\"屁股\"},{\"tagCode\":\"aroq\",\"tagName\":\"数位马赛克\"}],\"url\":\"javli7jw3q\",\"viewedCount\":25,\"wantCount\":88}";
        JMovie jMovie = JSONObject.parseObject(movie, JMovie.class);
        System.out.println(jMovie);
        jMovieService.insertMovie(jMovie);
    }

    @Test
    public void all() {
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
    public void c() {
        String content = "点击进入2下载mageneet:?xt=urn:.torrentbtih:6f831c832cacd4fda1a419f381cb439fc825dd9e";
        if (StringUtils.isNotBlank(content) && content.matches(".*?(點擊進入下載|点击进入下载|magnet:\\?xt=|\\.torrent).*?")) {
            System.out.println("DDD");
        }
    }
}
