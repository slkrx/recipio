package learn.backendserver.data;

import learn.backendserver.models.AppUserRecipeCreated;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppUserRecipeCreatedJdbcRepository implements AppUserRecipeCreatedRepository {
    private final JdbcTemplate jdbcTemplate;

    public AppUserRecipeCreatedJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(int appUserId, int recipeId) {
        String sql = """
                INSERT INTO
                    app_user_recipe_created(app_user_id, recipe_id)
                VALUES
                    (?, ?);
                """;
        return jdbcTemplate.update(sql, appUserId, recipeId) > 0;
    }

    @Override
    public List<AppUserRecipeCreated> findByUsername(String username) {
        final String sql = """
                select
                    c.app_user_id,
                    c.recipe_id,
                    c.created_time
                FROM app_user_recipe_created c
                INNER JOIN app_user u ON u.app_user_id = c.app_user_id
                WHERE u.username = ?
                ORDER BY c.created_time DESC;
                """;

        return jdbcTemplate.query(sql, new AppUserRecipeCreatedMapper(), username);
    }

}
