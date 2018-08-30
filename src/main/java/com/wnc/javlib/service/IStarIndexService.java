package com.wnc.javlib.service;

import com.wnc.javlib.entity.JStar;
import org.jsoup.nodes.Document;

import java.util.List;

public interface IStarIndexService
{
    List<JStar> getPageStars(Document doc );

    /**
     * 获取某索引下的明星页数
     * 
     * @return
     */
    int getPageCount( Document doc );
}
