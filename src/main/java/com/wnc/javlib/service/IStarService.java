package com.wnc.javlib.service;

import com.wnc.javlib.jpa.entity.JMovie;
import org.jsoup.nodes.Document;

import java.util.List;


public interface IStarService {
    /**
     * @param doc 某一页的Movie列表,只有基本信息
     * @return
     */
    List<JMovie> getPageBasicMovies(Document doc);

    int getPageCount(Document doc);
}

