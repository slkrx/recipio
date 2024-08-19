package learn.backendserver.domain;

import learn.backendserver.data.OrganizationAppUserRepository;
import learn.backendserver.models.OrganizationAppUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationAppUserService {

    private final OrganizationAppUserRepository repository;

    public OrganizationAppUserService(OrganizationAppUserRepository repository) {
        this.repository = repository;
    }

    public List<OrganizationAppUser> findByOrganizationId(int organizationId) {
        return repository.findByOrganizationId(organizationId);
    }

    public boolean create(OrganizationAppUser organizationAppUser) {
        return repository.create(organizationAppUser);
    }

}
