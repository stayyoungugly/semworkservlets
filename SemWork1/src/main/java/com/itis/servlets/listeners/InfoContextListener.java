package com.itis.servlets.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itis.servlets.dao.*;
import com.itis.servlets.dao.impl.*;
import com.itis.servlets.services.*;
import com.itis.servlets.services.impl.*;
import com.itis.servlets.services.validation.Validator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class InfoContextListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		String DB_USERNAME;
		String DB_PASSWORD;
		String DB_URL;
		String DB_DRIVER;
		String IMAGES_STORAGE_PATH;

		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Требуется файл properties");
		}
		DB_USERNAME = (String) properties.get("db.user");
		DB_PASSWORD = (String) properties.get("db.password");
		DB_URL = (String) properties.get("db.url");
		DB_DRIVER = (String) properties.get("db.driver");
		IMAGES_STORAGE_PATH = (String) properties.get("storage.images");

		ServletContext servletContext = servletContextEvent.getServletContext();

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DB_DRIVER);
		dataSource.setUsername(DB_USERNAME);
		dataSource.setPassword(DB_PASSWORD);
		dataSource.setUrl(DB_URL);

		UsersRepository usersRepository = new UsersRepositoryJdbcTemplateImpl(dataSource);
		FilesRepository filesRepository = new FilesRepositoryJdbcTemplateImpl(dataSource);
		SongsRepository songsRepository = new SongsRepositoryJdbcTemplateImpl(dataSource);
		PlaylistsRepository playlistsRepository = new PlaylistsRepositoryJdbcTemplateImpl(dataSource);
		PostsRepository postsRepository = new PostsRepositoryJdbcTemplateImpl(dataSource);
		UsersSongsRepository usersSongsRepository = new UsersSongsRepositoryJdbcTemplateImpl(dataSource);
		UsersPlaylistsRepository usersPlaylistsRepository = new UsersPlaylistsRepositoryJdbcTemplateImpl(dataSource);
		SongsPlaylistsRepository songsPlaylistsRepository = new SongsPlaylistsRepositoryJdbcTemplateImpl(dataSource);

		PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
		Validator validator = new ValidatorImpl(usersRepository);
		ObjectMapper objectMapper = new ObjectMapper();

		SongsPlaylistsService songsPlaylistsService = new SongsPlaylistsServiceImpl(songsPlaylistsRepository);
		SongsService songsService = new SongsServiceImpl(songsRepository);
		PlaylistsService playlistsService = new PlaylistsServiceImpl(playlistsRepository, songsService, songsPlaylistsService);
		UsersSongsService usersSongsService = new UsersSongsServiceImpl(usersSongsRepository);
		UsersPlaylistsService usersPlaylistsService = new UsersPlaylistsServiceImpl(usersPlaylistsRepository);


		PostsService postsService = new PostsServiceImpl(postsRepository);
		FilesService filesService = new FilesServiceImpl(IMAGES_STORAGE_PATH, filesRepository);
		SignUpService signUpService = new SignUpServiceImpl(usersRepository, passwordEncoder, validator);
		SignInService signInService = new SignInServiceImpl(usersRepository, passwordEncoder);

		servletContext.setAttribute("usersRepository", usersRepository);
		servletContext.setAttribute("filesRepository", filesRepository);
		servletContext.setAttribute("songsRepository", songsRepository);
		servletContext.setAttribute("playlistsRepository", playlistsRepository);
		servletContext.setAttribute("postsRepository", postsRepository);
		servletContext.setAttribute("usersSongsRepository", usersSongsRepository);
		servletContext.setAttribute("usersPlaylistsRepository", usersPlaylistsRepository);
		servletContext.setAttribute("songsPlaylistsRepository", songsPlaylistsRepository);

		servletContext.setAttribute("songsService", songsService);
		servletContext.setAttribute("playlistsService", playlistsService);
		servletContext.setAttribute("usersSongsService", usersSongsService);
		servletContext.setAttribute("usersPlaylistsService", usersPlaylistsService);
		servletContext.setAttribute("songsPlaylistsService", songsPlaylistsService);
		servletContext.setAttribute("songsService", songsService);
		servletContext.setAttribute("postsService", postsService);
		servletContext.setAttribute("filesService", filesService);
		servletContext.setAttribute("signUpService", signUpService);
		servletContext.setAttribute("signInService", signInService);

		servletContext.setAttribute("objectMapper", objectMapper);

	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
}
