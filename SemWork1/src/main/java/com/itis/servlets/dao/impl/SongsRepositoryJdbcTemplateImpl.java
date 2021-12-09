package com.itis.servlets.dao.impl;

import com.itis.servlets.dao.SongsRepository;
import com.itis.servlets.models.Song;
import com.itis.servlets.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

public class SongsRepositoryJdbcTemplateImpl implements SongsRepository {

	private final static String SQL_INSERT = "insert into songs(author_id, title, creator, description, created_at, is_shared, song_id, cover_id) " +
			"values (?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String SQL_UPDATE = "update songs set author_id = ?, title = ?, creator = ?, description = ?, created_at = ?, is_shared = ?, cover_id = ? where id = ?";
	private final static String SQL_UPDATE_LIKES = "update songs set likes = ? where id = ?";
	private final static String SQL_UPDATE_COVER = "update songs set cover_id = ? where id = ?";
	private final static String SQL_SELECT_BY_ID = "select songs.id as songs_id, author_id, title, description, cover_id,creator,song_id, created_at, likes, is_shared, users.id as users_id, first_name, last_name, age, password_hash, email, avatar_id from songs left join users on songs.author_id = users.id where songs.id = ? order by created_at desc";
	private final static String SQL_SELECT_ALL = "select songs.id as songs_id, author_id, title, description, cover_id, song_id, creator,created_at, likes, is_shared, users.id as users_id, first_name, last_name, age, password_hash, email, avatar_id from songs left join users on songs.author_id = users.id order by created_at desc";
	private final static String SQL_SELECT_BY_AUTHOR_ID = "select songs.id as songs_id, author_id, title, description,creator,song_id, cover_id, created_at, likes, is_shared, users.id as users_id, first_name, last_name, age, password_hash, email, avatar_id from songs left join users on songs.author_id = users.id where users.id = ? order by created_at desc";
	private static final String SQL_DELETE = "delete from songs where id = ?";

	private final RowMapper<Song> rowMapper = (row, rowNumber) -> Song.builder()
			.id(row.getLong("songs_id"))
			.title(row.getString("title"))
			.creator(row.getString("creator"))
			.description(row.getString("description"))
			.isShared(row.getBoolean("is_shared"))
			.author(User.builder()
					.id(row.getLong("users_id"))
					.firstName(row.getString("first_name"))
					.lastName(row.getString("last_name"))
					.age(row.getLong("age"))
					.hashPassword(row.getString("password_hash"))
					.email(row.getString("email"))
					.avatarId(row.getLong("avatar_id"))
					.build())
			.coverId(row.getLong("cover_id") == 0 ? null : row.getLong("cover_id"))
			.songId(row.getLong("song_id"))
			.createdAt(row.getTimestamp("created_at"))
			.likes(row.getLong("likes"))
			.build();

	private final JdbcTemplate jdbcTemplate;

	public SongsRepositoryJdbcTemplateImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Optional<Song> findById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void updateLikes(Long likes, Long songId) {
		jdbcTemplate.update(SQL_UPDATE_LIKES,
				likes, songId
		);
	}

	@Override
	public List<Song> findByAuthorId(Long authorId) {
		return jdbcTemplate.query(SQL_SELECT_BY_AUTHOR_ID, rowMapper, authorId);
	}

	@Override
	public List<Song> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
	}

	@Override
	public Song save(Song item) {
		if (item.getId() == null) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
				statement.setLong(1, item.getAuthor().getId());
				statement.setString(2, item.getTitle());
				statement.setString(3, item.getCreator());
				statement.setString(4, item.getDescription());
				statement.setTimestamp(5, item.getCreatedAt());
				statement.setBoolean(6, item.getIsShared());
				statement.setLong(7, item.getSongId());
				if (item.getCoverId() == 0) {
					statement.setNull(8, Types.NULL);
				} else statement.setLong(8, item.getCoverId());
				return statement;
			}, keyHolder);
			if (keyHolder.getKey() != null) {
				item.setId(keyHolder.getKey().longValue());
			}
		} else {
			jdbcTemplate.update(SQL_UPDATE,
					item.getAuthor().getId(),
					item.getTitle(),
					item.getCreator(),
					item.getDescription(),
					item.getCreatedAt(),
					item.getIsShared(),
					item.getCoverId(),
					item.getId()
			);
		}
		return item;
	}

	@Override
	public void delete(Long id) {
		if (findById(id).isPresent()) {
			jdbcTemplate.update(connection -> {
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
				preparedStatement.setLong(1, id);
				return preparedStatement;
			});
			System.out.println("User with id=" + id + " was deleted");
		} else System.out.println("Error! User with id=" + id + " doesn't exist");
	}
}