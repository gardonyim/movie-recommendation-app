CREATE TABLE IF NOT EXISTS recommendations (
    id INT NOT NULL AUTO_INCREMENT,
    recommendation_text TEXT,
    rating INT,
    movie_id INT,
    viewer_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (viewer_id) REFERENCES viewers(id)
);