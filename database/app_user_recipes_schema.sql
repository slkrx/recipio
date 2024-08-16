use recipe_search;

DROP TABLE IF EXISTS app_user_recipe_saved;

CREATE TABLE app_user_recipe_saved (
    app_user_id INT,
    recipe_id INT UNSIGNED,
    saved_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_user_recipe_saved_app_user_id
        FOREIGN KEY (app_user_id)
        REFERENCES app_user(app_user_id),
    CONSTRAINT fk_app_user_recipe_saved_recipe_id
        FOREIGN KEY (recipe_id)
        REFERENCES recipe(id)
);