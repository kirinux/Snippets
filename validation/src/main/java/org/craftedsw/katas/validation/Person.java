package org.craftedsw.katas.validation;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class Person {


    @NotNull(message = "firstName could not be null")
    private final String firstName;
    @NotBlank
    private final String name;

    @Positive
    private final int age;

    @Min(value = 10, message = "Un commentaire est obligatoire taille minimum {value}, actuelle ${validatedValue.length()}")
    @Max(value = 20, message = "taille maximum {value}")
    private String comment;

    @Email(message = "email {validatedValue} is not a correct email")
    private String email;

    private String country;

    private List<@NotBlank String> hobbies = new ArrayList<>();


    public Person(String firstName, String name, int age) {
        this.firstName = firstName;
        this.name = name;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public @NotBlank  String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void addHobby(String hobby) {
        hobbies.add(hobby);
    }

    public List<String> getHobbies() {
        return hobbies;
    }
}
