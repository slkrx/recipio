package learn.backendserver.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import learn.backendserver.data.OrganizationRepository;
import learn.backendserver.models.Credentials;
import learn.backendserver.models.Organization;
import org.springframework.stereotype.Service;

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

}
