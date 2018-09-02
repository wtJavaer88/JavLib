package com.wnc.javlib.utils;

public class UrlFormatter
{

    public static String getIndexStarsUrl( char index, int page )
    {
        return String.format( JavConfig.INDEX_STAR_SFT, index, page );
    }

    public static String getStarMoviesUrl( String starCode, int page )
    {
        return String.format( JavConfig.STAR_MOVIE_SFT, starCode, page );
    }

}
