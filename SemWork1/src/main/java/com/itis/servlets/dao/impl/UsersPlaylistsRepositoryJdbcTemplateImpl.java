package com.itis.servlets.dao.impl;

import com.itis.servlets.dao.UsersPlaylistsRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class UsersPlaylistsRepositoryJdbcTemplateImpl implements UsersPlaylistsRepository {
	private final static String SQL_INSERT = "insert into users_playlists(user_id, playlist_id)" +
			"values (?, ?)" + "ON CONFLICT DO NOTHING";
	private final static String SQL_SELECT_BY_USER = "select playlist_id from users_playlists where users_playlists.user_id = ?";

	private final JdbcTemplate jdbcTemplate;

	public UsersPlaylistsRepositoryJdbcTemplateImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void add(Long userId, Long playlistId) {
		jdbcTemplate.update(SQL_INSERT, userId, playlistId);
	}

	@Override
	public List<Long> findByUserId(Long userId) {
		return jdbcTemplate.queryForList(SQL_SELECT_BY_USER, Long.class, userId);
	}
}
