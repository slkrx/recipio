package learn.backendserver.domain;

import learn.backendserver.TestHelpers;
import learn.backendserver.data.RecipeRepository;
import learn.backendserver.models.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RecipeServiceTest {

    @Autowired
    RecipeService service;

    @MockBean
    RecipeRepository repository;

    @Test
    void shouldFindById() {
        when(repository.findById(TestHelpers.RECIPE1.getId())).thenReturn(TestHelpers.RECIPE1);
        assertEquals(TestHelpers.RECIPE1, service.findById(TestHelpers.RECIPE1.getId()));
    }

    @Test
    void shouldCreate() {
        Recipe newRecipeWithId = new Recipe(TestHelpers.NEW_RECIPE);
        newRecipeWithId.setId(3);
        when(repository.create(TestHelpers.NEW_RECIPE)).thenReturn(newRecipeWithId);
        assertEquals(newRecipeWithId, service.create(TestHelpers.NEW_RECIPE));
    }

    @Test
    void update() {
        Recipe recipe1 = TestHelpers.RECIPE1;
        when(repository.update(recipe1)).thenReturn(true);
        assertTrue(service.update(recipe1));
    }

    @Test
    void delete() {
        when(repository.delete(TestHelpers.RECIPE1.getId())).thenReturn(true);
        assertTrue(service.delete(TestHelpers.RECIPE1.getId()));
    }

}