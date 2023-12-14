package ru.otus.dao;

import ru.otus.model.Answer;
import ru.otus.model.Question;
import ru.otus.model.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestDaoImpl implements TestDao {

    private final String separator;

    private final String fileName;

    private final int passPercentage;

    public TestDaoImpl(String separator, String fileName, int passPercentage) {
        this.separator = separator;
        this.fileName = fileName;
        this.passPercentage = passPercentage;
    }

    @Override
    public Test load() {
        List<Question> questions = new ArrayList<>();
        InputStream stream = readFile(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            int j = 1;
            while ((line = reader.readLine()) != null) {
                Question question = new Question(j, loadText(line), loadAnswers(line));
                questions.add(question);
                j++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Test(questions, passPercentage);
    }

    private InputStream readFile(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(fileName);
    }

    private String loadText(String line) {
        String[] array = line.split(separator);
        return array[0];
    }

    private List<Answer> loadAnswers(String line) {
        String[] array = line.split(separator);
        List<Answer> answerList = new ArrayList<>();
        for (int i = 1; i < array.length - 1; i++) {
            String answerId = String.valueOf((char) (64 + i));
            boolean isRight = answerId.equals(array[array.length - 1]);
            answerList.add(new Answer(answerId, array[i], isRight));
        }
        return answerList;
    }
}
