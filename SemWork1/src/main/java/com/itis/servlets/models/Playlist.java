package com.itis.servlets.models;

import com.itis.servlets.dto.out.SongDto;
import com.itis.servlets.dto.out.UserDto;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Playlist {
	private Long id;
	private User author;
	private String title;
	private String description;
	private Long coverId;
	private Timestamp createdAt;
	private Long likes;
	private Boolean isShared;
	private List<SongDto> songs;
}
