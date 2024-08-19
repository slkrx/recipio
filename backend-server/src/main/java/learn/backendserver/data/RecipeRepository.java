package learn.backendserver.data;

import learn.backendserver.models.Recipe;

import java.util.List;

public interface RecipeRepository {
    Recipe findById(int id);
    List<Recipe> findByIds(List<Integer> ids);
    Recipe create(Recipe recipe);
    boolean update(Recipe recipe);
    boolean delete(int id);
}
