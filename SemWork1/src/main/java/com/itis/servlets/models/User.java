package com.itis.servlets.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Long age;
	private String password;
	private String hashPassword;
	private String email;
	private Long avatarId;
	private List<Post> posts;
	private List<Song> songs;
	private List<Playlist> playlists;
}


