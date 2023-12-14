package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.otus.dao.TestDao;
import ru.otus.model.Answer;
import ru.otus.model.Question;
import ru.otus.model.Result;
import ru.otus.model.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LauncherServiceImplTest {

    private TestDao testDao;

    private IOService ioService;

    private LauncherServiceImpl launcherService;

    private ru.otus.model.Test test;

    private User user;

    private Map<Integer, String> usersAnswers;

    private Result result;

    private LocaleService localeService;

    private ArgumentCaptor<String> captor;

    @BeforeEach
    void setUp() {
        Question firstQuestion = new Question(1, "question_0",
                List.of(new Answer("A", "answer_0", true), new Answer("B", "answer_1", false)));
        Question secondQuestion = new Question(2, "question_1",
                List.of(new Answer("C", "answer_2", true), new Answer("D", "answer_3", false)));
        List<Question> questions = List.of(firstQuestion, secondQuestion);
        test = new ru.otus.model.Test(questions, 50);
        user = new User("First Name", "Last Name");
        usersAnswers = Map.of(firstQuestion.getId(), firstQuestion.getAnswers().get(0).getId(),
                secondQuestion.getId(), secondQuestion.getAnswers().get(0).getId());
        result = new Result(test, user);
        testDao = Mockito.mock(TestDao.class);
        ioService = Mockito.mock(IOService.class);
        localeService = Mockito.mock(LocaleService.class);
        launcherService = new LauncherServiceImpl(testDao, ioService, localeService);
        captor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    void launch() {
        List<String> output = createOutput(test);
        when(testDao.load())
                .thenReturn(test);
        when(localeService.getMessage("user.first.name", null))
                .thenReturn("Please enter first name");
        when(localeService.getMessage("user.last.name", null))
                .thenReturn("Please enter last name");
        when(localeService.getMessage("press.any.key", null))
                .thenReturn("Press ENTER key to start test");
        when(localeService.getMessage("choose.answer", null))
                .thenReturn("Choose you answer");
        when(localeService.getMessage("test.result", user.toString(),
                String.valueOf(result.getResults())))
                .thenReturn("Result:\n" + user + " has " + result.getResults() +
                        "% correct answers");
        if (result.getResults() >= test.getPassPercentage()) {
            when(localeService.getMessage("test.passed", null))
                    .thenReturn("Test passed!");
        } else {
            when(localeService.getMessage("test.not.passed", null))
                    .thenReturn("Test not passed!");
        }
        when(ioService.input())
                .thenReturn(user.getFirstName())
                .thenReturn(user.getLastName())
                .thenReturn(null)
                .thenReturn(usersAnswers.get(1))
                .thenReturn(usersAnswers.get(2));
        launcherService.launch();
        int outputEmptyLines = countEmptyLines(output);
        Mockito.verify(ioService, Mockito.times(output.size() - outputEmptyLines))
                .output(captor.capture());
        Mockito.verify(ioService, Mockito.times(outputEmptyLines))
                .outputEmptyLine();
        output = clearFromNull(output);
        for (int i = 0; i < output.size(); i++) {
            assertEquals(output.get(i), captor.getAllValues().get(i));
        }
    }

    private List<String> createOutput(ru.otus.model.Test test) {
        List<String> output = new ArrayList<>();
        output.add("Please enter first name");
        output.add("Please enter last name");
        output.add(null);
        output.add("Press ENTER key to start test");
        List<Question> questions = test.getQuestions();
        for (Question question : questions) {
            output.add(question.getId() + ". " + question.getText());
            for (Answer answer : question.getAnswers()) {
                output.add(answer.getId() + " " + answer.getText());
            }
            output.add("Choose you answer");
            output.add(null);
            result.applyAnswer(question.getId(), usersAnswers.get(question.getId()));
        }
        output.add("Result:\n" + user + " has " + result.getResults() +
                "% correct answers");
        if (result.getResults() >= test.getPassPercentage()) {
            output.add("Test passed!");
        } else {
            output.add("Test not passed!");
        }
        return output;
    }

    private int countEmptyLines(List<String> lines) {
        int counter = 0;
        for (String line : lines) {
            if (line == null) {
                counter++;
            }
        }
        return counter;
    }

    private List<String> clearFromNull(List<String> lines) {
        return lines.stream()
                .filter(Objects::nonNull)
                .toList();
    }
}