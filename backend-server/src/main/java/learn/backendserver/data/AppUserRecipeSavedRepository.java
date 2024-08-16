package learn.backendserver.data;

import learn.backendserver.models.Recipe;

import java.util.List;

public interface AppUserRecipeSavedRepository {
    List<Recipe> findRecipeByUserSaved(String username);
}
