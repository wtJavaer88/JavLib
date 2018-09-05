package com.wnc.javlib.jpa.repo;

import com.wnc.javlib.jpa.entity.JavUser;
import org.springframework.data.repository.CrudRepository;

public interface JavUserRepository
        extends CrudRepository<JavUser, String> {

}