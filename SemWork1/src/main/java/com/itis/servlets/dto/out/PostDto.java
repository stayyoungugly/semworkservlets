package com.itis.servlets.dto;

import com.itis.servlets.dto.out.UserDto;
import com.itis.servlets.models.Post;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private Long id;
	private UserDto author;
	private String content;
	private Timestamp createdAt;

	public static PostDto from(Post post) {
		return PostDto.builder()
				.id(post.getId())
				.author(UserDto.from(post.getAuthor()))
				.content(post.getContent())
				.createdAt(post.getCreatedAt())
				.build();
	}
}
