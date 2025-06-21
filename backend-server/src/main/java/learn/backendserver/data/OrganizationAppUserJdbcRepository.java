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
    public List<OrganizationAppUser> findByUsername(String username) {
        final String sql = """
                SELECT
                    oau.organization_id,
                    oau.app_user_id
                FROM organization_app_user oau
                INNER JOIN app_user au ON au.app_user_id = oau.app_user_id
                WHERE au.username = ?
                """;
        return jdbcTemplate.query(sql, new OrganizationAppUserMapper(), username);
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
