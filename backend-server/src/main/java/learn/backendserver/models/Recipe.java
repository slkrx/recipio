package learn.backendserver.models;

import java.util.Arrays;
import java.util.Objects;

public class Recipe {

    private int id;
    private String title;
    private String[] categories;
    private float rating;
    private short ratings;
    private String imageUrl;
    private String time;
    private String description;
    private String[] ingredients;
    private String[] steps;
    private String url;

    public Recipe() {}

    public Recipe(int id, String title, String[] categories, float rating, short ratings, String imageUrl, String time, String description, String[] ingredients, String[] steps, String url) {
        this.id = id;
        this.title = title;
        this.categories = categories;
        this.rating = rating;
        this.ratings = ratings;
        this.imageUrl = imageUrl;
        this.time = time;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.url = url;
    }

    public Recipe(Recipe recipe) {
        this.id =           recipe.getId();
        this.title =        recipe.getTitle();
        this.categories =   recipe.getCategories();
        this.rating =       recipe.getRating();
        this.ratings =      recipe.getRatings();
        this.imageUrl =     recipe.getImageUrl();
        this.time =         recipe.getTime();
        this.description =  recipe.getDescription();
        this.ingredients =  recipe.getIngredients();
        this.steps =        recipe.getSteps();
        this.url =          recipe.getUrl();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public short getRatings() {
        return ratings;
    }

    public void setRatings(short ratings) {
        this.ratings = ratings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && Float.compare(rating, recipe.rating) == 0 && ratings == recipe.ratings && Objects.equals(title, recipe.title) && Objects.deepEquals(categories, recipe.categories) && Objects.equals(imageUrl, recipe.imageUrl) && Objects.equals(time, recipe.time) && Objects.equals(description, recipe.description) && Objects.deepEquals(ingredients, recipe.ingredients) && Objects.deepEquals(steps, recipe.steps) && Objects.equals(url, recipe.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, Arrays.hashCode(categories), rating, ratings, imageUrl, time, description, Arrays.hashCode(ingredients), Arrays.hashCode(steps), url);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", categories=" + Arrays.toString(categories) +
                ", rating=" + rating +
                ", ratings=" + ratings +
                ", imageUrl='" + imageUrl + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", steps=" + Arrays.toString(steps) +
                ", url='" + url + '\'' +
                '}';
    }
}
