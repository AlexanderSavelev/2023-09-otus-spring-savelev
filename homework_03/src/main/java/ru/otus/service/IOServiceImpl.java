package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final Scanner input;

    private final PrintStream output;

    public IOServiceImpl(@Value("#{T(java.lang.System).in}") InputStream input,
                         @Value("#{T(java.lang.System).out}") PrintStream output) {
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
