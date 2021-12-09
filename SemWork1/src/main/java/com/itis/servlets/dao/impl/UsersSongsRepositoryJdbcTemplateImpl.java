package com.itis.servlets.dao.impl;

import com.itis.servlets.dao.UsersSongsRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class UsersSongsRepositoryJdbcTemplateImpl implements UsersSongsRepository {
	private final static String SQL_INSERT = "insert into users_songs(user_id, song_id)" +
			"values (?, ?)" + "ON CONFLICT DO NOTHING";
	private final static String SQL_SELECT_BY_USER = "select song_id from users_songs where users_songs.user_id = ?";
	private static final String SQL_DELETE = "delete from user_songs where user_songs.user_id = ? and user_songs.song_id = ?";

	private final JdbcTemplate jdbcTemplate;

	public UsersSongsRepositoryJdbcTemplateImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void add(Long userId, Long songId) {
		jdbcTemplate.update(SQL_INSERT, userId, songId);
	}

	@Override
	public List<Long> findByUserId(Long userId) {
		return jdbcTemplate.queryForList(SQL_SELECT_BY_USER, Long.class, userId);
	}
}
