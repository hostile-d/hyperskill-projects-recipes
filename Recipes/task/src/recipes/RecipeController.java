package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipeController {
    @Autowired
    RecipesRepository recipesRepository;

    @PostMapping("/api/recipe/new")
    public ResponseEntity<RecipeDTO> postRecipe(@RequestBody Recipe recipe) {
        var id = recipesRepository.addNewRecipe(recipe);

        return new ResponseEntity<>(new RecipeDTO(id), HttpStatus.OK);
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity getRecipe(@PathVariable int id) {
        var recipe = recipesRepository.getRecipeById(id);

        if (recipe != null) {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }

        return new ResponseEntity<>("No recipe found with id " + id, HttpStatus.NOT_FOUND);
    }
}
