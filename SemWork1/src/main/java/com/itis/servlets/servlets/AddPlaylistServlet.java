package com.itis.servlets.servlets;

import com.itis.servlets.dto.out.PlaylistDto;
import com.itis.servlets.dto.out.SongDto;
import com.itis.servlets.dto.out.UserDto;
import com.itis.servlets.models.FileInfo;
import com.itis.servlets.services.FilesService;
import com.itis.servlets.services.PlaylistsService;
import com.itis.servlets.services.SongsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
@WebServlet("/add-playlist")
public class AddPlaylistServlet extends HttpServlet {
	private PlaylistsService playlistsService;
	private FilesService filesService;
	private SongsService songsService;

	@Override
	public void init(ServletConfig config) {
		songsService = (SongsService) config.getServletContext().getAttribute("songsService");
		playlistsService = (PlaylistsService) config.getServletContext().getAttribute("playlistsService");
		filesService = (FilesService) config.getServletContext().getAttribute("filesService");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
		request.setAttribute("user", userDto);
		List<SongDto> songs = songsService.getByAuthorId(userDto.getId());
		request.setAttribute("songs", songs);
		request.getRequestDispatcher("add_playlist.ftl").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
		boolean shared = request.getParameter("isShared") != null;
		Long cover_Id = 0L;

		if (request.getPart("cover_file").getSize() > 0) {
			Part part = request.getPart("cover_file");
			FileInfo fileInfo = filesService.saveFileToStorage(part.getInputStream(),
					part.getSubmittedFileName(),
					part.getContentType(),
					part.getSize());
			cover_Id = fileInfo.getId();
		}

		List<SongDto> songs = new ArrayList<>();
		if (request.getParameterValues("checked") != null) {
			String[] songs_id = request.getParameterValues("checked");
			if (songs_id.length > 0) {
				for (String song_id : songs_id) {
					SongDto song = songsService.getById(Long.parseLong(song_id)).get();
					songs.add(song);
				}
			}
		}
		PlaylistDto form = PlaylistDto.builder()
				.title(request.getParameter("title"))
				.description(request.getParameter("description"))
				.songs(songs)
				.isShared(shared)
				.createdAt(new Timestamp(System.currentTimeMillis()))
				.author(userDto)
				.coverId(cover_Id)
				.likes(0L)
				.build();
		if (form.getTitle().isEmpty() || form.getSongs().isEmpty()) {
			request.setAttribute("message", "Укажите все необходимые данные");
			List<SongDto> songs_new = songsService.getByAuthorId(userDto.getId());
			request.setAttribute("songs", songs_new);
			request.getRequestDispatcher("add_playlist.ftl").forward(request, response);
		} else {
			playlistsService.addPlaylist(form);
			response.sendRedirect("/profile");
		}
	}
}
