package recipes.presentation;

import lombok.*;
import recipes.businesslayer.Recipe;

@Data
@NoArgsConstructor
public class GetRecipeDTO {
    String name;
    String description;
    String[] ingredients;
    String[] directions;
    public GetRecipeDTO(Recipe recipe) {
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.ingredients = recipe.getIngredients();
        this.directions = recipe.getDirections();
    }
}
