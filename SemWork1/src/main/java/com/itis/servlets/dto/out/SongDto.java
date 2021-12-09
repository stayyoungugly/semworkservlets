package com.itis.servlets.dto.out;

import com.itis.servlets.models.Song;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SongDto {
	private Long id;
	private UserDto author;
	private String title;
	private String creator;
	private String description;
	private Timestamp createdAt;
	private Long coverId;
	private Long songId;
	private Long likes;
	private Boolean isShared;

	public static SongDto from(Song song) {
		return SongDto.builder()
				.id(song.getId())
				.author(UserDto.from(song.getAuthor()))
				.title(song.getTitle())
				.creator(song.getCreator())
				.description(song.getDescription())
				.createdAt(song.getCreatedAt())
				.coverId(song.getCoverId())
				.songId(song.getSongId())
				.likes(song.getLikes())
				.isShared(song.getIsShared())
				.build();
	}
}
