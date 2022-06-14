INSERT INTO actors (id, name) VALUES
    (1, 'testviewer');

INSERT INTO directors (id, name) VALUES
    (1, 'testdirector');

INSERT INTO genres (id, type) VALUES
    (1, 'testgenre');

INSERT INTO movies (id, title, release_year, length, average_rating, director_id, genre_id) VALUES
    (1, 'testmovie', 2022, 90, 5.0, 1, 1);

INSERT INTO actor_movie (movie_id, actor_id) VALUES
    (1, 1);
