package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.businesslayer.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}