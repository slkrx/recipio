package learn.backendserver.data;

import learn.backendserver.models.AppUserRecipeSaved;
import learn.backendserver.models.Recipe;

import java.util.List;

public interface AppUserRecipeSavedRepository {
    List<AppUserRecipeSaved> findByUsername(String username);
    boolean create(int appUserId, int recipeId);
    boolean delete(int appUserId, int recipeId);
}
