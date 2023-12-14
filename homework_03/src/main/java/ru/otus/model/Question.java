package ru.otus.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Question {

    private final int id;

    private final String text;

    private final List<Answer> answers;

    private int currentAnswer;

    public Question(int id, String text, List<Answer> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
        this.currentAnswer = 0;
    }

    public boolean hasNextAnswer() {
        return (currentAnswer + 1) <= answers.size();
    }

    public Answer getNextAnswer() {
        Answer answer = answers.get(currentAnswer);
        currentAnswer++;
        return answer;
    }

    @Override
    public String toString() {
        return id + ". " + text;
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
