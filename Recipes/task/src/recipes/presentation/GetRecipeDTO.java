package recipes.presentation;

import lombok.*;
import recipes.businesslayer.Recipe;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GetRecipeDTO {
    String name;
    String description;
    String category;
    LocalDateTime date;
    String[] ingredients;
    String[] directions;
    public GetRecipeDTO(Recipe recipe) {
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.category = recipe.getCategory();
        this.date = recipe.getDate();
        this.ingredients = recipe.getIngredients();
        this.directions = recipe.getDirections();
    }
}
