package learn.backendserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.backendserver.domain.RecipeService;
import learn.backendserver.models.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeApiController {

    private final RecipeService service;

    public RecipeApiController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<Recipe> findByQuery(@RequestParam("q") String query, @RequestParam("p") int page) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("http://localhost:%s/api/v1/index/?q=%s&p=%s", 8000, query, page);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            List<Recipe> recipes = new ArrayList<>();
            root.path("docs").forEach(doc -> recipes.add(service.findById(doc.path("docid").asInt())));
            return recipes;
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return List.of();
    }
}
