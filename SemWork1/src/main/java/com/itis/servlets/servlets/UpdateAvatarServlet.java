package com.itis.servlets.servlets;

import com.itis.servlets.dao.impl.UsersRepositoryJdbcTemplateImpl;
import com.itis.servlets.dto.out.UserDto;
import com.itis.servlets.models.FileInfo;
import com.itis.servlets.models.User;
import com.itis.servlets.services.impl.FilesServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/update-avatar")
@MultipartConfig
public class UpdateAvatarServlet extends HttpServlet {
	private FilesServiceImpl filesService;
	private UsersRepositoryJdbcTemplateImpl usersRepository;

	@Override
	public void init(ServletConfig config) {
		this.filesService = (FilesServiceImpl) config.getServletContext().getAttribute("filesService");
		this.usersRepository = (UsersRepositoryJdbcTemplateImpl) config.getServletContext().getAttribute("usersRepository");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part part = request.getPart("file");
		HttpSession session = request.getSession(true);
		UserDto userDto = (UserDto) session.getAttribute("user");
		FileInfo fileInfo = filesService.saveFileToStorage(
				part.getInputStream(),
				part.getSubmittedFileName(),
				part.getContentType(),
				part.getSize());
		userDto.setAvatarId(fileInfo.getId());
		session.setAttribute("user", userDto);

		User user = User.builder()
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.age(userDto.getAge())
				.email(userDto.getEmail())
				.avatarId(userDto.getAvatarId())
				.id(userDto.getId())
				.build();
		usersRepository.save(user);
		response.sendRedirect("/profile");
	}
}