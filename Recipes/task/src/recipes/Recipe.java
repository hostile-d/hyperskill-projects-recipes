package recipes;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    String name;
    String description;
    String[] ingredients;
    String[] directions;
}
