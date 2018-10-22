package developersudhanshu.com.bakingtime.lifecycle_components;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecipeIngredientDao {

    @Query("SELECT * FROM recipe_ingredient_data ORDER BY id")
    List<RecipeIngredientModel> loadAllRecipes();

    @Insert
    void insertRecipe(RecipeIngredientModel recipeData);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(RecipeIngredientModel recipeData);

    @Delete
    void deleteRecipe(RecipeIngredientModel recipeData);

    @Query("SELECT * FROM recipe_ingredient_data WHERE recipe_id = :id")
    List<RecipeIngredientModel> loadIngredientsById(int id);
}
