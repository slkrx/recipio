package learn.backendserver.data;

import learn.backendserver.TestHelpers;
import learn.backendserver.models.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrganizationJdbcRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    OrganizationRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldCreateOrganization() {
        Organization arg = new Organization(0, TestHelpers.USER1.getAppUserId(), "test");
        Organization actual = repository.create(arg);
        assertEquals(2, actual.getId());
    }

    @Test
    void shouldFindById() {
        Organization arg = new Organization(0, TestHelpers.USER1.getAppUserId(), "test");
        Organization expected = repository.create(arg);
        assertEquals(expected, repository.findById(2));
    }
}