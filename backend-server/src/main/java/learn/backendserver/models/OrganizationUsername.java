package learn.backendserver.models;

public class OrganizationUsername {
    int organizationId;
    String username;

    public OrganizationUsername(int organizationId, String username) {
        this.organizationId = organizationId;
        this.username = username;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
