CREATE TABLE IF NOT EXISTS movies (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL ,
    release_year INT,
    length INT,
    average_rating FLOAT,
    director_id INT,
    genre_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (director_id) REFERENCES directors(id)
)