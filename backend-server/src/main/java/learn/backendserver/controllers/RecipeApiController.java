package learn.backendserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.backendserver.domain.AppUserRecipeCreatedService;
import learn.backendserver.domain.RecipeService;
import learn.backendserver.models.AppUser;
import learn.backendserver.models.Recipe;
import learn.backendserver.security.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
public class RecipeApiController {

    private final RecipeService service;
    private final AppUserRecipeCreatedService appUserRecipeCreatedService;
    private final AppUserService appUserService;

    public RecipeApiController(RecipeService service, AppUserRecipeCreatedService appUserRecipeCreatedService, AppUserService appUserService) {
        this.service = service;
        this.appUserRecipeCreatedService = appUserRecipeCreatedService;
        this.appUserService = appUserService;
    }

    @GetMapping("/search")
    public Map<String, Object> findByQuery(@RequestParam("q") String query, @RequestParam("p") int page) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("http://localhost:%s/api/v1/index/?q=%s&p=%s", 8000, query, page);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> message = new HashMap<>();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            List<Recipe> recipes = new ArrayList<>();
            root.path("docs").forEach(doc -> recipes.add(service.findById(doc.path("docid").asInt())));
            int length = root.path("length").asInt();
            message.put("recipes", recipes);
            message.put("length", length);
            return message;
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        message.put("recipes", new ArrayList<>());
        message.put("length", 0);
        return message;
    }

    @GetMapping("/{id}")
    public Recipe findById(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> create(@RequestBody Recipe recipe, @PathVariable String username) {
        recipe = service.create(recipe);
        AppUser user = (AppUser) appUserService.loadUserByUsername(username);
        if (appUserRecipeCreatedService.create(user.getAppUserId(), recipe.getId())) {
            return new ResponseEntity<Recipe>(recipe, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Recipe recipe, @PathVariable int id) {
        if (service.update(recipe)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (service.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
