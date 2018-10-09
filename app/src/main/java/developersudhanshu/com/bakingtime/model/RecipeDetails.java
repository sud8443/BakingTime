package developersudhanshu.com.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetails implements Parcelable {

    public RecipeDetails(String name, List<Ingredient> ingredients, List<Step> steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
    }

    protected RecipeDetails(Parcel in) {
        this.name = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(this.ingredients, Ingredient.class.getClassLoader());
        this.steps = new ArrayList<Step>();
        in.readList(this.steps, Step.class.getClassLoader());
    }

    public static final Parcelable.Creator<RecipeDetails> CREATOR = new Parcelable.Creator<RecipeDetails>() {

        @Override
        public RecipeDetails createFromParcel(Parcel source) {
            return new RecipeDetails(source);
        }

        @Override
        public RecipeDetails[] newArray(int size) {
            return new RecipeDetails[size];
        }
    };
}
