package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.businesslayer.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@Validated
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @PostMapping("/api/recipe/new")
    public ResponseEntity<PostRecipeDTO> postRecipe(@RequestBody @Valid Recipe recipe) {
        try {
            var createdRecipe = recipeService.save(recipe);
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

    @PutMapping(value="/api/recipe/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRecipe(@PathVariable @NotNull Long id, @RequestBody @Valid Recipe recipe) {
        var foundRecipe = recipeService.findRecipeById(id);
        if (foundRecipe.isPresent()) {
            recipeService.update(id, recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No recipe found with id " + id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/recipe/search/")
    public ResponseEntity getRecipe(@RequestParam Optional<String> name, @RequestParam Optional<String> category) {
        var paramsPassed = Stream.of(name, category).filter(Optional::isPresent).count();
        if (paramsPassed != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var result = recipeService.search(name, category);
        if (result.size() > 0) {
            return new ResponseEntity<>(result.stream().map(GetRecipeDTO::new).toArray(), HttpStatus.OK);
        }

        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }
}
