package learn.backendserver.controllers;

import learn.backendserver.domain.AppUserRecipeCreatedService;
import learn.backendserver.domain.RecipeService;
import learn.backendserver.models.AppUserRecipeCreated;
import learn.backendserver.models.Recipe;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/app-user-recipe-created")
public class AppUserRecipeCreatedController {
    private final AppUserRecipeCreatedService service;
    private final RecipeService recipeService;

    public AppUserRecipeCreatedController(AppUserRecipeCreatedService service, RecipeService recipeService) {
        this.service = service;
        this.recipeService = recipeService;
    }

    @GetMapping("/{username}")
    public List<Recipe> findByUsername(@PathVariable String username) {
        List<AppUserRecipeCreated> appUserRecipeCreateds = service.findByUsername(username);
        return appUserRecipeCreateds.stream().map(appUserRecipeCreated -> (
                recipeService.findById(appUserRecipeCreated.getRecipeId())
        )).toList();
    }
}
