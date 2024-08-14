package learn.backendserver.controllers;

import learn.backendserver.domain.Result;
import learn.backendserver.models.AppUser;
import learn.backendserver.models.Credentials;
import learn.backendserver.security.AppUserService;
import learn.backendserver.security.JwtConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/security")
@ConditionalOnWebApplication
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtConverter jwtConverter;
    private final AppUserService appUserService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtConverter jwtConverter,
                          AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtConverter = jwtConverter;
        this.appUserService = appUserService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody Credentials credentials) {

        var token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        if (authentication.isAuthenticated()) {
            String jwt = jwtConverter.getTokenFromUser((AppUser)authentication.getPrincipal());
            Map<String, String> jwtMap = Map.of("jwt_token", jwt);
            return new ResponseEntity<>(jwtMap, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Credentials credentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Result<AppUser> result = appUserService.register(credentials);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        Map<String, Integer> map = Map.of("appUserId", result.getPayload().getAppUserId());
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@AuthenticationPrincipal AppUser appUser) {
        String jwt = jwtConverter.getTokenFromUser(appUser);
        Map<String, String> jwtMap = Map.of("jwt_token", jwt);
        return new ResponseEntity<>(jwtMap, HttpStatus.OK);
    }
}