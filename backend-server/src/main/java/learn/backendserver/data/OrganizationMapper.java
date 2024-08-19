package learn.backendserver.data;


import learn.backendserver.models.Organization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationMapper implements RowMapper<Organization> {
    @Override
    public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Organization(
                rs.getInt("id"),
                rs.getInt("owner_id"),
                rs.getString("name")
        );
    }
}
