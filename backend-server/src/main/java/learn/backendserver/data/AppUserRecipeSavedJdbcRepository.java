package learn.backendserver.data;

import learn.backendserver.models.AppUser;
import learn.backendserver.models.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class AppUserRecipeSavedJdbcRepository implements AppUserRecipeSavedRepository {
    private final JdbcTemplate jdbcTemplate;

    public AppUserRecipeSavedJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Recipe> findRecipeByUserSaved(String username) {
        Integer userId = jdbcTemplate.query("SELECT app_user_id FROM app_user WHERE username = ?;",
                (ResultSet rs, int rowNum) -> rs.getInt("app_user_id"), username)
                .stream().findFirst().orElse(null);
        if (userId == null) return List.of();

        final String sql = """
                select
                    r.id,
                    r.title,
                    r.categories,
                    r.rating,
                    r.ratings,
                    r.image_url,
                    r.time,
                    r.description,
                    r.ingredients,
                    r.steps,
                    r.url,
                    s.saved_time
                FROM recipe r
                INNER JOIN app_user_recipe_saved s ON s.recipe_id = r.id
                WHERE s.app_user_id = ?;
                """;

        return jdbcTemplate.query(sql, new RecipeMapper(), userId);
    }
}
