package learn.backendserver.data;

import learn.backendserver.models.Recipe;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeMapper implements RowMapper<Recipe> {

    @Override
    public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setId(rs.getInt("id"));
        recipe.setTitle(rs.getString("title"));
        String[] categories = rs.getString("categories").split(",");
        recipe.setCategories(categories);
        recipe.setRating(rs.getFloat("rating"));
        recipe.setRatings(rs.getShort("ratings"));
        recipe.setImageUrl(rs.getString("image_url"));
        recipe.setTime(rs.getString("time"));
        recipe.setDescription(rs.getString("description"));
        recipe.setIngredients(rs.getString("ingredients").split("\\|"));
        recipe.setSteps(rs.getString("steps").split("\\|"));
        recipe.setUrl(rs.getString("url"));

        return recipe;
    }

}
