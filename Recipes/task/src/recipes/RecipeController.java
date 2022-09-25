package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.*;

@RestController
public class RecipeController {

    private Recipe savedRecipe;

    @GetMapping("/api/recipe")
    public ResponseEntity<Recipe> getRecipe() {
        return new ResponseEntity<>(savedRecipe, HttpStatus.OK);
    }


    @PostMapping("/api/recipe")
    public ResponseEntity<String> postRecipe(@RequestBody Recipe recipe) {
        savedRecipe = recipe;
        return new ResponseEntity<>("Recipe " + recipe.name + " has been saved", HttpStatus.OK);
    }
}
