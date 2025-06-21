package learn.backendserver.data;

import learn.backendserver.models.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Repository
public class RecipeJdbcRepository implements RecipeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RecipeJdbcRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Recipe findById(int id) {
        final String sql = """
                SELECT
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
                    url
                FROM recipe
                WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, new RecipeMapper(), id)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Recipe> findByIds(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);

        final String sql = """
                SELECT
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
                    url
                FROM recipe
                WHERE id IN (:ids);
                """;

        return namedParameterJdbcTemplate.query(sql, parameters, new RecipeMapper());
    }

    @Override
    public Recipe create(Recipe recipe) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("recipe")
                .usingGeneratedKeyColumns("id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("title", recipe.getTitle());
        args.put("categories", String.join(",", recipe.getCategories()));
        args.put("rating", 0.0);
        args.put("ratings", 0);
        args.put("image_url", recipe.getImageUrl());
        args.put("time", recipe.getTime());
        args.put("description", recipe.getDescription());
        args.put("ingredients", String.join(",", recipe.getIngredients()));
        args.put("steps", String.join(",", recipe.getSteps()));
        args.put("url", "");

        recipe.setId(insert.executeAndReturnKey(args).intValue());
        return recipe;
    }

    @Override
    public boolean update(Recipe recipe) {
        final String sql = """
                UPDATE recipe SET
                    title = ?,
                    categories = ?,
                    rating = ?,
                    ratings = ?,
                    image_url = ?,
                    time = ?,
                    description = ?,
                    ingredients = ?,
                    steps = ?,
                    url = ?
                WHERE id = ?;
                """;

        return jdbcTemplate.update(sql,
            recipe.getTitle(),
            String.join(",", recipe.getCategories()),
            recipe.getRating(),
            recipe.getRatings(),
            recipe.getImageUrl(),
            recipe.getTime(),
            recipe.getDescription(),
            String.join(",", recipe.getIngredients()),
            String.join(",", recipe.getSteps()),
            recipe.getUrl(),
            recipe.getId()
        ) > 0;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        jdbcTemplate.update("DELETE FROM app_user_recipe_created WHERE recipe_id = ?;", id);
        jdbcTemplate.update("DELETE FROM app_user_recipe_saved WHERE recipe_id = ?;", id);
        return jdbcTemplate.update("DELETE FROM recipe WHERE id = ?;", id) > 0;
    }
}
