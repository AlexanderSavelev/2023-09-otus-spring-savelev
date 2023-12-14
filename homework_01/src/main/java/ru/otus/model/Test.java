package ru.otus.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Test {

    private List<Question> questions;

    public Test(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Test other = (Test) obj;
        if (this.questions.size() != other.questions.size()) {
            return false;
        }
        for (int i = 0; i < this.questions.size(); i++) {
            if (!this.questions.get(i).equals(other.questions.get(i))) {
                return false;
            }
        }
        return true;
    }
}
