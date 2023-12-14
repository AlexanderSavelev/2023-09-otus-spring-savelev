package ru.otus.service;

import ru.otus.dao.TestDao;
import ru.otus.model.User;
import ru.otus.model.Question;
import ru.otus.model.Answer;
import ru.otus.model.Test;
import ru.otus.model.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LauncherServiceImpl implements LauncherService {

    private final TestDao testDao;

    private final IOService ioService;

    public LauncherServiceImpl(TestDao testDao, IOService ioService) {
        this.testDao = testDao;
        this.ioService = ioService;
    }

    @Override
    public void launch() {
        User user = getUser();
        ioService.outputEmptyLine();
        ioService.output("Press ENTER key to start test");
        ioService.input();

        Map<Integer, String> userAnswers = new HashMap<>();

        Test test = testDao.load();
        List<Question> questions = test.getQuestions();
        for (Question question : questions) {
            ioService.output(question.toString());
            for (Answer answer : question.getAnswers()) {
                ioService.output(answer.toString());
            }
            ioService.output("Choose you answer");
            userAnswers.put(question.getId(), ioService.input());
            ioService.outputEmptyLine();
        }

        Result result = new Result(test, user, userAnswers);
        ioService.output("Result:\n" + user + " has " + result.getResults() +
                "% correct answers");
        ioService.output(result.getResults() >= test.getPassPercentage() ? "Test passed!" : "Test not passed!");
    }

    private User getUser() {
        ioService.output("Please enter first name");
        String firstName = ioService.input();
        ioService.output("Please enter last name");
        String lastName = ioService.input();
        return new User(firstName, lastName);
    }
}
