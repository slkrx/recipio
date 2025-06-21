package learn.backendserver.data;

import learn.backendserver.models.AppUser;
import learn.backendserver.models.AppUserRecipeSaved;
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
    public List<AppUserRecipeSaved> findByUsername(String username) {
        final String sql = """
                select
                    s.app_user_id,
                    s.recipe_id,
                    s.saved_time
                FROM app_user_recipe_saved s
                INNER JOIN app_user u ON u.app_user_id = s.app_user_id
                WHERE u.username = ?
                ORDER BY s.saved_time DESC;
                """;

        return jdbcTemplate.query(sql, new AppUserRecipeSavedMapper(), username);
    }

    @Override
    public boolean create(int appUserId, int recipeId) {
        String sql = """
                INSERT INTO
                    app_user_recipe_saved(app_user_id, recipe_id)
                VALUES
                    (?, ?);
                """;
        return jdbcTemplate.update(sql, appUserId, recipeId) > 0;
    }

    @Override
    public boolean delete(int appUserId, int recipeId) {
        String sql = "delete from app_user_recipe_saved where app_user_id = ? and recipe_id = ?;";
        return jdbcTemplate.update(sql, appUserId, recipeId) > 0;
    }
}
