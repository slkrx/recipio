-- id,title,categories,rating,ratings,image url,time,description,ingredients,steps,url
DROP DATABASE IF EXISTS recipe_search_test;
CREATE DATABASE recipe_search_test;

use recipe_search_test;

CREATE TABLE recipe (
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(100),
    categories  VARCHAR(500),
    rating      FLOAT(1),
    ratings     SMALLINT UNSIGNED,
    image_url   VARCHAR(5000),
    time        VARCHAR(100),
    description VARCHAR(5000),
    ingredients TEXT,
    steps       TEXT,
    url         VARCHAR(5000)
);

CREATE TABLE app_user (
    app_user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(2048) NOT NULL,
    enabled BIT NOT NULL DEFAULT(1)
);

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

CREATE TABLE app_role (
    app_role_id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE app_user_role (
    app_user_id INT NOT NULL,
    app_role_id INT NOT NULL,
    CONSTRAINT pk_app_user_role
        PRIMARY KEY (app_user_id, app_role_id),
    CONSTRAINT fk_app_user_role_user_id
        FOREIGN KEY (app_user_id)
        REFERENCES app_user(app_user_id),
    CONSTRAINT fk_app_user_role_role_id
        FOREIGN KEY (app_role_id)
        REFERENCES app_role(app_role_id)
);

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

CREATE TABLE organization (
    id INT PRIMARY KEY AUTO_INCREMENT,
    owner_id INT,
    name VARCHAR(500),
    CONSTRAINT fk_owner_id_app_user_id
        FOREIGN KEY (owner_id)
        REFERENCES app_user(app_user_id)
);

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

CREATE TABLE organization_recipe (
    organization_id INT,
    recipe_id INT UNSIGNED,
    CONSTRAINT fk_organization_recipe_organization_id
        FOREIGN KEY (organization_id)
        REFERENCES organization(id),
    CONSTRAINT fk_organization_recipe_recipe_id
        FOREIGN KEY (recipe_id)
        REFERENCES recipe(id)
);

INSERT INTO app_role (`name`) VALUES
    ('USER'),
    ('ADMIN');

DELIMITER //
CREATE PROCEDURE set_known_good_state()
BEGIN
    DELETE FROM app_user_role;
    ALTER TABLE app_user_role AUTO_INCREMENT=1;
    DELETE FROM app_user_recipe_saved;
    ALTER TABLE app_user_recipe_saved AUTO_INCREMENT=1;
    DELETE FROM app_user_recipe_created;
    ALTER TABLE app_user_recipe_created AUTO_INCREMENT=1;

    DELETE FROM organization_app_user;
    ALTER TABLE organization_app_user AUTO_INCREMENT=1;
    DELETE FROM organization_recipe;
    ALTER TABLE organization_recipe AUTO_INCREMENT=1;

    DELETE FROM organization;
    ALTER TABLE organization AUTO_INCREMENT=1;
    DELETE FROM app_user;
    ALTER TABLE app_user AUTO_INCREMENT=1;
    DELETE FROM recipe;
    ALTER TABLE recipe AUTO_INCREMENT=1;

    INSERT INTO recipe(
        id,
        title,
        categories,
        rating,
        ratings,
        image_url,
        time,
        description,
        ingredients,
        steps,
        url)
    VALUES
        (1,
        "Scrumpets",
        "Bread,Quick Bread Scone Recipes",
        0,
        0,
        "https://www.allrecipes.com/thmb/XP5yE6ZH_K2cSZW4KPFeNZ8-1dA=/600x400/filters:no_upscale():max_bytes(150000):strip_icc()/recipe_placeholder_image-29d3b402646a47228dbd7786375d9d5e.png",
        "45 mins",
        "A scrumpet is a cross between a scone and a crumpet. These easy-to-make biscuits will be the star of any meal, and you'll love that you don't need to heat up an oven. I searched for years before finding a bacon and cheese version of this recipe, which I modified to make this plain version that I love. When I finally found it, I was amazed at how easy these are to make!",
        "4 cups all-purpose flour, sifted|2 tablespoons white sugar|4 teaspoons baking powder|1 teaspoon salt|1 cup milk|3 eggs|½ cup butter, melted|1 teaspoon butter, or as needed (Optional)",
        "Combine flour, sugar, baking powder, and salt in a bowl; mix well.|Beat milk, eggs, and 1/2 cup melted butter together in another bowl; stir into flour mixture, with just a few strokes. Handle dough as little as possible.|Scrape dough onto a generously floured surface; dip your fingers in flour. Pat dough into a circle, about 1/2-inch thick. Cut into 4-inch rounds to form 18 scrumpets.|Melt enough remaining butter over medium-high heat in a large skillet until very lightly greased. Fill skillet with scrumpets, cooking in batches, and immediately reduce heat to medium. Cook 5 minutes; flip reduce heat again, and cook 5 minutes more. Cook remaining scrumpets, in batches, in lightly buttered skillet. Serve warm.|Cook's Notes:|Substitute 1 cup milk with 3/4 cup water if desired.|If you are like me and put the butter in the microwave while you are measuring out dry ingredients you may want to crack the eggs directly in the dry ingredients to avoid cooking them in the hot melted butter.",
        "https://www.allrecipes.com/recipe/268815/scrumpets/"),
        (2,
        "Turkey Stock",
        "Soups, Stews and Chili Broth and Stock Recipes",
        4.4,
        18,
        "https://www.allrecipes.com/thmb/1cRyH7S_GpiLN4pPI3tR5-7BdYI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/1990237-turkey-stock-Rebekah-Rose-Hills-4x3-1-7486363818bd44d3a89ece19391d9500.jpg",
        "1 hr 30 mins",
        "Make a great turkey stock with the carcass and then create wonderful soups. This recipe is the basic stock recipe my great-grandma used and her mother before her. Once you have a basic stock you can add leftovers, use it to cook rice, or make a soup with dumplings — the uses are endless! Hot stock with a few veggies and alphabet pasta is a great after-school warm-up. I have soup made in the fridge so hubby can snack on it instead of junk.",
        "1 turkey carcass|3 onions, coarsely chopped|1 pound carrots, coarsely chopped|1 bunch celery, coarsely chopped|1 green bell pepper, coarsely chopped|3 cloves garlic|4 cubes chicken bouillon|1 tablespoon whole black peppercorns, or to taste|3 bay leaves|water to cover",
        "Combine turkey carcass, onions, carrots, celery, green bell pepper, garlic, chicken bouillon cubes, peppercorns, and bay leaves in a stockpot; pour in enough water to cover. Bring mixture to a boil, reduce heat, and simmer until flavors blend, about 1 hour. Remove stockpot from heat and let sit for 15 minutes.|Strain stock through a cheesecloth and discard solids.|Cook’s Note|I keep ends of root veggies and tops of bell peppers to use in meat stock when I make soup from leftover Sunday roast. Leaving the skin on the onion will darken stock and add more taste. I use roasted peppers and whole roasted garlic.",
        "https://www.allrecipes.com/recipe/235243/turkey-stock/");


    -- passwords are set to "P@ssw0rd!"
    INSERT INTO app_user (username, password_hash, enabled)
        VALUES
        ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
        ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
        ('test@test.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

    INSERT INTO app_user_role
        VALUES
        (1, 2),
        (2, 1);
    
    INSERT INTO app_user_recipe_saved (app_user_id, recipe_id)
        VALUES
        (1, 1);
    INSERT INTO app_user_recipe_saved (app_user_id, recipe_id, saved_time)
        VALUES
        (1, 2, CURRENT_TIMESTAMP + INTERVAL '10' SECOND);

    INSERT INTO app_user_recipe_created (app_user_id, recipe_id)
        VALUES
        (1, 1);
    INSERT INTO app_user_recipe_created (app_user_id, recipe_id, created_time)
        VALUES
        (1, 2, CURRENT_TIMESTAMP + INTERVAL '10' SECOND);

    INSERT INTO organization (name, owner_id)
        VALUES
        ("test", 1);

    INSERT INTO organization_app_user (organization_id, app_user_id)
        VALUES
        (1, 2);

    INSERT INTO organization_recipe (organization_id, recipe_id)
        VALUES
        (1, 1);

END//
DELIMITER ;