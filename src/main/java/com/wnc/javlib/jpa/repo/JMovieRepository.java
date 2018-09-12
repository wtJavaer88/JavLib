package com.wnc.javlib.jpa.repo;

import com.wnc.javlib.jpa.entity.JMovie;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface JMovieRepository
        extends CrudRepository<JMovie, String> {

    @Modifying
    @Query(value = "UPDATE JMovie t SET t.hasTorrent = :hasTorrent WHERE t.movieCode= :movieCode")
    void updateTorrent(@Param("hasTorrent") int hasTorrent, @Param("movieCode") String movieCode);
}