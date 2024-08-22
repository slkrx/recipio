package learn.backendserver.domain;

import learn.backendserver.TestHelpers;
import learn.backendserver.data.AppUserRecipeSavedRepository;
import learn.backendserver.models.AppUserRecipeSaved;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserRecipeSavedServiceTest {

    @Autowired
    AppUserRecipeSavedService service;

    @MockBean
    AppUserRecipeSavedRepository repository;

    @Test
    void findByUsername() {
        when(repository.findByUsername(TestHelpers.USER1.getUsername())).thenReturn(List.of(new AppUserRecipeSaved(
                1, 1, new Timestamp(0)
        )));
        List<AppUserRecipeSaved> actual = service.findByUsername(TestHelpers.USER1.getUsername());
        assertEquals(1, actual.get(0).getAppUserId());
        assertEquals(1, actual.get(0).getRecipeSavedId());
    }

    @Test
    void create() {
        when(repository.create(2, 1)).thenReturn(true);
        assertTrue(service.create(2, 1));
    }

    @Test
    void delete() {
        when(repository.delete(1, 2)).thenReturn(true);
        assertTrue(service.delete(1, 2));
    }

}