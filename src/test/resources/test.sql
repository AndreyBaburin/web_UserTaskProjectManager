CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255));


CREATE TABLE tasks (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255),
                       user_id INT,
                       FOREIGN KEY (user_id) REFERENCES users(id));

CREATE TABLE project (
                         id SERIAL PRIMARY KEY,
                         definition VARCHAR(255));

CREATE TABLE task_project (
                              task_id INT,
                              project_id INT,
                              PRIMARY KEY (task_id, project_id),
                              FOREIGN KEY (task_id) REFERENCES tasks(id),
                              FOREIGN KEY (project_id) REFERENCES project(id));

INSERT INTO users (name, email) VALUES
                                    ('User1', 'user1@test.com'),
                                    ('User2', 'user2@test.com'),
                                    ('User3', 'user3@test.com'),
                                    ('User4', 'user4@test.com'),
                                    ('User5', 'user5@test.com'),
                                    ('User6', 'user6@test.com');


INSERT INTO tasks (title, user_id) VALUES
                                       ('Task1', 1),
                                       ('Task2', 2),
                                       ('Task3', 3),
                                       ('Task4', 4),
                                       ('Task5', 5),
                                       ('Task6', 6);


INSERT INTO project (definition) VALUES
                                     ('Project1'),
                                     ('Project2'),
                                     ('Project3');


INSERT INTO task_project (task_id, project_id) VALUES
                                                   (1, 1),
                                                   (2, 1),
                                                   (3, 2),
                                                   (4, 2),
                                                   (5, 3),
                                                   (6, 3);