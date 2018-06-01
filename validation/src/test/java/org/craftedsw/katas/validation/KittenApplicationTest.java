package org.craftedsw.katas.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class KittenApplicationTest {

    private static final String BASE_URL = "http://localhost";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void register_no_kitten_should_not_raise_error() throws Exception {
        assertThat(mockMvc).isNotNull();
        mockMvc.perform(get("/miaou"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
