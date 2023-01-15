CREATE TABLE users (
                       id INT NOT NULL AUTO_INCREMENT,
                       name VARCHAR (100) NOT NULL,
                       email VARCHAR (100) NOT NULL,
                       password VARCHAR (100) NOT NULL,
                       date DATETIME NOT NULL,
                       PRIMARY KEY (id)
);
CREATE TABLE categories (
                            id INT NOT NULL AUTO_INCREMENT,
                            name VARCHAR (100) NOT NULL,
                            PRIMARY KEY (id)
);
CREATE TABLE events (
                        id INT NOT NULL AUTO_INCREMENT,
                        name VARCHAR (100) NOT NULL,
                        date DATETIME NOT NULL,
                        location VARCHAR (100) NOT NULL,
                        authorId INT NOT NULL,
                        categoryId INT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (categoryId) REFERENCES categories (id),
                        FOREIGN KEY (authorId) REFERENCES users (id)
);
CREATE TABLE attendees (
                           eventId INT NOT NULL,
                           userId INT NOT NULL,
                           PRIMARY KEY (eventId, userId),
                           FOREIGN KEY (eventId) REFERENCES events (id),
                           FOREIGN KEY (userId) REFERENCES users (id)
);
CREATE TABLE tasks (
                       id INT NOT NULL AUTO_INCREMENT,
                       name VARCHAR (100) NOT NULL,
                       startDate DATETIME NOT NULL,
                       endDate DATETIME NOT NULL,
                       description VARCHAR (500) NOT NULL,
                       isDone BIT NOT NULL,
                       authorId INT NOT NULL,
                       categoryId INT NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (categoryId) REFERENCES categories (id),
                       FOREIGN KEY (authorId) REFERENCES users (id)
);
CREATE TABLE collaborators (
                               taskId INT NOT NULL,
                               userId INT NOT NULL,
                               PRIMARY KEY (taskId, userId),
                               FOREIGN KEY (taskId) REFERENCES tasks (id),
                               FOREIGN KEY (userId) REFERENCES users (id)
);
CREATE TABLE eventInvites (
                              id INT NOT NULL AUTO_INCREMENT,
                              eventId INT NOT NULL,
                              userId INT NOT NULL,
                              sentDate DATETIME NOT NULL,
                              PRIMARY KEY (id),
                              FOREIGN KEY (eventId) REFERENCES events (id),
                              FOREIGN KEY (userId) REFERENCES users (id)
);
CREATE TABLE taskInvites (
                             id INT NOT NULL AUTO_INCREMENT,
                             taskId INT NOT NULL,
                             userId INT NOT NULL,
                             sentDate DATETIME NOT NULL,
                             PRIMARY KEY (id),
                             FOREIGN KEY (taskId) REFERENCES tasks (id),
                             FOREIGN KEY (userId) REFERENCES users (id)
);
CREATE TABLE notes (
                       id INT NOT NULL AUTO_INCREMENT,
                       authorId INT NOT NULL,
                       taskId INT NOT NULL,
                       date DATETIME NOT NULL,
                       content VARCHAR (500) NOT NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (authorId) REFERENCES users (id),
                       FOREIGN KEY (taskId) REFERENCES tasks (id)
);
