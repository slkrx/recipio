package learn.backendserver.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class AppUserRecipeSaved {
    int appUserId;
    int recipeSavedId;
    Timestamp savedDateTime;

    public AppUserRecipeSaved(int appUserId, int recipeSavedId, Timestamp savedDateTime) {
        this.appUserId = appUserId;
        this.recipeSavedId = recipeSavedId;
        this.savedDateTime = savedDateTime;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getRecipeSavedId() {
        return recipeSavedId;
    }

    public void setRecipeSavedId(int recipeSavedId) {
        this.recipeSavedId = recipeSavedId;
    }

    public Timestamp getSavedDateTime() {
        return savedDateTime;
    }

    public void setSavedDateTime(Timestamp savedDateTime) {
        this.savedDateTime = savedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserRecipeSaved that = (AppUserRecipeSaved) o;
        return appUserId == that.appUserId && recipeSavedId == that.recipeSavedId && Objects.equals(savedDateTime, that.savedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, recipeSavedId, savedDateTime);
    }

}
