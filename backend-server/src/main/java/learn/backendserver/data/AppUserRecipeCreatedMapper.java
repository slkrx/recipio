package learn.backendserver.data;

import learn.backendserver.models.AppUserRecipeCreated;
import learn.backendserver.models.AppUserRecipeSaved;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserRecipeCreatedMapper implements RowMapper<AppUserRecipeCreated> {
    @Override
    public AppUserRecipeCreated mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AppUserRecipeCreated(
                rs.getInt("app_user_id"),
                rs.getInt("recipe_id"),
                rs.getTimestamp("created_time")
        );
    }
}
