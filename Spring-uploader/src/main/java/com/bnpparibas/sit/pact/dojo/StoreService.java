package com.bnpparibas.sit.pact.dojo;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 *
 */
public interface StoreService {
    void storeFile(MultipartFile file) throws Exception;
}
