package learn.backendserver.data;

import learn.backendserver.TestHelpers;
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
    void shouldFindRecipesByUsername() {
        List<Recipe> actual = repository.findRecipeByUserSaved(TestHelpers.USER1.getUsername());
        List<Recipe> expected = List.of(TestHelpers.RECIPE2, TestHelpers.RECIPE1);
        assertEquals(expected, actual);
    }
}