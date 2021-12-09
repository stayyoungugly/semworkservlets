package com.itis.servlets.models;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Comment {
	private Long id;
	private User author;
	private String content;
	private Timestamp createdAt;
}
