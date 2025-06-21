package learn.backendserver.domain;

import learn.backendserver.data.OrganizationRecipeRepository;
import learn.backendserver.models.OrganizationRecipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationRecipeService {
    private final OrganizationRecipeRepository repository;

    public OrganizationRecipeService(OrganizationRecipeRepository repository) {
        this.repository = repository;
    }

    public boolean create(OrganizationRecipe organizationRecipe) {
        return repository.create(organizationRecipe);
    }

    public List<OrganizationRecipe> findByOrganizationId(int organizationId) {
        return repository.findByOrganizationId(organizationId);
    }
}
