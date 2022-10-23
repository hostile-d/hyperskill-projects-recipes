package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.businesslayer.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Stream;

@RestController
@Validated
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @PostMapping("/api/recipe/new")
    public ResponseEntity<PostRecipeDTO> postRecipe(@RequestBody @Valid Recipe recipe) {
        try {
            var createdRecipe = recipeService.save(recipe, getCurrentUser());
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

        if (recipe.isEmpty()) {
            return new ResponseEntity<>("No recipe found with id " + id, HttpStatus.NOT_FOUND);
        }

        if (!getCurrentUser().equals(recipe.get().getUser())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        recipeService.delete(recipe.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value="/api/recipe/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRecipe(@PathVariable @NotNull Long id, @RequestBody @Valid Recipe recipe) {
        var foundRecipe = recipeService.findRecipeById(id);
        var user = getCurrentUser();

        if (foundRecipe.isPresent()) {
            assert user != null;
            if (user.getId() != null && !Objects.equals(foundRecipe.get().getUser().getId(), user.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        if (foundRecipe.isPresent()) {
            recipeService.update(id, recipe, user);
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

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return userService.findUserById(userService.loadUserByUsername(auth.getName()).getId()).get();
        } catch (UsernameNotFoundException ex) {
            return null;
        }
    }
}
