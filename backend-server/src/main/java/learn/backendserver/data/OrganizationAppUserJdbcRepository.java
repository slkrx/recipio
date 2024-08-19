package learn.backendserver.data;

import learn.backendserver.models.OrganizationAppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrganizationAppUserJdbcRepository implements OrganizationAppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrganizationAppUserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrganizationAppUser> findByOrganizationId(int organizationId) {
        final String sql = """
                SELECT
                    organization_id,
                    app_user_id
                FROM organization_app_user
                WHERE organization_id = ?;
                """;
        return jdbcTemplate.query(sql, new OrganizationAppUserMapper(), organizationId);
    }

    @Override
    public boolean create(OrganizationAppUser organizationAppUser) {
        final String sql = """
                INSERT INTO
                    organization_app_user(organization_id, app_user_id)
                VALUES
                    (?, ?);
                """;
        return jdbcTemplate.update(sql, organizationAppUser.getOrganizationId(), organizationAppUser.getAppUserId()) > 0;
    }
}
