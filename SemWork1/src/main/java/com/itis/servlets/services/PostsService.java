package com.itis.servlets.services;

import com.itis.servlets.dto.PostDto;

import java.util.List;

public interface PostsService {
	List<PostDto> getByAuthorId(Long authorId);
	List<PostDto> getAll();
	PostDto addPost(PostDto postDto);
}