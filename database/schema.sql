-- id,title,categories,rating,ratings,image url,time,description,ingredients,steps,url
DROP DATABASE IF EXISTS recipe_search;
CREATE DATABASE recipe_search;

use recipe_search;

CREATE TABLE recipe (
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(500),
    categories  VARCHAR(500),
    rating      FLOAT(1),
    ratings     SMALLINT UNSIGNED,
    image_url   VARCHAR(5000),
    time        VARCHAR(100),
    description TEXT,
    ingredients TEXT,
    steps       TEXT,
    url         VARCHAR(5000)
);