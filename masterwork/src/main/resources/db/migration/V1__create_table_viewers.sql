CREATE TABLE IF NOT EXISTS viewers (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(255),
    email VARCHAR(255),
    activation VARCHAR(255),
    enabled BOOL,
    PRIMARY KEY (id)
)