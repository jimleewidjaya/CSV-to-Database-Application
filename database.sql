USE ocbc_advanced_test;

CREATE TABLE users (
     username   VARCHAR(100) NOT NULL,
     password   varchar(100) NOT NULL,
     name       varchar(100) NOT NULL,
     PRIMARY KEY (username)
) ENGINE InnoDB;

DESC users;

-- INSERT INTO Persons
-- VALUES (1, 'Tandio', 'Surabaya');

DELETE FROM users WHERE 1;