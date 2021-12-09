package com.itis.servlets.dto.out;

import com.itis.servlets.models.Playlist;
import com.itis.servlets.models.Song;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PlaylistDto {
	private Long id;
	private UserDto author;
	private String title;
	private String description;
	private Long coverId;
	private Timestamp createdAt;
	private Long likes;
	private Boolean isShared;
	private List<SongDto> songs;

	public static PlaylistDto from(Playlist playlist) {
		return PlaylistDto.builder()
				.id(playlist.getId())
				.author(UserDto.from(playlist.getAuthor()))
				.title(playlist.getTitle())
				.description(playlist.getDescription())
				.coverId(playlist.getCoverId())
				.createdAt(playlist.getCreatedAt())
				.likes(playlist.getLikes())
				.isShared(playlist.getIsShared())
				.songs(playlist.getSongs())
				.build();
	}
}
