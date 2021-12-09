package com.itis.servlets.services.impl;

import com.itis.servlets.dao.PostsRepository;
import com.itis.servlets.dto.PostDto;
import com.itis.servlets.models.Post;
import com.itis.servlets.models.User;
import com.itis.servlets.services.PostsService;

import java.util.List;
import java.util.stream.Collectors;

public class PostsServiceImpl implements PostsService {
	private final PostsRepository postsRepository;

	public PostsServiceImpl(PostsRepository postsRepository) {
		this.postsRepository = postsRepository;
	}

	@Override
	public List<PostDto> getByAuthorId(Long authorId) {
		return postsRepository.findByAuthorId(authorId).stream()
				.map(PostDto::from)
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getAll() {
		return postsRepository.findAll().stream()
				.map(PostDto::from)
				.collect(Collectors.toList());
	}

	@Override
	public PostDto addPost(PostDto postDto) {
		Post post = Post.builder()
				.id(postDto.getId())
				.author(User.builder()
						.id(postDto.getAuthor().getId())
						.avatarId(postDto.getAuthor().getAvatarId())
						.email(postDto.getAuthor().getEmail())
						.firstName(postDto.getAuthor().getFirstName())
						.lastName(postDto.getAuthor().getLastName())
						.build())
				.content(postDto.getContent())
				.createdAt(postDto.getCreatedAt())
				.build();
		Post savedPost = postsRepository.save(post);
		return PostDto.from(savedPost);
	}
}