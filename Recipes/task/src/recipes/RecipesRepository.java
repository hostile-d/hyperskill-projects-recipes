package recipes;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Data
@NoArgsConstructor
@Component
public class RecipesRepository {
    private HashMap<Integer, Recipe> recipes = new HashMap<>();

    public Recipe getRecipeById(Integer id) {
        return recipes.get(id);
    }

    public Integer addNewRecipe(Recipe recipe) {
        var id = recipes.size() + 1;
        recipes.put(id, recipe);
        return id;
    }
}
