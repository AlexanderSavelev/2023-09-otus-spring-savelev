package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class IOServiceImplTest {

    private InputStream input;

    private PrintStream output;

    private IOServiceImpl ioService;

    @BeforeEach
    void setUp() {
        input = Mockito.mock(InputStream.class);
        output = Mockito.mock(PrintStream.class);
        ioService = new IOServiceImpl(input, output);
    }

    @Test
    void output() {
        String testLine = "Test";
        ioService.output(testLine);
        Mockito.verify(output, Mockito.times(1))
                .println(testLine);
    }

    @Test
    void outputEmptyLine() {
        ioService.outputEmptyLine();
        Mockito.verify(output, Mockito.times(1))
                .println();
    }

    @Test
    void input() {
        String testLine = "Test";
        try (MockedConstruction<Scanner> mock = mockConstruction(Scanner.class)) {
            ioService = new IOServiceImpl(input, output);
            when(mock.constructed().get(0).nextLine())
                    .thenReturn(testLine);
            assertEquals(testLine, ioService.input());
        }
    }
}