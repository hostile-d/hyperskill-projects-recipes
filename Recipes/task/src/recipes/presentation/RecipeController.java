package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.businesslayer.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @PostMapping("/api/recipe/new")
    public ResponseEntity<PostRecipeDTO> postRecipe(@RequestBody @Valid Recipe recipe) {
        try {
            var createdRecipe = recipeService.save(new Recipe(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getDescription(),
                    recipe.getIngredients(),
                    recipe.getDirections()
            ));
            return new ResponseEntity<>(new PostRecipeDTO(createdRecipe), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity getRecipe(@PathVariable @NotNull Long id) {
        var recipe = recipeService.findRecipeById(id);

        if (recipe.isPresent()) {
            return new ResponseEntity<>(new GetRecipeDTO(recipe.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>("No recipe found with id " + id, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity deleteRecipe(@PathVariable @NotNull Long id) {
        var recipe = recipeService.findRecipeById(id);
        if (recipe.isPresent()) {
            recipeService.delete(recipe.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No recipe found with id " + id, HttpStatus.NOT_FOUND);
    }
}
