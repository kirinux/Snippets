package org.craftedsw.katas.validation;

/**
 *
 *
 */
public class PersonBuilder {

    private Person current;

    public static PersonBuilder newInstance() {
        return new PersonBuilder();
    }

    public PersonBuilder newPerson(String firstName, String name, int age) {
        this.current = new Person(firstName, name, age);
        return this;
    }

    public PersonBuilder withComment(String comment) {
        this.current.setComment(comment);
        return this;
    }

    public PersonBuilder withEmail(String email) {
        this.current.setEmail(email);
        return this;
    }

    public PersonBuilder withCountry(String country) {
        this.current.setCountry(country);
        return this;
    }

    public PersonBuilder withHobbies(String...hobbies) {
        for (String s: hobbies) {
            this.current.addHobby(s);
        }
        return this;
    }

    public Person build() {
        return this.current;
    }

}
