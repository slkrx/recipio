package learn.backendserver.data;

import learn.backendserver.models.AppUserRecipeCreated;

import java.util.List;

public interface AppUserRecipeCreatedRepository {
    boolean create(int appUserId, int recipeId);
    List<AppUserRecipeCreated> findByUsername(String username);
}
