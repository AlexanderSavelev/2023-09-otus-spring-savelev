package ru.otus.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceImpl implements IOService {

    private final Scanner input;

    private final PrintStream output;

    public IOServiceImpl(InputStream input, PrintStream output) {
        this.input = new Scanner(input);
        this.output = output;
    }

    @Override
    public void output(String string) {
        output.println(string);
    }

    @Override
    public void outputEmptyLine() {
        output.println();
    }

    @Override
    public String input() {
        return input.nextLine();
    }
}
