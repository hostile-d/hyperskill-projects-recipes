package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.businesslayer.Recipe;

import java.util.Collection;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Collection<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);
    Collection<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

}