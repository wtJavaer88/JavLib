package com.wnc.javlib.utils;

public class JavConfig {
	public static final String DOMAIN = "http://v22q.com/cn";
	public static final String INDEXSTARSFT = DOMAIN + "/star_list.php?prefix=%s&page=%d";
	public static final String STARMOVIESFT = DOMAIN + "/vl_star.php?&mode=2&s=%s&page=%d";// mode为2所有影片,1是有评论的
	public static final String MVCOMMENTSFT = DOMAIN + "/videocomments.php?v=%s&mode=2"; // %s为影片url核心,如javlicrf3q
}
