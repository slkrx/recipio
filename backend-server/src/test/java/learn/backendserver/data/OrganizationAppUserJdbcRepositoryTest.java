package learn.backendserver.data;

import learn.backendserver.models.OrganizationAppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrganizationAppUserJdbcRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    OrganizationAppUserRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindByOrganizationId() {
        List<OrganizationAppUser> expected = List.of(new OrganizationAppUser(1, 2));
        List<OrganizationAppUser> actual = repository.findByOrganizationId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldCreate() {
        assertTrue(repository.create(new OrganizationAppUser(1, 3)));
    }

}