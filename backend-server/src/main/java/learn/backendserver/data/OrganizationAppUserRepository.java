package learn.backendserver.data;

import learn.backendserver.models.OrganizationAppUser;

import java.util.List;

public interface OrganizationAppUserRepository {
    List<OrganizationAppUser> findByOrganizationId(int organizationId);
    boolean create(OrganizationAppUser organizationAppUser);
    List<OrganizationAppUser> findByUsername(String username);
}
