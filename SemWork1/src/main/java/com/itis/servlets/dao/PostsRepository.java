package com.itis.servlets.dao;

import com.itis.servlets.dao.base.CrudRepository;
import com.itis.servlets.models.Post;

import java.util.List;

public interface PostsRepository extends CrudRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
}