package learn.backendserver.controllers;

import learn.backendserver.domain.OrganizationAppUserService;
import learn.backendserver.models.AppUser;
import learn.backendserver.models.OrganizationAppUser;
import learn.backendserver.models.OrganizationUsername;
import learn.backendserver.security.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization-app-user")
public class OrganizationAppUserController {

    private final OrganizationAppUserService service;
    private final AppUserService userService;

    public OrganizationAppUserController(OrganizationAppUserService service, AppUserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/{organizationId}")
    public List<AppUser> findByOrganizationId(@PathVariable int organizationId) {
        List<OrganizationAppUser> organizationAppUsers = service.findByOrganizationId(organizationId);
        return organizationAppUsers.stream().map(organizationAppUser -> {
            return userService.findById(organizationAppUser.getAppUserId());
        }).toList();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrganizationUsername organizationUsername) {
        AppUser user = (AppUser) userService.loadUserByUsername(organizationUsername.getUsername());
        OrganizationAppUser organizationAppUser = new OrganizationAppUser(
                organizationUsername.getOrganizationId(), user.getAppUserId());
        if (service.create(organizationAppUser)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
