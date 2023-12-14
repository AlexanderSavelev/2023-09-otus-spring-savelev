package ru.otus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnswerTest {

    private Answer answer;

    private final String id = "A";

    private final String text = "answer";

    private final boolean isRight = true;

    @BeforeEach
    void setUp() {
        answer = new Answer(id, text, isRight);
    }

    @Test
    void getId() {
        assertEquals(id, answer.getId());
    }

    @Test
    void getText() {
        assertEquals(text, answer.getText());
    }

    @Test
    void getIsRight() {
        assertEquals(isRight, answer.isRight());
    }

    @Test
    void setId() {
        String newId = "B";
        answer.setId(newId);
        assertEquals(newId, answer.getId());
    }

    @Test
    void setText() {
        String newText = "new answer";
        answer.setText(newText);
        assertEquals(newText, answer.getText());
    }

    @Test
    void setRight() {
        boolean isFalse = false;
        answer.setRight(isFalse);
        assertEquals(isFalse, answer.isRight());
    }
}