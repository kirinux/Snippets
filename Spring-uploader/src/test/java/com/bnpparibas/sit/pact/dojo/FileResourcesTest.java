package com.bnpparibas.sit.pact.dojo;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 *
 *
 */
@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest
@WebMvcTest
public class FileResourcesTest {



    @Autowired
    private MockMvc mvc;

    @MockBean
    private StoreService service;

    @Test
    public void should_save_uploaded_file() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some content".getBytes());
        this.mvc.perform(multipart("/upload")
                .file(file))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("uploaded successfully")));

        ArgumentCaptor<MultipartFile> captor = ArgumentCaptor.forClass(MultipartFile.class);
        Mockito.verify(service, Mockito.times(1)).storeFile(captor.capture());
        Assertions.assertThat(file).isEqualTo(captor.getValue());
    }

    @Test
    public void should_return_http_error_when_store_service_failed() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some content".getBytes());

        Mockito.doAnswer(invoc -> {throw new IOException();
        }).when(service).storeFile(file);
        this.mvc.perform(multipart("/upload")
                .file(file))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("impossible to store")));


    }


}