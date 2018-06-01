package org.craftedsw.katas.validation;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.*;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 *
 */

public class ValidationTest {

    private Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    private <T> void  printViolations(Set<ConstraintViolation<T>> violations) {

        for (ConstraintViolation<T> v : violations) {
            System.out.println("constraint " + v);
            System.out.println("descriptor" + v.getConstraintDescriptor());
            System.out.println("getAnnotation " + v.getConstraintDescriptor().getAnnotation());
            System.out.println("getExecutableParameters " + v.getExecutableParameters());
            System.out.println("getExecutableReturnValue " + v.getExecutableReturnValue());
            System.out.println("getInvalidValue " + v.getInvalidValue());
            System.out.println("getMessage " + v.getMessage());
            System.out.println("getMessageTemplate " + v.getMessageTemplate());
            System.out.println("getPropertyPath " + v.getPropertyPath());
        }
    }

    @Test
    public void valid_correct_person() {
        Validator validator = getValidator();
        Person p = PersonBuilder.newInstance().newPerson("firstName", "name", 10)
                .withCountry("FR")
                .build();
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        assertThat(violations).isEmpty();
    }


    @Test
    public void null_name_should_create_empty_violation() {
        Validator validator = getValidator();
        Person p = PersonBuilder.newInstance().newPerson("firstName", null, 10)
                .withCountry("FR")
                .build();
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        assertThat(violations).isNotEmpty();
        assertThat(violations).hasSize(1);

        for (ConstraintViolation<Person> v : violations) {
            assertThat(v.getPropertyPath().toString()).isEqualTo("name");
            assertThat(v.getConstraintDescriptor().getAnnotation().annotationType().getCanonicalName()).isEqualTo(NotBlank.class.getCanonicalName());
        }

        printViolations(violations);

    }

    @Test
    public void blank_name_should_create_empty_violation() {
        Validator validator = getValidator();
        Person p = PersonBuilder.newInstance().newPerson("firstName", "", 10)
                .withCountry("FR")
                .build();
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        assertThat(violations).isNotEmpty();
        assertThat(violations).hasSize(1);

        for (ConstraintViolation<Person> v : violations) {
            assertThat(v.getPropertyPath().toString()).isEqualTo("name");
            assertThat(v.getConstraintDescriptor().getAnnotation().annotationType().getCanonicalName()).isEqualTo(NotBlank.class.getCanonicalName());
        }

        printViolations(violations);

    }

    @Test
    public void null_firstname_should_create_empty_violation() {
        Validator validator = getValidator();
        Person p = PersonBuilder.newInstance().newPerson(null, "name", 10)
                .withCountry("FR")
                .build();
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        assertThat(violations).isNotEmpty();
        assertThat(violations).hasSize(1);

        for (ConstraintViolation<Person> v : violations) {
            assertThat(v.getPropertyPath().toString()).isEqualTo("firstName");
            assertThat(v.getConstraintDescriptor().getAnnotation().annotationType().getCanonicalName()).isEqualTo(NotNull.class.getCanonicalName());
            assertThat(v.getMessage()).isEqualTo("firstName could not be null");
        }

        printViolations(violations);

    }

    @Test
    public void too_short_comment_should_have_violation_with_EL_message() {
        Validator validator = getValidator();
        Person p = PersonBuilder.newInstance().newPerson("firstName", "name", 10)
                .withComment("trop petit")
                .withCountry("FR")
                .build();
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        printViolations(violations);
        assertThat(violations).isNotEmpty();
        violations.stream().filter(v -> v.getConstraintDescriptor().getAnnotation().annotationType().getClass().equals(Min.class))
                .forEach(v -> {
                    assertThat(v.getPropertyPath().toString()).isEqualTo("comment");
                    assertThat(v.getMessage()).startsWith("Un commentaire est obligatoire taille minimum 10");
                });

    }

    @Test
    public void too_long_comment_should_have_violation_with_EL_message() {
        Validator validator = getValidator();
        Person p = PersonBuilder.newInstance().newPerson("firstName", "name", 10)
                .withComment("trop graaaaaaaaaaannnnnnnnnnnnnnnnnnnnnndddddddddddddd")
                .withCountry("FR")
                .build();
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        printViolations(violations);
        assertThat(violations).isNotEmpty();
        violations.stream().filter(v -> v.getConstraintDescriptor().getAnnotation().annotationType().getClass().equals(Max.class))
                .forEach(v -> {
                    assertThat(v.getPropertyPath().toString()).isEqualTo("comment");
                    assertThat(v.getMessage()).isEqualTo("taille maximum 20");
                });



    }

    @Test
    public void bad_email_format_should_add_violation() {
        Validator validator = getValidator();
        Person p = PersonBuilder.newInstance().newPerson("firstName", "name", 10)
                .withEmail("truc")
                .withCountry("FR")
                .build();
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        printViolations(violations);
        assertThat(violations).isNotEmpty();
        violations.stream().filter(v -> v.getConstraintDescriptor().getAnnotation().annotationType().getClass().equals(Email.class))
                .forEach(v -> {
                    assertThat(v.getPropertyPath().toString()).isEqualTo("email");
                    assertThat(v.getMessage()).contains("is not a correct email");
                });
    }

    @Test
    public void blank_country_should_add_violation() {
        Validator validator = getValidator();
        Person p = PersonBuilder.newInstance().newPerson("firstName", "name", 10)
                .withEmail("truc")
                .build();
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        printViolations(violations);
        assertThat(violations).isNotEmpty();
        violations.stream().filter(v -> v.getConstraintDescriptor().getAnnotation().annotationType().getClass().equals(NotBlank.class))
                .forEach(v -> {
                    assertThat(v.getPropertyPath().toString()).isEqualTo("country");
//                    assertThat(v.getMessage()).contains("is not a correct email");
                });


    }

    @Test
    public void empty_hobby_list_is_valid() {
        Validator validator = getValidator();
        Person p = PersonBuilder.newInstance().newPerson("firstName", "name", 10)
                .withEmail("truc@truc.com")
                .withCountry("FR")
                .build();
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        printViolations(violations);
        assertThat(violations).isEmpty();

    }

    @Test
    public void empty_hobby_list_is_invalid() {
        Validator validator = getValidator();
        Person p = PersonBuilder.newInstance().newPerson("firstName", "name", 10)
                .withEmail("truc@truc.com")
                .withCountry("FR")
                .withHobbies("")
                .build();
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        printViolations(violations);
        assertThat(violations).isNotEmpty();

    }

}