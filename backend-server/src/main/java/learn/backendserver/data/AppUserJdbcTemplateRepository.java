package learn.backendserver.data;

import learn.backendserver.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository{

    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AppUser findByUsername(String username) {
        List<String> roles = getRolesByUsername(username);

        final String sql = """
                select
                    app_user_id,
                    username,
                    password_hash,
                    enabled
                from app_user
                where username = ?;
                """;

        return jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public AppUser findById(int appUserId) {
        final String sql = """
                select
                    app_user_id,
                    username,
                    password_hash,
                    enabled
                from app_user
                where app_user_id = ?;
                """;

        return jdbcTemplate.query(sql, new AppUserMapper(List.of("USER")), appUserId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public AppUser create(AppUser user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("app_user")
                .usingColumns("username", "password_hash", "enabled")
                .usingGeneratedKeyColumns("app_user_id");

        Map<String, Object> args = Map.of(
                "username", user.getUsername(),
                "password_hash", user.getPassword(),
                "enabled", user.isEnabled()
        );

        user.setAppUserId(insert.executeAndReturnKey(args).intValue());

        if (user.getAppUserId() <= 0) {
            return null;
        }

        updateRoles(user);

        return user;
    }

    @Override
    public boolean update(AppUser user) {
        final String sql = """
            update app_user set
                username = ?,
                enabled = ?,
                where app_user_id = ?;
            """;

        int rowsAffected = jdbcTemplate.update(sql,
                user.getUsername(), user.isEnabled(), user.getAppUserId());

        if (rowsAffected <= 0) {
            return false;
        }

        updateRoles(user);

        return true;
    }

    private void updateRoles(AppUser user) {
        // delete all roles, then re-add
        jdbcTemplate.update("delete from app_user_role where app_user_id = ?;", user.getAppUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null) {
            return;
        }

        for (GrantedAuthority role : authorities) {
            String sql = "insert into app_user_role (app_user_id, app_role_id) "
                    + "select ?, app_role_id from app_role where `name` = ?;";
            jdbcTemplate.update(sql, user.getAppUserId(), role.getAuthority());
        }
    }

    private List<String> getRolesByUsername(String username) {
        final String sql = """
               select r.name
               from app_user_role ur
               inner join app_role r on ur.app_role_id = r.app_role_id
               inner join app_user au on ur.app_user_id = au.app_user_id
               where au.username = ?;
               """;
        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("name"), username);
    }
}