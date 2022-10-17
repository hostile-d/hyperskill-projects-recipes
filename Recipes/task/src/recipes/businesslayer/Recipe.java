package recipes.businesslayer;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime date;
    @Column
    @NotBlank
    private String name;
    @Column
    @NotBlank
    private String category;
    @Column
    @NotBlank
    private String description;
    @Column
    @NotEmpty
    private String[] ingredients;
    @Column
    @NotEmpty
    private String[] directions;
}
