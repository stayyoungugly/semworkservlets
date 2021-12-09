package com.itis.servlets.dao.impl;

import com.itis.servlets.dao.FilesRepository;
import com.itis.servlets.models.FileInfo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FilesRepositoryJdbcTemplateImpl implements FilesRepository {

	private final static String SQL_FIND_BY_NAME = "select * from file_info where original_file_name = ?;";
	private final static String SQL_SELECT_ALL = "select * from file_info;";
	private final static String SQL_INSERT = "insert into file_info(storage_file_name, original_file_name, type, size) " +
			"values (?, ?, ?, ?)";
	private final static String SQL_UPDATE = "update file_info set storage_file_name = ?, original_file_name = ?, type = ?, size = ? where id = ?";
	private final static String SQL_SELECT_BY_ID = "select * from file_info where id = ?";
	private static final String SQL_DELETE = "delete from file_info where id = ?";

	private final JdbcTemplate jdbcTemplate;

	public FilesRepositoryJdbcTemplateImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private final RowMapper<FileInfo> fileRowMapper = (row, rowNumber) ->
			FileInfo.builder()
					.id(row.getLong("id"))
					.originalFileName(row.getString("original_file_name"))
					.storageFileName(row.getString("storage_file_name"))
					.size(row.getLong("size"))
					.type(row.getString("type"))
					.build();

	@Override
	public FileInfo save(FileInfo item) {
		if (item.getId() == null) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
				statement.setString(1, item.getStorageFileName());
				statement.setString(2, item.getOriginalFileName());
				statement.setString(3, item.getType());
				statement.setLong(4, item.getSize());
				return statement;
			}, keyHolder);
			item.setId(keyHolder.getKey().longValue());
		} else {
			jdbcTemplate.update(SQL_UPDATE,
					item.getStorageFileName(),
					item.getOriginalFileName(),
					item.getType(),
					item.getSize(),
					item.getId()
			);
		}
		return item;
	}

	public FileInfo update(Long id, FileInfo item) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(SQL_UPDATE, new String[]{"id"});
			statement.setString(1, item.getOriginalFileName());
			statement.setString(2, item.getStorageFileName());
			statement.setString(3, item.getType());
			statement.setLong(4, item.getSize());
			statement.setLong(5, id);
			return statement;
		}, keyHolder);
		item.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
		return item;
	}

	@Override
	public Optional<FileInfo> findById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, fileRowMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<FileInfo> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL, fileRowMapper);
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

