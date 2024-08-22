package learn.backendserver.controllers;

import learn.backendserver.domain.OrganizationRecipeService;
import learn.backendserver.domain.RecipeService;
import learn.backendserver.models.OrganizationRecipe;
import learn.backendserver.models.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization-recipe")
public class OrganizationRecipeController {
    private final OrganizationRecipeService service;
    private final RecipeService recipeService;

    public OrganizationRecipeController(OrganizationRecipeService service, RecipeService recipeService) {
        this.service = service;
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrganizationRecipe organizationRecipe) {
        if (service.create(organizationRecipe)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{organizationId}")
    public List<Recipe> findByOrganizationId(@PathVariable int organizationId) {
        List<OrganizationRecipe> organizationRecipes = service.findByOrganizationId(organizationId);
        return organizationRecipes.stream().map(organizationRecipe -> {
            return recipeService.findById(organizationRecipe.getRecipeId());
        }).toList();
    }
}
