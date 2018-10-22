package developersudhanshu.com.bakingtime.lifecycle_components;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recipe_ingredient_data")
public class RecipeIngredientModel {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "recipe_id")
    int recipeId;
    @ColumnInfo(name = "recipe_name")
    String recipeName;
    @ColumnInfo(name = "quantity")
    private Float quantity;
    @ColumnInfo(name = "measure")
    private String measure;
    @ColumnInfo(name = "name")
    private String ingredient;

    public RecipeIngredientModel(int recipeId, String recipeName, Float quantity,
                                 String measure, String ingredient) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;

    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
