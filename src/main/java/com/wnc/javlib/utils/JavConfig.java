package com.wnc.javlib.utils;

import com.wnc.basic.BasicFileUtil;

public class JavConfig {
    public static final String DOMAIN = "http://v22q.com/cn";
    public static final String INDEX_STAR_SFT = DOMAIN + "/star_list.php?prefix=%s&page=%d";
    /**
     * Tag下面所有的影片
     */
    public static final String TAG_MOVIE_SFT = DOMAIN + "/vl_genre.php?&mode=2&g=%s&page=%d";//缩略图模式
    public static final String TAG_CHECK_MOVIE_SFT = DOMAIN + "/vl_genre.php?list&mode=2&g=%s&page=%d";//文字列表模式
    /**
     * 演员的所有影片
     */
    public static final String STAR_MOVIE_SFT = DOMAIN + "/vl_star.php?&mode=2&s=%s&page=%d";// mode为2所有影片,1是有评论的
    /**
     * 获取全部文章,不分语言, 需要分页
     */
    public static final String MV_COMMENT_SFT = DOMAIN + "/videocomments.php?mode=2&v=%s&page=%d"; // %s为影片url核心,如javlicrf3q

    /**
     * 获取全部评论(REVIEW),不分语言
     */
    public static final String MV_REVIEW_SFT = DOMAIN + "/?v=%s&mode=2";// 一页搞定,无需分页
    /**
     * 获取用户基本信息
     */
    public static final String USER_INFO_SFT = DOMAIN + "/user.php?u=%s";//用户id

    public static final String MV_DETAIL_SFT = DOMAIN + "/?v=%s";

    public static final String APP_DIR = "G:\\data\\spider\\javlib\\";

    public static final String MOVIES_DIR = APP_DIR + "movies\\";
    public static final String MOVIES_LOG = MOVIES_DIR + "movies-log.txt";
    public static final String MOVIES_ERR_LOG = MOVIES_DIR + "movies-err-log.txt";

    public static final String TAG_MOVIE_DIR = APP_DIR + "tags\\";

    public static final String TAGS_FILE = APP_DIR + "tags.txt";
    public static final String TAGS_LOG = TAG_MOVIE_DIR + "tags-log.txt";
    public static final String TAGS_ERR_LOG = TAG_MOVIE_DIR + "tags-err-log.txt";

    public static final String TAG_CHECK_MOVIE_DIR = APP_DIR + "tags-check\\";
    public static final String TAGS_CHECK_LOG = TAG_CHECK_MOVIE_DIR + "tags-check-log.txt";
    public static final String TAGS_CHECK_ERR_LOG = TAG_CHECK_MOVIE_DIR + "tags-err-log.txt";

    static {
        BasicFileUtil.makeDirectory(APP_DIR);
        BasicFileUtil.makeDirectory(MOVIES_DIR);
        BasicFileUtil.makeDirectory(TAG_MOVIE_DIR);
        BasicFileUtil.makeDirectory(TAG_CHECK_MOVIE_DIR);
    }
}
