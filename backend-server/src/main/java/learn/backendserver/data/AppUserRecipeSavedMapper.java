package learn.backendserver.data;

import learn.backendserver.models.AppUserRecipeSaved;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserRecipeSavedMapper implements RowMapper<AppUserRecipeSaved> {
    @Override
    public AppUserRecipeSaved mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AppUserRecipeSaved(
                rs.getInt("app_user_id"),
                rs.getInt("recipe_id"),
                rs.getTimestamp("saved_time")
        );
    }
}
