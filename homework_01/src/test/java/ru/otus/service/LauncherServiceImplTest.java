package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.otus.dao.TestDao;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LauncherServiceImplTest {

    private TestDao testDao;

    private OutputService outputService;

    private LauncherServiceImpl launcherService;

    private ru.otus.model.Test test;

    private ArgumentCaptor<String> captor;

    @BeforeEach
    void setUp() {
        Question firstQuestion = new Question(1, "question_0",
                List.of(new Answer("A", "answer_0", true), new Answer("B", "answer_1", false)));
        Question secondQuestion = new Question(2, "question_1",
                List.of(new Answer("C", "answer_2", true), new Answer("D", "answer_3", false)));
        List<Question> questions = List.of(firstQuestion, secondQuestion);
        test = new ru.otus.model.Test(questions);
        testDao = Mockito.mock(TestDao.class);
        outputService = Mockito.mock(OutputService.class);
        launcherService = new LauncherServiceImpl(testDao, outputService);
        captor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    void launch() {
        List<String> output = createOutput(test);
        when(testDao.load())
                .thenReturn(test);
        launcherService.launch();
        Mockito.verify(outputService, Mockito.times(output.size()))
                .outputString(captor.capture());
        for (int i = 0; i < output.size(); i++) {
            assertEquals(output.get(i), captor.getAllValues().get(i));
        }
    }

    private List<String> createOutput(ru.otus.model.Test test) {
        List<String> output = new ArrayList<>();
        List<Question> questions = test.getQuestions();
        for (Question question : questions) {
            output.add(question.getId() + ". " + question.getText());
            for (Answer answer : question.getAnswers()) {
                output.add(answer.getId() + " " + answer.getText());
            }
        }
        return output;
    }
}