package learn.backendserver.data;

import learn.backendserver.models.OrganizationAppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationAppUserMapper implements RowMapper<OrganizationAppUser> {
    @Override
    public OrganizationAppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrganizationAppUser(
                rs.getInt("organization_id"),
                rs.getInt("app_user_id")
        );
    }
}
