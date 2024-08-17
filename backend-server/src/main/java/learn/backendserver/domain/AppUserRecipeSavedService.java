package learn.backendserver.domain;

import learn.backendserver.data.AppUserRecipeSavedRepository;
import learn.backendserver.models.AppUserRecipeSaved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserRecipeSavedService {

    private final AppUserRecipeSavedRepository repository;

    public AppUserRecipeSavedService(AppUserRecipeSavedRepository repository) {
        this.repository = repository;
    }

    public List<AppUserRecipeSaved> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public boolean create(int appUserId, int recipeId) {
        return repository.create(appUserId, recipeId);
    }

    public boolean delete(int appUserId, int recipeId) {
        return repository.delete(appUserId, recipeId);
    }
}
