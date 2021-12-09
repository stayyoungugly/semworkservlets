package com.itis.servlets.servlets;

import com.itis.servlets.dto.out.SongDto;
import com.itis.servlets.dto.out.UserDto;
import com.itis.servlets.services.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet("/open-song")
public class OpenSongServlet extends HttpServlet {
	private SongsService songsService;
	private UsersSongsService usersSongsService;

	@Override
	public void init(ServletConfig config) {
		usersSongsService = (UsersSongsService) config.getServletContext().getAttribute("usersSongsService");
		songsService = (SongsService) config.getServletContext().getAttribute("songsService");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
		request.setAttribute("user", userDto);
		request.getSession().setAttribute("current", request.getParameter("current"));
		Long song_id = Long.parseLong(request.getParameter("song_id"));
		SongDto songDto = songsService.getById(song_id).get();
		request.setAttribute("song", songDto);
		request.getRequestDispatcher("open_song.ftl").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long song_id = Long.parseLong(request.getParameter("song_id"));
		SongDto songDto = songsService.getById(song_id).get();
		String current = (String) request.getSession().getAttribute("current");
		UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
		if (request.getParameter("helper") != null) {
			if (request.getParameter("delete") != null) {
				songsService.delete(song_id);
				if (current.equals("main")) {
					response.sendRedirect("/main");
				} else if (current.equals("profile")) {
					response.sendRedirect("/profile");
				}
			} else if (request.getParameter("close") != null) {
				boolean shared = request.getParameter("checkbox") != null;
				songDto.setIsShared(shared);
				songsService.addSong(songDto);
				if (current.equals("main")) {
					response.sendRedirect("/main");
				} else if (current.equals("profile")) {
					response.sendRedirect("/profile");
				}
			}
		} else if (request.getParameter("add") != null) {
			if (userDto != null) {
				usersSongsService.add(userDto.getId(), song_id);
				response.sendRedirect("/main");
			} else response.sendRedirect("/sign-in");
		} else if (request.getParameter("close") != null) {
			response.sendRedirect("/main");
		}
	}
}
