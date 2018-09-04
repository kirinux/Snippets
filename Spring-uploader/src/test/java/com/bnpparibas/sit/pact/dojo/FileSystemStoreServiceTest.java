package com.bnpparibas.sit.pact.dojo;

import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

/**
 *
 *
 */
@RunWith(SpringRunner.class)
public class FileSystemStoreServiceTest {

    @ClassRule
    public static TemporaryFolder testFolder = new TemporaryFolder();

    @Autowired
    private StoreService service;

    @Test
    public void should_write_test_file_on_file_system_() {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some content".getBytes());
        Assertions.assertThatCode(() -> service.storeFile(file)).doesNotThrowAnyException();
    }

    @Test
    public void should_throw_exception_when_input_file_is_null() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> service.storeFile(null));
    }

    @Test
    public void should_throw_exception_when_input_file_is_empty() {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "".getBytes());
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> service.storeFile(file));
    }

    @Configuration
    static class AppTestConfiguration {
        @Bean
        public File getUploadFolder() throws IOException {
            return testFolder.newFolder();
        }

        @Bean
        public StoreService getStoreService() {
            return new FileSystemStoreService();
        }

    }

}
