package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.config.TestProperties;
import ru.otus.model.Answer;
import ru.otus.model.Question;
import ru.otus.model.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TestDaoImpl implements TestDao {

    private final TestProperties testProperties;

    public TestDaoImpl(TestProperties testProperties) {
        this.testProperties = testProperties;
    }

    @Override
    public Test load() {
        List<Question> questions = new ArrayList<>();
        InputStream stream = readFile(testProperties.getName());
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
        return new Test(questions, testProperties.getPass());
    }

    private InputStream readFile(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(fileName);
    }

    private String loadText(String line) {
        String[] array = line.split(testProperties.getSeparator());
        return array[0];
    }

    private List<Answer> loadAnswers(String line) {
        String[] array = line.split(testProperties.getSeparator());
        List<Answer> answerList = new ArrayList<>();
        for (int i = 1; i < array.length - 1; i++) {
            String answerId = String.valueOf((char) (64 + i));
            boolean isRight = answerId.equals(array[array.length - 1]);
            answerList.add(new Answer(answerId, array[i], isRight));
        }
        return answerList;
    }
}
