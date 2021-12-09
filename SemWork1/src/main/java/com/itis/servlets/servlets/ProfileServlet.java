package com.itis.servlets.servlets;

import com.itis.servlets.dto.PostDto;
import com.itis.servlets.dto.out.PlaylistDto;
import com.itis.servlets.dto.out.SongDto;
import com.itis.servlets.dto.out.UserDto;
import com.itis.servlets.services.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private PostsService postsService;
	private SongsService songsService;
	private PlaylistsService playlistsService;
	private UsersSongsService usersSongsService;
	private UsersPlaylistsService usersPlaylistsService;

	@Override
	public void init(ServletConfig config) {
		postsService = (PostsService) config.getServletContext().getAttribute("postsService");
		songsService = (SongsService) config.getServletContext().getAttribute("songsService");
		playlistsService = (PlaylistsService) config.getServletContext().getAttribute("playlistsService");
		usersPlaylistsService = (UsersPlaylistsService) config.getServletContext().getAttribute("usersPlaylistsService");
		usersSongsService = (UsersSongsService) config.getServletContext().getAttribute("usersSongsService");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
		request.setAttribute("user", userDto);
		request.setAttribute("message", "Загрузить трек");
		List<PostDto> posts = new ArrayList<>();
		if (postsService.getByAuthorId(userDto.getId()) != null) {
			posts = postsService.getByAuthorId(userDto.getId());
		}
		request.setAttribute("posts", posts);
		List<SongDto> songs = songsService.getByAuthorId(userDto.getId());
		System.out.println(songs.size());
		request.setAttribute("songs", songs);
		List<PlaylistDto> playlists = playlistsService.getByAuthorId(userDto.getId());
		request.setAttribute("playlists", playlists);

		List<Long> userSongs = usersSongsService.findByUserId(userDto.getId());
		if (!userSongs.isEmpty()) {
			List<SongDto> userSongsDtoList = userSongs.stream().map(
					n -> songsService.getById(n).get()).collect(Collectors.toList());
			for (SongDto song : userSongsDtoList) {
				System.out.println(song);
			}
			request.setAttribute("songs_in_library", userSongsDtoList);
		} else request.setAttribute("songs_in_library", userSongs);

		List<Long> userPlaylists = usersPlaylistsService.findByUserId(userDto.getId());
		if (!userPlaylists.isEmpty()) {
			List<PlaylistDto> userPlaylistsDtoList = userPlaylists.stream().map(
					n -> playlistsService.getById(n).get()).collect(Collectors.toList());
			for (PlaylistDto playlist : userPlaylistsDtoList) {
				System.out.println(playlist);
			}
			request.setAttribute("playlists_in_library", userPlaylistsDtoList);
		} else request.setAttribute("playlists_in_library", userPlaylists);
		request.getRequestDispatcher("/profile.ftl").forward(request, response);
	}
}