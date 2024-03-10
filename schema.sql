DROP TABLE IF EXISTS project
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
