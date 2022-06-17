DELETE FROM recommendations;
DELETE FROM viewers;
DELETE FROM actor_movie;
DELETE FROM movies;
DELETE FROM actors;
DELETE FROM directors;
DELETE FROM genres;

INSERT INTO actors (id, name) VALUES
    (111, 'testactor');

INSERT INTO directors (id, name) VALUES
    (111, 'testdirector');

INSERT INTO genres (id, type) VALUES
    (111, 'testgenre');

INSERT INTO movies (id, title, release_year, length, average_rating, director_id, genre_id) VALUES
    (111, 'testmovie', 2022, 90, 5.0, 111, 111),
    (112, 'testmovie2', 2022, 90, 6.0, 111, 111),
    (113, 'testmovie3', 2022, 90, 5.5, 111, 111);

INSERT INTO actor_movie (movie_id, actor_id) VALUES
    (111, 111);

INSERT INTO viewers (id, username, password, email, activation, enabled) VALUES
    (111, 'testviewer', '$2a$10$.qSSzsZJzssLjB2riYiUBObwEZ3raz36e/mQ.3TeTyM8A.4kuHvW6', 'test@test', 'testactivation', true),
    (112, 'testuser2', 'password', 'test@test', 'testactivation', true);

INSERT INTO recommendations (id, recommendation_text, rating, movie_id, viewer_id) VALUES
    (111, 'test', 5, 111, 111),
    (112, 'test2', 5, 112, 111),
    (113, 'test3', 5, 113, 111);
