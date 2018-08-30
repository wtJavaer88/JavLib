package com.wnc.javlib;

import com.wnc.javlib.entity.JMovie;
import com.wnc.javlib.service.MovieDetailImpl;
import com.wnc.javlib.utils.JavConfig;
import common.spider.HttpClientUtil;
import org.jsoup.Jsoup;

import java.io.IOException;

public class JavServiceTest {
	public static void main(String[] args) throws IOException {
		String url = JavConfig.DOMAIN + "/?v=javlipiwya";
		url = JavConfig.DOMAIN +"/?v=javlikan3m";
		JMovie movieDetail = new MovieDetailImpl().getMovieDetail(Jsoup.parse(HttpClientUtil.getWebPage(url)));
		System.out.println(movieDetail);
	}
}
