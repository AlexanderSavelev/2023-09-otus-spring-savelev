package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Author {

    private long id;

    private String fullName;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (this.id != other.id) {
            return false;
        }
        return this.fullName.equals(other.fullName);
    }
}
