package recipes.businesslayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistence.RecipeRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Optional<Recipe> findRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe save (Recipe recipe) {
        return recipeRepository.save(new Recipe(
                recipe.getId(),
                LocalDateTime.now(),
                recipe.getName(),
                recipe.getCategory(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections()
        ));
    }

    public void delete (Recipe recipe) {
        recipeRepository.delete(recipe);
    }
    public void update (Long id, Recipe recipe) {
        recipeRepository.save(new Recipe(
                id,
                LocalDateTime.now(),
                recipe.getName(),
                recipe.getCategory(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections()
        ));
    }

    public Collection<Recipe> search(Optional<String> name, Optional<String> category) {
        if (name.isPresent()) {
            return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name.get());
        }

        if (category.isPresent()) {
            return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category.get());
        }
        return Collections.emptyList();
    }
}
