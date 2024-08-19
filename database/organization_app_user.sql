USE recipe_search;

DROP TABLE IF EXISTS organization_app_user;

CREATE TABLE organization_app_user (
    organization_id INT,
    app_user_id INT,
    CONSTRAINT fk_organization_app_user_organization_id
        FOREIGN KEY (organization_id)
        REFERENCES organization(id),
    CONSTRAINT fk_organization_app_user_app_user_id
        FOREIGN KEY (app_user_id)
        REFERENCES app_user(app_user_id)
);