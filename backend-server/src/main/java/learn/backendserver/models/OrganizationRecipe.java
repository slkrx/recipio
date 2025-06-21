package learn.backendserver.models;

import java.util.Objects;

public class OrganizationRecipe {
    int organizationId;
    int recipeId;

    public OrganizationRecipe(int organizationId, int recipeId) {
        this.organizationId = organizationId;
        this.recipeId = recipeId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationRecipe that = (OrganizationRecipe) o;
        return organizationId == that.organizationId && recipeId == that.recipeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, recipeId);
    }
}
