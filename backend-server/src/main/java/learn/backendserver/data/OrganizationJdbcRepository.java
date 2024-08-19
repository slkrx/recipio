package learn.backendserver.data;

import learn.backendserver.models.Organization;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

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
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
