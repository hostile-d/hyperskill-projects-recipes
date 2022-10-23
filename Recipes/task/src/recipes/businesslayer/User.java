package recipes.businesslayer;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Pattern(regexp = ".+@.+\\..+")
    private String email;
    @Column
    @NotBlank
    @Size(min=8)
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();
}
