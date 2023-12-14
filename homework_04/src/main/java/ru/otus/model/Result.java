package ru.otus.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Result {

    private final Test test;

    private final User user;

    private final Map<Integer, String> answers;

    public Result(Test test, User user) {
        this.test = test;
        this.user = user;
        this.answers = new HashMap<>();
    }

    public void applyAnswer(int questionId, String answerId) {
        answers.put(questionId, answerId);
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
