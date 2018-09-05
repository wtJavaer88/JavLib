package com.wnc.javlib.jpa.repo;

import com.wnc.javlib.jpa.entity.JMovie;
import org.springframework.data.repository.CrudRepository;

public interface JMovieRepository
        extends CrudRepository<JMovie, String> {

}