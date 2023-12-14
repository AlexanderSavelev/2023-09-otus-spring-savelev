package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book {

    private long id;

    private String title;

    private Author author;

    private Genre genre;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!this.author.equals(other.author)) {
            return false;
        }
        return this.genre.equals(other.genre);
    }
}
