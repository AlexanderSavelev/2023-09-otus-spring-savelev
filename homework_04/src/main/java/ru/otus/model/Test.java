package ru.otus.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Test {

    private final List<Question> questions;

    private final int passPercentage;

    private int questionCounter;

    public Test(List<Question> questions, int passPercentage) {
        this.questions = questions;
        this.passPercentage = passPercentage;
        this.questionCounter = 0;
    }

    public boolean hasNextQuestion() {
        return (questionCounter + 1) <= questions.size();
    }

    public Question getNextQuestion() {
        Question question = questions.get(questionCounter);
        questionCounter++;
        return question;
    }

    public Map<Integer, String> getRightAnswers() {
        Map<Integer, String> rightAnswers = new HashMap<>();
        for (Question question : questions) {
            for (Answer answer : question.getAnswers()) {
                if (answer.isRight()) {
                    rightAnswers.put(question.getId(), answer.getId());
                }
            }
        }
        return rightAnswers;
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
