INSERT INTO author(full_name)
VALUES ('TestAuthorOne'),
       ('TestAuthorTwo'),
       ('TestAuthorThree');

INSERT INTO genre(name)
VALUES ('TestGenreOne'),
       ('TestGenreTwo'),
       ('TestGenreThree');

INSERT INTO book(title, author_id, genre_id)
VALUES ('TestBookOne', 1, 1),
       ('TestBookTwo', 2, 2),
       ('TestBookThree', 3, 3);