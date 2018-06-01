package org.craftedsw.katas.validation;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 *
 *
 */
public class Kitten {
    @NotBlank
    private  String name;
    @Range(min = 0, max = 30)
    private  int age;
    @Pattern(regexp = "\\w+")
    private  String breed;
    @Past
    private  LocalDate birthDate;

    public Kitten() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Kitten(String name, int age, String breed, LocalDate birthDate) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    @Override
    public String toString() {
        return "Kitten{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", breed='" + breed + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kitten kitten = (Kitten) o;

        if (age != kitten.age) return false;
        if (name != null ? !name.equals(kitten.name) : kitten.name != null) return false;
        if (breed != null ? !breed.equals(kitten.breed) : kitten.breed != null) return false;
        return birthDate != null ? birthDate.equals(kitten.birthDate) : kitten.birthDate == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + (breed != null ? breed.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }
}
