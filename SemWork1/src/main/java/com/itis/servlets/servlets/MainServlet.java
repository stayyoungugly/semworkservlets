package com.itis.servlets.servlets;

import com.itis.servlets.dto.PostDto;
import com.itis.servlets.dto.out.PlaylistDto;
import com.itis.servlets.dto.out.SongDto;
import com.itis.servlets.services.PlaylistsService;
import com.itis.servlets.services.PostsService;
import com.itis.servlets.services.SongsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
	private SongsService songsService;
	private PlaylistsService playlistsService;
	private PostsService postsService;

	@Override
	public void init(ServletConfig config) {
		songsService = (SongsService) config.getServletContext().getAttribute("songsService");
		playlistsService = (PlaylistsService) config.getServletContext().getAttribute("playlistsService");
		postsService = (PostsService) config.getServletContext().getAttribute("postsService");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<SongDto> songs = songsService.getAll();
		songs.removeIf(songDto -> !songDto.getIsShared());
		request.setAttribute("songs", songs);
		List<PlaylistDto> playlists = playlistsService.getAll();
		songs.removeIf(playlistDto -> !playlistDto.getIsShared());
		request.setAttribute("playlists", playlists);
		List<PostDto> posts = postsService.getAll();
		request.setAttribute("posts", posts);
		request.setAttribute("user", request.getSession(true).getAttribute("user"));
		request.getRequestDispatcher("main.ftl").forward(request, response);
	}
}
