import com.google.gson.Gson;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;
import org.hyperskill.hstest.testcase.CheckResult;

import static org.hyperskill.hstest.testing.expect.json.JsonChecker.*;
import static org.hyperskill.hstest.testing.expect.Expectation.expect;

import recipes.RecipesApplication;

import static org.hyperskill.hstest.testcase.CheckResult.correct;


public class RecipesApplicationTest extends SpringTest {

    public RecipesApplicationTest() {
        super(RecipesApplication.class);
    }


    // Initialization ---

    static class Recipe {
        final String name;
        final String description;
        final String ingredients;
        final String directions;

        Recipe(String name, String description, String ingredients, String directions) {
            this.name = name;
            this.description = description;
            this.ingredients = ingredients;
            this.directions = directions;
        }
    }

    final Recipe[] RECIPES = {
        new Recipe(
            "Fresh Mint Tea /Test",
            "Light, aromatic and refreshing beverage, ... /Test",
            "boiled water, honey, fresh mint leaves /Test",
            "1) Boil water. 2) Pour boiling hot water into a mug. 3) Add fresh mint leaves. 4) Mix and" +
                " let the mint leaves seep for 3-5 minutes. 5) Add honey and mix again. /Test"
        ),

        new Recipe(
            "Fresh Mint Tea /Test2",
            "Light, aromatic and refreshing beverage, ... /Test2",
            "boiled water, honey, fresh mint leaves /Test2",
            "1) Boil water. 2) Pour boiling hot water into a mug. 3) Add fresh mint leaves. 4) Mix and" +
                " let the mint leaves seep for 3-5 minutes. 5) Add honey and mix again. /Test2"
        )
    };

    final String[] JSON_RECIPES = {
        new Gson().toJson(RECIPES[0]),
        new Gson().toJson(RECIPES[1])
    };

    final String API_RECIPE = "/api/recipe";


    // Helper functions ---

    static void throwIfIncorrectStatusCode(HttpResponse response, int status) {
        if (response.getStatusCode() != status) {
            throw new WrongAnswer(response.getRequest().getMethod() +
                " " + response.getRequest().getLocalUri() +
                " should respond with status code " + status +
                ", responded: " + response.getStatusCode() + "\n\n" +
                "Response body:\n" + response.getContent());
        }
    }


    // Tests ---

    @DynamicTest
    DynamicTesting[] dt = new DynamicTesting[]{

        () -> testPostRecipe(JSON_RECIPES[0]),
        () -> testGetRecipe(RECIPES[0]),

        () -> testPostRecipe(JSON_RECIPES[1]),
        () -> testGetRecipe(RECIPES[1])
    };

    CheckResult testPostRecipe(String jsonRecipe) {
        HttpResponse response = post(API_RECIPE, jsonRecipe).send();

        throwIfIncorrectStatusCode(response, 200);

        return correct();
    }

    CheckResult testGetRecipe(Recipe recipe) {
        HttpResponse response = get(API_RECIPE).send();

        throwIfIncorrectStatusCode(response, 200);

        expect(response.getContent()).asJson().check(
            isObject()
                .value("name", isString(recipe.name))
                .value("description", isString(recipe.description))
                .value("ingredients", isString(recipe.ingredients))
                .value("directions", isString(recipe.directions)));

        return correct();
    }
}
