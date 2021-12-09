package com.itis.servlets.dao;

import com.itis.servlets.dao.base.CrudRepository;
import com.itis.servlets.models.FileInfo;

public interface FilesRepository extends CrudRepository<FileInfo, Long> {
}
