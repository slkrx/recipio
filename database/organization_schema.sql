USE recipe_search;

DROP TABLE IF EXISTS organization;

CREATE TABLE organization (
    id INT PRIMARY KEY AUTO_INCREMENT,
    owner_id INT,
    name VARCHAR(500),
    CONSTRAINT fk_owner_id_app_user_id
        FOREIGN KEY (owner_id)
        REFERENCES app_user(app_user_id)
);