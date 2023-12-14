package ru.otus.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {

    private String id;

    private String text;

    private boolean isRight;

    public Answer(String id, String text, boolean isRight) {
        this.id = id;
        this.text = text;
        this.isRight = isRight;
    }

    @Override
    public String toString() {
        return id + " " + text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Answer other = (Answer) obj;
        if (!this.id.equals(other.id)) {
            return false;
        }
        return this.text.equals(other.text);
    }
}
