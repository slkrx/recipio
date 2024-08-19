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
        Recipe actual = repository.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByRecipeIds() {
        List<Recipe> expected = List.of(TestHelpers.RECIPE1, TestHelpers.RECIPE2);
        List<Recipe> actual = repository.findByIds(List.of(1, 2));
        assertEquals(expected, actual);
    }

    @Test
    void shouldCreateRecipe() {
        Recipe actual = repository.create(TestHelpers.NEW_RECIPE);
        assertEquals(3, actual.getId());
    }

    @Test
    void shouldUpdateRecipe() {
        Recipe arg = repository.findById(1);
        arg.setTitle("new title");
        assertTrue(repository.update(arg));
        assertEquals("new title", repository.findById(1).getTitle());
    }

    @Test
    void shouldDeleteRecipe() {
        assertTrue(repository.delete(1));
        assertNull(repository.findById(1));
    }
    
}