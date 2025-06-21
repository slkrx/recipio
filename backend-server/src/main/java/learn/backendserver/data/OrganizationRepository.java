package learn.backendserver.data;

import learn.backendserver.models.Organization;

import java.util.List;

public interface OrganizationRepository {
    Organization findById(int id);
    Organization create(Organization organization);
    List<Organization> findByUsername(String username);
    boolean update(Organization organization);
    boolean delete(int id);
}
