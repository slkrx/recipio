package learn.backendserver.domain;

import learn.backendserver.data.RecipeRepository;
import learn.backendserver.models.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe findById(int id) {
        return repository.findById(id);
    }

    public Recipe create(Recipe recipe) {
        return repository.create(recipe);
    }

    public boolean update(Recipe recipe) {
        return repository.update(recipe);
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }

}
