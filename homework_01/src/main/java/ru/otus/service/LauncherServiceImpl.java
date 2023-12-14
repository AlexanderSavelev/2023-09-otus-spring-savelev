package ru.otus.service;

import ru.otus.dao.TestDao;
import ru.otus.model.Answer;
import ru.otus.model.Question;
import ru.otus.model.Test;

import java.util.List;

public class LauncherServiceImpl implements LauncherService {

    private final TestDao testDao;

    private final OutputService outputService;

    public LauncherServiceImpl(TestDao testDao, OutputService outputService) {
        this.testDao = testDao;
        this.outputService = outputService;
    }

    @Override
    public void launch() {
        Test test = testDao.load();
        List<Question> questions = test.getQuestions();
        for (Question question : questions) {
            outputService.outputString(question.getId() + ". " + question.getText());
            for (Answer answer : question.getAnswers()) {
                outputService.outputString(answer.getId() + " " + answer.getText());
            }
        }
    }
}
