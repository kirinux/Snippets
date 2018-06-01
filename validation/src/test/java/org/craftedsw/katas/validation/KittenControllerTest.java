package org.craftedsw.katas.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/**
 *
 *
 */
@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest
public class KittenControllerTest {

    @Autowired
    private KittenController kittenController;

    @Test
    public void register_kitten_should_not_raise_error() throws Exception {
        assertThat(kittenController).isNotNull();
        kittenController.registerKittenWithAttributes("name", 10, "cutest", null);
    }

    @Test
    public void register_kitten_without_name_should_raise_error() throws Exception {
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> {
            kittenController.registerKittenWithAttributes("", 10, "cutest", null);
        }).withMessageContaining("registerKittenWithAttributes.name");
    }

    @Test
    public void register_kitten_with_not_past_date_should_raise_error() throws Exception {
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> {
            kittenController.registerKittenWithAttributes("name", 10, "cutest", LocalDate.now());
        }).withMessageContaining("registerKittenWithAttributes.birthDate");
    }

    @Test
    public void register_kitten_bean_should_not_raise_error() throws Exception {
        assertThatCode(() -> {
            kittenController.postRegisterKittenWithBean(new Kitten("name", 10, "breed", LocalDate.of(2017, 9, 30)));
        }).doesNotThrowAnyException();
    }

    @Test
    public void register_kitten_null_bean_should_raise_error() throws Exception {
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> {
            kittenController.postRegisterKittenWithBean(null);
        });
    }

    @Test
    public void register_kitten_bean_with_not_past_date_should_raise_error() throws Exception {
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> {
            kittenController.postRegisterKittenWithBean(new Kitten("name", 10, "breed", LocalDate.now()));
        }).withMessageContaining("postRegisterKittenWithBean.kitten.birthDate");
    }

    @Test
    public void register_kitten_bean_with_multiple_errors() throws Exception {
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> {
            kittenController.postRegisterKittenWithBean(new Kitten("", -10, "breed", LocalDate.of(2017, 9, 30)));
        });
    }
}
