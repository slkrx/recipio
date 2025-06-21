package learn.backendserver.data;

import learn.backendserver.models.Organization;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Repository
public class OrganizationJdbcRepository implements OrganizationRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrganizationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Organization findById(int id) {
        final String sql = """
                SELECT
                    name,
                    owner_id,
                    id
                FROM organization
                WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, new OrganizationMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public List<Organization> findByUsername(String username) {
        final String sql = """
                SELECT
                    o.name,
                    o.owner_id,
                    o.id
                FROM organization o
                INNER JOIN app_user u ON u.app_user_id = o.owner_id
                WHERE u.username = ?;
                """;
        return jdbcTemplate.query(sql, new OrganizationMapper(), username);
    }

    @Override
    public Organization create(Organization organization) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("organization")
                .usingGeneratedKeyColumns("id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("owner_id", organization.getOwnerId());
        args.put("name", organization.getName());

        organization.setId(insert.executeAndReturnKey(args).intValue());

        return organization;
    }

    @Override
    public boolean update(Organization organization) {
        final String sql = """
                UPDATE organization SET
                    name = ?
                WHERE
                    id = ?;
                """;
        return jdbcTemplate.update(sql, organization.getName(), organization.getId()) > 0;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        jdbcTemplate.update("DELETE FROM organization_app_user WHERE organization_id = ?;", id);
        jdbcTemplate.update("DELETE FROM organization_recipe WHERE organization_id = ?;", id);
        return jdbcTemplate.update("DELETE FROM organization WHERE id = ?;", id) > 0;
    }
}
