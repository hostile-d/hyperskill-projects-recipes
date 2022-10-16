package recipes.presentation;

import lombok.*;
import recipes.businesslayer.Recipe;

@Data
@NoArgsConstructor
public class PostRecipeDTO {
    Long id;
    public PostRecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
    }
}
