package learn.backendserver.data;

import learn.backendserver.TestHelpers;
import learn.backendserver.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RecipeJdbcRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RecipeJdbcRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindRecipeById() {
        Recipe expected = TestHelpers.RECIPE1;
        Recipe actual = repository.findById(2571);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByRecipeIds() {
        List<Recipe> expected = List.of(TestHelpers.RECIPE2, TestHelpers.RECIPE1);
        List<Recipe> actual = repository.findByIds(List.of(2571, 1));
        assertEquals(expected, actual);
    }
    
}