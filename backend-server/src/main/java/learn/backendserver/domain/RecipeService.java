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

    public List<Recipe> findByIds(List<Integer> ids) {
        return repository.findByIds(ids);
    }

}
