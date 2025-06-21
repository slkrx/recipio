package learn.backendserver.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import learn.backendserver.data.OrganizationRepository;
import learn.backendserver.models.Organization;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository repository;
    private final Validator validator;

    public OrganizationService(OrganizationRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Result<Organization> create(Organization organization) {
        Result<Organization> result = validate(organization);

        if (!result.isSuccess()) return result;

        result.setPayload(repository.create(organization));
        return result;
    }

    public Organization findById(int id) {
        return repository.findById(id);
    }

    public List<Organization> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    private Result<Organization> validate(Organization organization) {
        Result<Organization> result = new Result<>();

        if(organization == null) {
            result.addMessage("Organization cannot be null.");
            return result;
        }

        var errors = validator.validate(organization);

        for (ConstraintViolation<Organization> violation : errors) {
            result.addMessage(violation.getMessage());
        }

        return result;
    }

    public Result<Void> delete(int organizationId) {
        Result<Void> result = new Result<>();
        if (repository.delete(organizationId)) {
            return result;
        }
        result.addMessage(String.format("Organization %d not found", organizationId));
        return result;
    }

    public Result<Void> update(Organization organization) {
        Result<Void> result = new Result<>();
        if (repository.update(organization)) {
            return result;
        }
        result.addMessage("Server error");
        return result;
    }

}
