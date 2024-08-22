package learn.backendserver.controllers;

import learn.backendserver.domain.OrganizationAppUserService;
import learn.backendserver.domain.OrganizationService;
import learn.backendserver.models.AppUser;
import learn.backendserver.models.Organization;
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
    private final OrganizationService organizationService;

    public OrganizationAppUserController(OrganizationAppUserService service, AppUserService userService, OrganizationService organizationService) {
        this.service = service;
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @GetMapping("/{organizationId}")
    public List<AppUser> findByOrganizationId(@PathVariable int organizationId) {
        List<OrganizationAppUser> organizationAppUsers = service.findByOrganizationId(organizationId);
        return organizationAppUsers.stream().map(organizationAppUser -> {
            return userService.findById(organizationAppUser.getAppUserId());
        }).toList();
    }

    @GetMapping
    public List<Organization> findByUsername(@RequestParam String username) {
        List<OrganizationAppUser> organizationAppUsers = service.findByUsername(username);
        return organizationAppUsers.stream().map(organizationAppUser -> {
            return organizationService.findById(organizationAppUser.getOrganizationId());
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
