package learn.backendserver.domain;

import learn.backendserver.data.AppUserRecipeCreatedMapper;
import learn.backendserver.data.AppUserRecipeCreatedRepository;
import learn.backendserver.models.AppUserRecipeCreated;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserRecipeCreatedService {
    private final AppUserRecipeCreatedRepository repository;

    public AppUserRecipeCreatedService(AppUserRecipeCreatedRepository repository) {
        this.repository = repository;
    }

    public boolean create(int appUserId, int recipeId) {
        return repository.create(appUserId, recipeId);
    }

    public List<AppUserRecipeCreated> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
