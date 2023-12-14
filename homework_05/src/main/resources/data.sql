INSERT INTO author(full_name)
VALUES ('AuthorOne'),
       ('AuthorTwo'),
       ('AuthorThree'),
       ('AuthorFour'),
       ('AuthorFive');

INSERT INTO genre(name)
VALUES ('GenreOne'),
       ('GenreTwo'),
       ('GenreThree'),
       ('GenreFour'),
       ('GenreFive');

INSERT INTO book(title, author_id, genre_id)
VALUES ('BookOne', 1, 1),
       ('BookTwo', 2, 2),
       ('BookThree', 3, 3),
       ('BookFour', 4, 4),
       ('BookFive', 5, 5);