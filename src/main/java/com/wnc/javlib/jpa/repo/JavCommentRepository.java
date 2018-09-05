package com.wnc.javlib.jpa.repo;

import com.wnc.javlib.jpa.entity.JavComment;
import org.springframework.data.repository.CrudRepository;

public interface JavCommentRepository
        extends CrudRepository<JavComment, Integer> {

}