package ru.otus.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Result {

    private final Test test;

    private final User user;

    private final Map<Integer, String> answers;

    public Result(Test test, User user, Map<Integer, String> answers) {
        this.test = test;
        this.user = user;
        this.answers = answers;
    }

    public int getResults() {
        int score = 0;
        Map<Integer, String> rightAnswers = test.getRightAnswers();
        for (Integer questionId : rightAnswers.keySet()) {
            if (answers.get(questionId).equals(rightAnswers.get(questionId))) {
                score++;
            }
        }
        float finalScore = (float) score / rightAnswers.size() * 100;
        return (int) finalScore;
    }
}
