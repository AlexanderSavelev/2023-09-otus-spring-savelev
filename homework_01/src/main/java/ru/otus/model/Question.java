package ru.otus.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Question {

    private int id;

    private String text;

    private List<Answer> answers;

    public Question(int id, String text, List<Answer> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Question other = (Question) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!this.text.equals(other.text)) {
            return false;
        }
        if (this.answers.size() != other.answers.size()) {
            return false;
        }
        for (int i = 0; i < this.answers.size(); i++) {
            if (!this.answers.get(i).equals(other.answers.get(i))) {
                return false;
            }
        }
        return true;
    }
}
