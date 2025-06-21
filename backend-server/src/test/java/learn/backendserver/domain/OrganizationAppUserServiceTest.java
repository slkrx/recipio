package learn.backendserver.domain;

import learn.backendserver.TestHelpers;
import learn.backendserver.data.OrganizationAppUserRepository;
import learn.backendserver.models.OrganizationAppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrganizationAppUserServiceTest {

    @Autowired
    OrganizationAppUserService service;

    @MockBean
    OrganizationAppUserRepository repository;

    @Test
    void findByOrganizationId() {
        OrganizationAppUser expected = new OrganizationAppUser(TestHelpers.ORGANIZATION1.getId(),
                TestHelpers.USER2.getAppUserId());
        when(repository.findByOrganizationId(TestHelpers.ORGANIZATION1.getId())).thenReturn(List.of(expected));
        assertEquals(List.of(expected), service.findByOrganizationId(TestHelpers.ORGANIZATION1.getId()));
    }

    @Test
    void create() {
        OrganizationAppUser arg = new OrganizationAppUser(TestHelpers.ORGANIZATION1.getId(),
                TestHelpers.USER1.getAppUserId());
        when(repository.create(arg)).thenReturn(true);
        assertTrue(service.create(arg));
    }

    @Test
    void findByUsername() {
        OrganizationAppUser expected = new OrganizationAppUser(TestHelpers.ORGANIZATION1.getId(),
                TestHelpers.USER2.getAppUserId());
        when(repository.findByUsername(TestHelpers.USER2.getUsername())).thenReturn(List.of(expected));
        assertEquals(List.of(expected), service.findByUsername(TestHelpers.USER2.getUsername()));
    }
}