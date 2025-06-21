package learn.backendserver.data;

import learn.backendserver.TestHelpers;
import learn.backendserver.models.AppUserRecipeSaved;
import learn.backendserver.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserRecipeSavedJdbcRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AppUserRecipeSavedRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindAppUserRecipesSavedByUsername() {
        List<AppUserRecipeSaved> actual = repository.findByUsername(TestHelpers.USER1.getUsername());
        assertEquals(1, actual.get(0).getRecipeSavedId());
        assertEquals(2571, actual.get(1).getRecipeSavedId());
    }

    @Test
    void shouldCreateAppUserRecipeSaved() {
        assertTrue(repository.create(TestHelpers.USER2.getAppUserId(), TestHelpers.RECIPE1.getId()));
    }

    @Test
    void shouldDeleteAppUserRecipeSaved() {
        assertTrue(repository.delete(TestHelpers.USER1.getAppUserId(), TestHelpers.RECIPE1.getId()));
    }
}