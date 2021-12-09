package com.itis.servlets.services;


import com.itis.servlets.models.FileInfo;

import java.io.InputStream;
import java.io.OutputStream;

public interface FilesService {
	FileInfo saveFileToStorage(InputStream file, String originalFileName, String contentType, Long size);

	void readFileFromStorage(Long fileId, OutputStream outputStream);

	FileInfo getFileInfo(Long fileId);
}
