package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Genre {

    private long id;

    private String name;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Genre other = (Genre) obj;
        if (this.id != other.id) {
            return false;
        }
        return this.name.equals(other.name);
    }
}
