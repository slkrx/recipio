package learn.backendserver.controllers;

import learn.backendserver.domain.OrganizationService;
import learn.backendserver.domain.Result;
import learn.backendserver.models.AppUser;
import learn.backendserver.models.Organization;
import learn.backendserver.security.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService service;
    private final AppUserService userService;

    public OrganizationController(OrganizationService service, AppUserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> create(@RequestBody Organization organization, @PathVariable String username) {
        AppUser user = (AppUser) userService.loadUserByUsername(username);
        organization.setOwnerId(user.getAppUserId());
        Result<Organization> result = service.create(organization);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Organization organization = service.findById(id);
        if (organization != null) {
            return new ResponseEntity<>(organization, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<Organization> findByUsername(@RequestParam String username) {
        return service.findByUsername(username);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Result<?> result = service.delete(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Organization organization) {
        Result<?> result = service.update(organization);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }

}
