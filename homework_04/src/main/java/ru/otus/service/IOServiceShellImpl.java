package ru.otus.service;

import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Primary
public class IOServiceShellImpl implements IOService {

    private final Terminal terminal;


    public IOServiceShellImpl(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public String input() {
        String input;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(terminal.input()))) {
            input = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return input;
    }

    @Override
    public void output(String string) {
        terminal.writer().println(string);
        terminal.writer().flush();
    }

    @Override
    public void outputEmptyLine() {
        terminal.writer().println();
    }
}
