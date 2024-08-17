package learn.backendserver;

import learn.backendserver.models.AppUser;
import learn.backendserver.models.Recipe;

import java.util.List;

public class TestHelpers {

    public static Recipe RECIPE1 = new Recipe(
            1,
            "Scrumpets",
            new String[]{"Bread","Quick Bread Scone Recipes"},
            (float)0,
            (short)0,
            "https://www.allrecipes.com/thmb/XP5yE6ZH_K2cSZW4KPFeNZ8-1dA=/600x400/filters:no_upscale():max_bytes(150000):strip_icc()/recipe_placeholder_image-29d3b402646a47228dbd7786375d9d5e.png",
            "45 mins",
            "A scrumpet is a cross between a scone and a crumpet. These easy-to-make biscuits will be the star of any meal, and you'll love that you don't need to heat up an oven. I searched for years before finding a bacon and cheese version of this recipe, which I modified to make this plain version that I love. When I finally found it, I was amazed at how easy these are to make!",
            new String[]{"4 cups all-purpose flour, sifted", "2 tablespoons white sugar", "4 teaspoons baking powder", "1 teaspoon salt", "1 cup milk", "3 eggs", "½ cup butter, melted", "1 teaspoon butter, or as needed (Optional)"},
            new String[]{"Combine flour, sugar, baking powder, and salt in a bowl; mix well.", "Beat milk, eggs, and 1/2 cup melted butter together in another bowl; stir into flour mixture, with just a few strokes. Handle dough as little as possible.", "Scrape dough onto a generously floured surface; dip your fingers in flour. Pat dough into a circle, about 1/2-inch thick. Cut into 4-inch rounds to form 18 scrumpets.", "Melt enough remaining butter over medium-high heat in a large skillet until very lightly greased. Fill skillet with scrumpets, cooking in batches, and immediately reduce heat to medium. Cook 5 minutes; flip reduce heat again, and cook 5 minutes more. Cook remaining scrumpets, in batches, in lightly buttered skillet. Serve warm.", "Cook's Notes:", "Substitute 1 cup milk with 3/4 cup water if desired.", "If you are like me and put the butter in the microwave while you are measuring out dry ingredients you may want to crack the eggs directly in the dry ingredients to avoid cooking them in the hot melted butter."},
            "https://www.allrecipes.com/recipe/268815/scrumpets/"
    );
    public static Recipe RECIPE2 = new Recipe(
            2,
            "Turkey Stock",
            new String[]{"Soups",  " Stews and Chili Broth and Stock Recipes"},
            (float)4.4,
            (short)18,
            "https://www.allrecipes.com/thmb/1cRyH7S_GpiLN4pPI3tR5-7BdYI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/1990237-turkey-stock-Rebekah-Rose-Hills-4x3-1-7486363818bd44d3a89ece19391d9500.jpg",
            "1 hr 30 mins",
            "Make a great turkey stock with the carcass and then create wonderful soups. This recipe is the basic stock recipe my great-grandma used and her mother before her. Once you have a basic stock you can add leftovers, use it to cook rice, or make a soup with dumplings — the uses are endless! Hot stock with a few veggies and alphabet pasta is a great after-school warm-up. I have soup made in the fridge so hubby can snack on it instead of junk.",
            new String[] {"1 turkey carcass", "3 onions, coarsely chopped", "1 pound carrots, coarsely chopped", "1 bunch celery, coarsely chopped", "1 green bell pepper, coarsely chopped", "3 cloves garlic", "4 cubes chicken bouillon", "1 tablespoon whole black peppercorns, or to taste", "3 bay leaves", "water to cover"},
            new String[] {"Combine turkey carcass, onions, carrots, celery, green bell pepper, garlic, chicken bouillon cubes, peppercorns, and bay leaves in a stockpot; pour in enough water to cover. Bring mixture to a boil, reduce heat, and simmer until flavors blend, about 1 hour. Remove stockpot from heat and let sit for 15 minutes.", "Strain stock through a cheesecloth and discard solids.", "Cook’s Note", "I keep ends of root veggies and tops of bell peppers to use in meat stock when I make soup from leftover Sunday roast. Leaving the skin on the onion will darken stock and add more taste. I use roasted peppers and whole roasted garlic."},
            "https://www.allrecipes.com/recipe/235243/turkey-stock/"
    );
    public static Recipe NEW_RECIPE = new Recipe(
            0,
            "Turkey Stock",
            new String[]{"Soups",  " Stews and Chili Broth and Stock Recipes"},
            (float)4.4,
            (short)18,
            "https://www.allrecipes.com/thmb/1cRyH7S_GpiLN4pPI3tR5-7BdYI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/1990237-turkey-stock-Rebekah-Rose-Hills-4x3-1-7486363818bd44d3a89ece19391d9500.jpg",
            "1 hr 30 mins",
            "Make a great turkey stock with the carcass and then create wonderful soups. This recipe is the basic stock recipe my great-grandma used and her mother before her. Once you have a basic stock you can add leftovers, use it to cook rice, or make a soup with dumplings — the uses are endless! Hot stock with a few veggies and alphabet pasta is a great after-school warm-up. I have soup made in the fridge so hubby can snack on it instead of junk.",
            new String[] {"1 turkey carcass", "3 onions, coarsely chopped", "1 pound carrots, coarsely chopped", "1 bunch celery, coarsely chopped", "1 green bell pepper, coarsely chopped", "3 cloves garlic", "4 cubes chicken bouillon", "1 tablespoon whole black peppercorns, or to taste", "3 bay leaves", "water to cover"},
            new String[] {"Combine turkey carcass, onions, carrots, celery, green bell pepper, garlic, chicken bouillon cubes, peppercorns, and bay leaves in a stockpot; pour in enough water to cover. Bring mixture to a boil, reduce heat, and simmer until flavors blend, about 1 hour. Remove stockpot from heat and let sit for 15 minutes.", "Strain stock through a cheesecloth and discard solids.", "Cook’s Note", "I keep ends of root veggies and tops of bell peppers to use in meat stock when I make soup from leftover Sunday roast. Leaving the skin on the onion will darken stock and add more taste. I use roasted peppers and whole roasted garlic."},
            "https://www.allrecipes.com/recipe/235243/turkey-stock/"
    );
    public static AppUser USER1 = new AppUser(
            1,
            "john@smith.com",
            "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",
            true,
            List.of()
    );
    public static AppUser USER2 = new AppUser(
            2,
            "sally@jones.com",
            "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",
            true,
            List.of()
    );
}
