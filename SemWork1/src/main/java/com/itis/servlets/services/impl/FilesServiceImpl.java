package com.itis.servlets.services.impl;

import com.itis.servlets.dao.FilesRepository;
import com.itis.servlets.exceptions.NotFoundException;
import com.itis.servlets.models.FileInfo;
import com.itis.servlets.services.FilesService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class FilesServiceImpl implements FilesService {

	private final String path;
	private final FilesRepository filesRepository;

	public FilesServiceImpl(String path, FilesRepository filesRepository) {
		this.path = path;
		this.filesRepository = filesRepository;
	}

	@Override
	public FileInfo saveFileToStorage(InputStream inputStream, String originalFileName, String contentType, Long size) {
		FileInfo fileInfo = FileInfo.builder()
				.originalFileName(originalFileName)
				.storageFileName(UUID.randomUUID().toString())
				.size(size)
				.type(contentType)
				.build();
		//if (fileInfo.getType().split("/")[0].equals("image")) {
		try {
			Files.copy(inputStream, Paths.get(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
			fileInfo = filesRepository.save(fileInfo);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		//}
		return fileInfo;
	}

	@Override
	public void readFileFromStorage(Long fileId, OutputStream outputStream) {
		Optional<FileInfo> optionalFileInfo = filesRepository.findById(fileId);
		FileInfo fileInfo = optionalFileInfo.orElseThrow(() -> new NotFoundException("File not found"));
		File file = new File(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
		try {
			Files.copy(file.toPath(), outputStream);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public FileInfo getFileInfo(Long fileId) {
		return filesRepository.findById(fileId).orElseThrow(() -> new NotFoundException("File not found"));
	}
}
