package learn.backendserver.domain;

import learn.backendserver.TestHelpers;
import learn.backendserver.data.OrganizationRepository;
import learn.backendserver.models.Organization;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrganizationServiceTest {

    @Autowired
    OrganizationService service;

    @MockBean
    OrganizationRepository repository;

    @Test
    void shouldCreate() {
        Organization arg = new Organization(0, TestHelpers.USER1.getAppUserId(), "test");
        Organization created = new Organization(1, TestHelpers.USER1.getAppUserId(), "test");
        when(repository.create(arg)).thenReturn(created);

        Result<Organization> result = service.create(arg);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotCreateWithoutOwnerId() {
        Organization arg = new Organization(0, 0, "test");

        Result<Organization> result = service.create(arg);

        assertFalse(result.isSuccess());
        assertEquals("Organization must be associated with a User by id.", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithoutName() {
        Organization arg = new Organization(0, TestHelpers.USER1.getAppUserId(), "");

        Result<Organization> result = service.create(arg);

        assertFalse(result.isSuccess());
        assertEquals("Organization name is required.", result.getMessages().get(0));
    }

}