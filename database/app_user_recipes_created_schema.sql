use recipe_search;

DROP TABLE IF EXISTS app_user_recipe_created;

CREATE TABLE app_user_recipe_created (
    app_user_id INT,
    recipe_id INT UNSIGNED,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_user_recipe_created_app_user_id
        FOREIGN KEY (app_user_id)
        REFERENCES app_user(app_user_id),
    CONSTRAINT fk_app_user_recipe_created_recipe_id
        FOREIGN KEY (recipe_id)
        REFERENCES recipe(id)
);