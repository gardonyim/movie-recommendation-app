DELETE FROM recommendations;
DELETE FROM viewers;
DELETE FROM actor_movie;
DELETE FROM movies;
DELETE FROM actors;
DELETE FROM directors;
DELETE FROM genres;

INSERT INTO actors (id, name) VALUES
    (1, 'testactor');

INSERT INTO directors (id, name) VALUES
    (1, 'testdirector');

INSERT INTO genres (id, type) VALUES
    (1, 'testgenre');

INSERT INTO movies (id, title, release_year, length, average_rating, director_id, genre_id) VALUES
    (1, 'testmovie', 2022, 90, 5.0, 1, 1),
    (2, 'testmovie2', 2022, 90, 6.0, 1, 1),
    (3, 'testmovie3', 2022, 90, 5.5, 1, 1);

INSERT INTO actor_movie (movie_id, actor_id) VALUES
    (1, 1);

INSERT INTO viewers (id, username, password, email, activation, enabled) VALUES
    (1, 'testviewer', 'password', 'test@test', 'testactivation', true);

INSERT INTO recommendations (id, recommendation_text, rating, movie_id, viewer_id) VALUES
    (1, 'test', 5, 1, 1);
