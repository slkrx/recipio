package learn.backendserver.models;

import java.util.Objects;

public class OrganizationAppUser {
    int organizationId;
    int appUserId;

    public OrganizationAppUser(int organizationId, int appUserId) {
        this.organizationId = organizationId;
        this.appUserId = appUserId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationAppUser that = (OrganizationAppUser) o;
        return organizationId == that.organizationId && appUserId == that.appUserId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, appUserId);
    }

}
