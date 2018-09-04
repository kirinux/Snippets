package com.bnpparibas.sit.pact.dojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
@RestController
public class FileResources {

    private static final Logger LOGGER = Logger.getLogger(FileResources.class.getName());

    @Autowired
    private StoreService service;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        LOGGER.log(Level.FINE, "UploadFile endpoint");
        try {
            service.storeFile(file);
        } catch (Exception e) {
            String msg = "impossible to store file " + file.getOriginalFilename();
            LOGGER.log(Level.SEVERE, msg, e);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(String.format("File %s uploaded successfully", file.getOriginalFilename()), HttpStatus.OK);
    }


}
