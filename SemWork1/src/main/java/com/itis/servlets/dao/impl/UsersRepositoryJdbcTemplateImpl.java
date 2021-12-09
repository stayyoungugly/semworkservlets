package com.itis.servlets.dao.impl;

import com.itis.servlets.dao.UsersRepository;
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
import java.util.Objects;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

	private final JdbcTemplate jdbcTemplate;

	public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private final static String SQL_INSERT = "insert into users(first_name, last_name, age, password_hash, email, avatar_id) " +
			"values (?, ?, ?, ?, ?, ?)";
	private final static String SQL_UPDATE = "update users set first_name = ?, last_name = ?, age = ?, email = ?, avatar_id = ? where id = ?";
	private final static String SQL_UPDATE_AVATAR = "update users set avatar_id = ? where id = ?";
	private final static String SQL_UPDATE_TOKEN = "update user_token set token = ? where user_id = ?";
	private final static String SQL_CREATE_TOKEN = "insert into user_token(user_id, token) VALUES (?, ?)";
	private final static String SQL_SELECT_BY_ID = "select * from users where id = ?";
	private final static String SQL_SELECT_TOKEN_BY_USER_ID = "select * from user_token where user_id = ?";
	private final static String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";
	private final static String SQL_SELECT_BY_TOKEN = "select * from user_token join users  on user_token.user_id = users.id where token = ?";
	private final static String SQL_SELECT_ALL = "select * from users";
	private static final String SQL_DELETE = "delete from users where id = ?";

	private final RowMapper<User> rowMapper = (row, rowNumber) ->
			User.builder()
					.id(row.getLong("id"))
					.firstName(row.getString("first_name"))
					.lastName(row.getString("last_name"))
					.age(row.getLong("age"))
					.hashPassword(row.getString("password_hash"))
					.email(row.getString("email"))
					.avatarId((row.getLong("avatar_id") == 0L) ? null : row.getLong("avatar_id"))
					.build();

	@Override
	public Optional<User> findByEmail(String email) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, rowMapper, email));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findByToken(String token) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_TOKEN, rowMapper, token));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void updateAvatarForUser(Long userId, Long fileId) {
		jdbcTemplate.update(SQL_UPDATE_AVATAR, fileId, userId);
	}

	@Override
	public Optional<String> getTokenByUserId(Long userId) {
		return jdbcTemplate.query(SQL_SELECT_TOKEN_BY_USER_ID, resultSet -> {
			if (resultSet.next()) {
				return Optional.of(resultSet.getString("token"));
			} else {
				return Optional.empty();
			}
		}, userId);
	}

	@Override
	public void createTokenForUser(Long userId, String token) {
		jdbcTemplate.update(SQL_CREATE_TOKEN, userId, token);
	}

	@Override
	public void updateTokenForUser(Long userId, String token) {
		jdbcTemplate.update(SQL_UPDATE_TOKEN, token, userId);
	}

	@Override
	public Optional<User> findById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
	}

	@Override
	public User save(User item) {
		if (!findById(item.getId()).isPresent()) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
				statement.setString(1, item.getFirstName());
				statement.setString(2, item.getLastName());
				statement.setLong(3, item.getAge());
				statement.setString(4, item.getHashPassword());
				statement.setString(5, item.getEmail());
				if (item.getAvatarId() != 0) {
					statement.setLong(6, item.getAvatarId());
				} else {
					statement.setNull(6, Types.NULL);
				}
				return statement;
			}, keyHolder);
			if (keyHolder.getKey() != null) {
				item.setId(keyHolder.getKey().longValue());
			}
		} else {
			jdbcTemplate.update(SQL_UPDATE,
					item.getFirstName(),
					item.getLastName(),
					item.getAge(),
					item.getEmail(),
					item.getAvatarId(),
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
