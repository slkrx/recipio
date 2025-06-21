package learn.backendserver.domain;

import learn.backendserver.TestHelpers;
import learn.backendserver.data.AppUserRecipeCreatedRepository;
import learn.backendserver.models.AppUserRecipeCreated;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserRecipeCreatedServiceTest {

    @Autowired
    AppUserRecipeCreatedService service;

    @MockBean
    AppUserRecipeCreatedRepository repository;

    @Test
    void create() {
        when(repository.create(1,1)).thenReturn(true);
        assertTrue(service.create(1,1));
    }

    @Test
    void findByUsername() {
        List<AppUserRecipeCreated> expected = List.of(
                new AppUserRecipeCreated(1,1, new Timestamp(0)));
        when(repository.findByUsername(TestHelpers.USER1.getUsername())).thenReturn(expected);
        assertEquals(expected, service.findByUsername(TestHelpers.USER1.getUsername()));
    }
}