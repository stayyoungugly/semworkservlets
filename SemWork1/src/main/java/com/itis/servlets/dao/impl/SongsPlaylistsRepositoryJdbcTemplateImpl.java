package com.itis.servlets.dao.impl;

import com.itis.servlets.dao.SongsPlaylistsRepository;
import com.itis.servlets.dao.UsersSongsRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class SongsPlaylistsRepositoryJdbcTemplateImpl implements SongsPlaylistsRepository {
	private final static String SQL_INSERT = "insert into songs_playlists(song_id, playlist_id)" +
			"values (?, ?)";
	private final static String SQL_SELECT_BY_PLAYLIST = "select song_id from songs_playlists where songs_playlists.playlist_id = ?";

	private final JdbcTemplate jdbcTemplate;

	public SongsPlaylistsRepositoryJdbcTemplateImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void add(Long songId, Long playlistId) {
		jdbcTemplate.update(SQL_INSERT, songId, playlistId);
	}

	@Override
	public List<Long> findByPlaylistId(Long playlistId) {
		return jdbcTemplate.queryForList(SQL_SELECT_BY_PLAYLIST, Long.class, playlistId);
	}
}
