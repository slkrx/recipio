package learn.backendserver.data;

import learn.backendserver.TestHelpers;
import learn.backendserver.models.AppUserRecipeCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserRecipeCreatedJdbcRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AppUserRecipeCreatedRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldCreate() {
        assertTrue(repository.create(TestHelpers.USER1.getAppUserId(), TestHelpers.RECIPE1.getId()));
    }

    @Test
    void shouldFindByUsername() {
        List<AppUserRecipeCreated> actual = repository.findByUsername(TestHelpers.USER1.getUsername());
        assertEquals(TestHelpers.RECIPE2.getId(), actual.get(0).getRecipeId());
    }
}