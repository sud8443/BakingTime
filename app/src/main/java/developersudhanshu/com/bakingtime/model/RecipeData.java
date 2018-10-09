package developersudhanshu.com.bakingtime.model;

/**
 * Created by HP on 20-09-2018.
 */

public class RecipeData {
    private String name;
    private int steps;
    private int servings;
    private int imageResourceId;

    // Alternate constructor to allow us to not pass the image URL initially
    public RecipeData(String name, int steps, int servings){
        this.name = name;
        this.steps = steps;
        this.servings = servings;
    }

    // Constructor to initialize all the data at once
    public RecipeData(String name, int steps, int servings, int imageUrl){
        this.name = name;
        this.steps = steps;
        this.servings = servings;
        this.imageResourceId = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
