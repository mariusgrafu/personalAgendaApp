CREATE DATABASE IF NOT EXISTS personalagendaapp;

CREATE TABLE IF NOT EXISTS users
(
    id       INT NOT NULL auto_increment,
    name     VARCHAR (100) NOT NULL,
    email    VARCHAR (100) NOT NULL,
    password VARCHAR (100) NOT NULL,
    date     DATETIME NOT NULL,
    PRIMARY KEY ( id )
    );

CREATE TABLE IF NOT EXISTS categories
(
    id   INT NOT NULL auto_increment,
    name VARCHAR (100) NOT NULL,
    PRIMARY KEY ( id )
    );

CREATE TABLE IF NOT EXISTS events
(
    id         INT NOT NULL auto_increment,
    name       VARCHAR (100) NOT NULL,
    date       DATETIME NOT NULL,
    location   VARCHAR (100) NOT NULL,
    authorid   INT NOT NULL,
    categoryid INT NULL,
    PRIMARY KEY ( id ),
    FOREIGN KEY ( categoryid ) REFERENCES categories ( id ),
    FOREIGN KEY ( authorid ) REFERENCES users ( id )
    );

CREATE TABLE IF NOT EXISTS attendees
(
    eventid INT NOT NULL,
    userid  INT NOT NULL,
    PRIMARY KEY ( eventid, userid ),
    FOREIGN KEY ( eventid ) REFERENCES events ( id ),
    FOREIGN KEY ( userid ) REFERENCES users ( id )
    );

CREATE TABLE IF NOT EXISTS tasks
(
    id          INT NOT NULL auto_increment,
    name        VARCHAR (100) NOT NULL,
    startdate   DATETIME NOT NULL,
    enddate     DATETIME NOT NULL,
    description VARCHAR (500) NOT NULL,
    isdone      BIT NOT NULL,
    authorid    INT NOT NULL,
    categoryid  INT NULL,
    PRIMARY KEY ( id ),
    FOREIGN KEY ( categoryid ) REFERENCES categories ( id ),
    FOREIGN KEY ( authorid ) REFERENCES users ( id )
    );

CREATE TABLE IF NOT EXISTS collaborators
(
    taskid INT NOT NULL,
    userid INT NOT NULL,
    PRIMARY KEY ( taskid, userid ),
    FOREIGN KEY ( taskid ) REFERENCES tasks ( id ),
    FOREIGN KEY ( userid ) REFERENCES users ( id )
    );

CREATE TABLE IF NOT EXISTS eventinvites
(
    id       INT NOT NULL auto_increment,
    eventid  INT NOT NULL,
    userid   INT NOT NULL,
    sentdate DATETIME NOT NULL,
    PRIMARY KEY ( id ),
    FOREIGN KEY ( eventid ) REFERENCES events ( id ),
    FOREIGN KEY ( userid ) REFERENCES users ( id )
    );

CREATE TABLE IF NOT EXISTS taskinvites
(
    id       INT NOT NULL auto_increment,
    taskid   INT NOT NULL,
    userid   INT NOT NULL,
    sentdate DATETIME NOT NULL,
    PRIMARY KEY ( id ),
    FOREIGN KEY ( taskid ) REFERENCES tasks ( id ),
    FOREIGN KEY ( userid ) REFERENCES users ( id )
    );

CREATE TABLE IF NOT EXISTS notes
(
    id       INT NOT NULL auto_increment,
    authorid INT NOT NULL,
    taskid   INT NOT NULL,
    date     DATETIME NOT NULL,
    content  VARCHAR (500) NOT NULL,
    PRIMARY KEY ( id ),
    FOREIGN KEY ( authorid ) REFERENCES users ( id ),
    FOREIGN KEY ( taskid ) REFERENCES tasks ( id )
    );