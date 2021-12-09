package com.itis.servlets.servlets;

import com.itis.servlets.dto.out.SongDto;
import com.itis.servlets.dto.out.UserDto;
import com.itis.servlets.models.FileInfo;
import com.itis.servlets.services.FilesService;
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

@MultipartConfig
@WebServlet("/add-song")
public class AddSongServlet extends HttpServlet {
	private SongsService songService;
	private FilesService filesService;

	@Override
	public void init(ServletConfig config) {
		songService = (SongsService) config.getServletContext().getAttribute("songsService");
		filesService = (FilesService) config.getServletContext().getAttribute("filesService");
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserDto userDto = (UserDto) request.getSession(true).getAttribute("user");
		boolean shared = request.getParameter("isShared") != null;
		Long cover_Id = 0L;
		Long song_Id = 0L;
		if (request.getPart("cover_file").getSize() > 0) {
			Part part = request.getPart("cover_file");
			FileInfo fileInfo = filesService.saveFileToStorage(part.getInputStream(),
					part.getSubmittedFileName(),
					part.getContentType(),
					part.getSize());
			cover_Id = fileInfo.getId();
		}
		if (request.getPart("song_file").getSize() > 0) {
			Part part = request.getPart("song_file");
			FileInfo fileInfo = filesService.saveFileToStorage(part.getInputStream(),
					part.getSubmittedFileName(),
					part.getContentType(),
					part.getSize());
			song_Id = fileInfo.getId();
		}

		SongDto form = SongDto.builder()
				.title(request.getParameter("title"))
				.creator(request.getParameter("creator"))
				.description(request.getParameter("description"))
				.isShared(shared)
				.createdAt(new Timestamp(System.currentTimeMillis()))
				.author(userDto)
				.coverId(cover_Id)
				.likes(0L)
				.songId(song_Id)
				.build();
		if (form.getCreator().isEmpty() || form.getTitle().isEmpty() || form.getSongId() == 0) {
			request.setAttribute("message", "Укажите все необходимые данные");
		} else {
			songService.addSong(form);
		}
		response.sendRedirect("/profile");
	}
}