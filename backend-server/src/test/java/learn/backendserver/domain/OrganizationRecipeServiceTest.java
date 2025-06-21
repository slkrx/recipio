package learn.backendserver.domain;

import learn.backendserver.TestHelpers;
import learn.backendserver.data.OrganizationRecipeRepository;
import learn.backendserver.models.OrganizationRecipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrganizationRecipeServiceTest {
    @Autowired
    OrganizationRecipeService service;

    @MockBean
    OrganizationRecipeRepository repository;

    @Test
    void shouldCreate() {
        OrganizationRecipe arg = new OrganizationRecipe(TestHelpers.ORGANIZATION1.getId(), TestHelpers.RECIPE1.getId());
        when(repository.create(arg)).thenReturn(true);
        assertTrue(service.create(arg));
    }

    @Test
    void findByOrganizationId() {
        OrganizationRecipe expected = new OrganizationRecipe(TestHelpers.ORGANIZATION1.getId(),
                TestHelpers.RECIPE1.getId());
        when(repository.findByOrganizationId(TestHelpers.ORGANIZATION1.getId())).thenReturn(List.of(expected));
        assertEquals(List.of(expected), service.findByOrganizationId(TestHelpers.ORGANIZATION1.getId()));
    }
}