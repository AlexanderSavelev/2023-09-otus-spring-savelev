package ru.otus.service;

import java.io.PrintStream;

public class OutputServiceImpl implements OutputService {

    private final PrintStream output;

    public OutputServiceImpl() {
        this.output = new PrintStream(System.out);
    }

    @Override
    public void outputString(String string) {
        output.println(string);
    }
}
