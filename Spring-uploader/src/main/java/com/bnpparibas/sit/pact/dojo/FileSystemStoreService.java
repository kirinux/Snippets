package com.bnpparibas.sit.pact.dojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 *
 */
@Service
public class FileSystemStoreService implements StoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemStoreService.class);

    @Autowired
    private File uploadFolder;


    @Override
    public void storeFile(MultipartFile file) throws Exception {

        if (uploadFolder == null || !uploadFolder.exists()) {
            throw new IllegalArgumentException("Upload folder does not exists: " + uploadFolder);
        }

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("No file selected");
        }
        LOGGER.info("[FileSystem] Handle file {} in upload folder {} ", file.getOriginalFilename(), uploadFolder);

        byte[] bytes = file.getBytes();
        Path path = new File(uploadFolder, file.getOriginalFilename()).toPath();
        Files.write(path, bytes);
        LOGGER.info("File {} stored successfully", path);
    }
}
