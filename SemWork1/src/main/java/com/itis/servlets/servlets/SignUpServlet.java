package com.itis.servlets.servlets;

import com.itis.servlets.dto.in.UserForm;
import com.itis.servlets.exceptions.ValidationException;
import com.itis.servlets.models.FileInfo;
import com.itis.servlets.services.FilesService;
import com.itis.servlets.services.SignUpService;
import com.itis.servlets.services.validation.ErrorEntity;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/sign-up")
@MultipartConfig
public class SignUpServlet extends HttpServlet {

	private SignUpService signUpService;
	private FilesService filesService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();

		this.filesService = (FilesService) context.getAttribute("filesService");
		this.signUpService = (SignUpService) context.getAttribute("signUpService");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("message", "Регистрация");
		request.getRequestDispatcher("sign_up.ftl").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserForm form;
		try {
			Long avatar_Id = 0L;
			if (request.getPart("file").getSize() > 0) {
				Part part = request.getPart("file");
				FileInfo fileInfo = filesService.saveFileToStorage(part.getInputStream(),
						part.getSubmittedFileName(),
						part.getContentType(),
						part.getSize());
				avatar_Id = fileInfo.getId();
			}
			form = UserForm.builder()
					.firstName(request.getParameter("firstName"))
					.lastName(request.getParameter("lastName"))
					.email(request.getParameter("email"))
					.avatarId(avatar_Id)
					.password(request.getParameter("password"))
					.age(Long.parseLong(request.getParameter("age")))
					.build();

		} catch (NumberFormatException e) {
			Set<ErrorEntity> errors = new HashSet<>();
			errors.add(ErrorEntity.INVALID_REQUEST);
			request.setAttribute("errors", errors);
			request.setAttribute("message", "Данные введены неверно");
			request.getRequestDispatcher("sign_up.ftl").forward(request, response);
			return;
		}

		try {
			signUpService.signUp(form);
		} catch (ValidationException e) {
			request.setAttribute("error", e.getEntity());
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("sign_up.ftl").forward(request, response);
			return;
		}
		response.sendRedirect("/sign-in");
	}
}