package learn.backendserver.controllers;

import learn.backendserver.domain.AppUserRecipeSavedService;
import learn.backendserver.domain.RecipeService;
import learn.backendserver.models.AppUser;
import learn.backendserver.models.AppUserRecipeSaved;
import learn.backendserver.models.Recipe;
import learn.backendserver.security.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app-user-recipe-saved")
public class AppUserRecipeSavedController {

    private final AppUserRecipeSavedService appUserRecipeSavedService;
    private final RecipeService recipeService;
    private final AppUserService appUserService;

    public AppUserRecipeSavedController(AppUserRecipeSavedService appUserRecipeSavedService, RecipeService recipeService, AppUserService appUserService) {
        this.appUserRecipeSavedService = appUserRecipeSavedService;
        this.recipeService = recipeService;
        this.appUserService = appUserService;
    }

    @GetMapping("/{username}")
    public List<Recipe> getSavedRecipes(@PathVariable String username) {
        List<AppUserRecipeSaved> appUserRecipeSaveds = appUserRecipeSavedService.findByUsername(username);

        return appUserRecipeSaveds.stream().map(appUserRecipeSaved -> {
            return recipeService.findById(appUserRecipeSaved.getRecipeSavedId());
        }).toList();
    }

    @PostMapping("/{username}/{recipeId}")
    public ResponseEntity<?> saveRecipe(@PathVariable String username, @PathVariable int recipeId) {
        AppUser user = (AppUser) appUserService.loadUserByUsername(username);
        if (appUserRecipeSavedService.create(user.getAppUserId(), recipeId)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{username}/{recipeId}")
    public ResponseEntity<?> deleteRecipeSave(@PathVariable String username, @PathVariable int recipeId) {
        AppUser user = (AppUser) appUserService.loadUserByUsername(username);
        if (appUserRecipeSavedService.delete(user.getAppUserId(), recipeId)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
