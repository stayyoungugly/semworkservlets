package com.itis.servlets.servlets;

import com.itis.servlets.dto.out.PlaylistDto;
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
@WebServlet("/open-playlist")
public class OpenPlaylistServlet extends HttpServlet {
	private PlaylistsService playlistsService;
	private UsersPlaylistsService usersPlaylistsService;

	@Override
	public void init(ServletConfig config) {
		usersPlaylistsService = (UsersPlaylistsService) config.getServletContext().getAttribute("usersPlaylistsService");
		playlistsService = (PlaylistsService) config.getServletContext().getAttribute("playlistsService");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
		request.setAttribute("user", userDto);
		request.getSession().setAttribute("current", request.getParameter("current"));
		Long playlist_id = Long.parseLong(request.getParameter("playlist_id"));
		PlaylistDto playlistDto = playlistsService.getById(playlist_id).get();
		request.setAttribute("playlist", playlistDto);
		request.getRequestDispatcher("open_playlist.ftl").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long playlist_id = Long.parseLong(request.getParameter("playlist_id"));
		UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
		String current = (String) request.getSession().getAttribute("current");
		request.setAttribute("user", userDto);
		PlaylistDto playlistDto = playlistsService.getById(playlist_id).get();
		if (request.getParameter("helper") != null) {
			if (request.getParameter("delete") != null) {
				playlistsService.delete(playlist_id);
				if (current.equals("main")) {
					response.sendRedirect("/main");
				} else if (current.equals("profile")) {
					response.sendRedirect("/profile");
				}
			} else if (request.getParameter("close") != null) {
				boolean shared = request.getParameter("checkbox") != null;
				playlistDto.setIsShared(shared);
				playlistsService.addPlaylist(playlistDto);
				if (current.equals("main")) {
					response.sendRedirect("/main");
				} else if (current.equals("profile")) {
					response.sendRedirect("/profile");
				}
			}
		} else if (request.getParameter("add") != null) {
			if (userDto != null) {
				usersPlaylistsService.add(userDto.getId(), playlist_id);
				response.sendRedirect("/main");
			} else response.sendRedirect("/sign-in");
		} else if (request.getParameter("close") != null) {
			response.sendRedirect("/main");
		}
	}
}
