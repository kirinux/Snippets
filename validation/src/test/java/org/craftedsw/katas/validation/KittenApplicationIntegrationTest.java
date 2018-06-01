package org.craftedsw.katas.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KittenApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    private static final String BASE_URL = "http://localhost";

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void miaou_is_ok() throws Exception {
        URI uri = UriComponentsBuilder.fromHttpUrl(String.format("%s:%s", BASE_URL, port))
                .path("/miaou").build().toUri();
        ResponseEntity<String> resp = restTemplate.getForEntity(uri, String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isEqualTo("miaou!");
    }

    @Test
    public void post_kitten_should_return_same_kitten() throws Exception {
        assertThat(restTemplate).isNotNull();
        URI uri = UriComponentsBuilder.fromHttpUrl(String.format("%s:%s", BASE_URL, port))
                .path("/postKitten").build().toUri();

        Kitten kitten = new Kitten("name", 10, "breed", LocalDate.of(2017, 9, 30));
        HttpEntity<Kitten> entity = new HttpEntity<>(kitten);

        ResponseEntity<KittenController.KittenDTO> resp = restTemplate.postForEntity(uri, entity, KittenController.KittenDTO.class);
        assertThat(resp.getBody()).isNotNull();
        KittenController.KittenDTO dto = resp.getBody();
        assertThat(dto.getKitten()).isEqualTo(kitten);
        assertThat(dto.getViolations()).isNull();

    }

    @Test
    public void post_kitten_without_name_should_return_violations_details() throws Exception {
        URI uri = UriComponentsBuilder.fromHttpUrl(String.format("%s:%s", BASE_URL, port))
                .path("/postKitten").build().toUri();

        Kitten kitten = new Kitten("", 10, "breed", LocalDate.of(2017, 9, 30));
        HttpEntity<Kitten> entity = new HttpEntity<>(kitten);
        ResponseEntity<KittenController.KittenDTO> resp = restTemplate.postForEntity(uri, entity, KittenController.KittenDTO.class);

        assertThat(resp.getBody()).isNotNull();
        KittenController.KittenDTO dto = resp.getBody();
        assertThat(dto.getKitten()).isNull();
        assertThat(dto.getViolations()).isNotEmpty();
    }


}
