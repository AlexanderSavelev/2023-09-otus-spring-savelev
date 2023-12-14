package ru.otus.controller;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.GenreService;

@ShellComponent
@ShellCommandGroup("Genres")
public class GenreControllerImpl extends AbstractController implements GenreController {

    private final GenreService genreService;

    public GenreControllerImpl(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    @ShellMethod(value = "Create genre", key = "genre create")
    public String create(@ShellOption(value = {"-n", "--name"}, help = "Genre name") String name) {
        return genreService.create(name);
    }

    @Override
    @ShellMethod(value = "Update genre", key = "genre update")
    public String update(@ShellOption(value = {"-i", "--id"}, help = "Genre id") long id,
                         @ShellOption(value = {"-n", "--name"}, help = "Genre new name") String newName) {
        return genreService.update(id, newName);
    }

    @Override
    @ShellMethod(value = "Find all genres", key = "genre find-all")
    public String findAll() {
        return genreService.findAll();
    }

    @Override
    @ShellMethod(value = "Find genre by id", key = "genre find-by-id")
    public String findById(@ShellOption(value = {"-i", "--id"}, help = "Genre id") long id) {
        return genreService.findById(id);
    }

    @Override
    @ShellMethod(value = "Delete genre by id", key = "genre delete-by-id")
    public void deleteById(@ShellOption(value = {"-i", "--id"}, help = "Genre id") long id) {
        genreService.deleteById(id);
    }
}