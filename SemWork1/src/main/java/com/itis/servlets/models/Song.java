package com.itis.servlets.models;

import com.itis.servlets.dto.out.UserDto;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Song {
	private Long id;
	private User author;
	private String title;
	private String creator;
	private String description;
	private Timestamp createdAt;
	private Long coverId;
	private Long songId;
	private Long likes;
	private Boolean isShared;
}
