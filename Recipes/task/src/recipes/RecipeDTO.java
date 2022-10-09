package recipes;

import lombok.*;

@Data
@NoArgsConstructor
public class RecipeDTO {
    Integer id;
    public RecipeDTO(int id) {
        this.id = id;
    }
}
