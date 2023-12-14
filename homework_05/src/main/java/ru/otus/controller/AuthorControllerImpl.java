package ru.otus.controller;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.AuthorService;

@ShellComponent
@ShellCommandGroup("Authors")
public class AuthorControllerImpl extends AbstractController implements AuthorController {

    private final AuthorService authorService;

    public AuthorControllerImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    @ShellMethod(value = "Create author", key = "author create")
    public String create(@ShellOption(value = {"-n", "--name"}, help = "Author full name") String fullName) {
        return authorService.create(fullName);
    }

    @Override
    @ShellMethod(value = "Update author", key = "author update")
    public String update(@ShellOption(value = {"-i", "--id"}, help = "Author id") long id,
                         @ShellOption(value = {"-n", "--name"}, help = "Author new full name") String newFullName) {
        return authorService.update(id, newFullName);
    }

    @Override
    @ShellMethod(value = "Find all authors", key = "author find-all")
    public String findAll() {
        return authorService.findAll();
    }

    @Override
    @ShellMethod(value = "Find author by id", key = "author find-by-id")
    public String findById(@ShellOption(value = {"-i", "--id"}, help = "Author id") long id) {
        return authorService.findById(id);
    }

    @Override
    @ShellMethod(value = "Delete author by id", key = "author delete-by-id")
    public void deleteById(@ShellOption(value = {"-i", "--id"}, help = "Author id") long id) {
        authorService.deleteById(id);
    }
}
