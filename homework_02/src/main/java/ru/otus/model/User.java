package ru.otus.model;

import lombok.Getter;

@Getter
public class User {

    private final String firstName;

    private final String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!this.firstName.equals(other.firstName)) {
            return false;
        }
        return this.lastName.equals(other.lastName);
    }
}
