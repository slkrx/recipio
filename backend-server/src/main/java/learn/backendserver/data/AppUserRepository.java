package learn.backendserver.data;

import learn.backendserver.models.AppUser;

public interface AppUserRepository {
    AppUser findByUsername(String username);
    AppUser findById(int appUserId);
    AppUser create(AppUser user);
    boolean update(AppUser user);
}