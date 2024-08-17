package learn.backendserver.models;

import java.sql.Timestamp;
import java.util.Objects;

public class AppUserRecipeCreated {
    int appUserId;
    int recipeId;
    Timestamp createdDateTime;

    public AppUserRecipeCreated(int appUserId, int recipeSavedId, Timestamp createdDateTime) {
        this.appUserId = appUserId;
        this.recipeId = recipeSavedId;
        this.createdDateTime = createdDateTime;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Timestamp getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Timestamp createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserRecipeCreated that = (AppUserRecipeCreated) o;
        return appUserId == that.appUserId && recipeId == that.recipeId && Objects.equals(createdDateTime, that.createdDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, recipeId, createdDateTime);
    }
}
