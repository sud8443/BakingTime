package developersudhanshu.com.bakingtime;

import java.util.HashMap;

public abstract class Constants {
    // Making the class as abstract since it will not be instantiated
    public static final String RECIPE_DATA_EXTRA_KEY = "RecipeData";
    public static final String RECIPE_STEP_URL_EXTRA_KEY = "RecipeStepUrl";
    public static final String RECIPE_STEP_CHOSEN_TO_WATCH_EXTRA_KEY = "RecipeStepChosen";
    public static final String RECIPE_STEP_DATA_EXTRA_KEY = "RecipeSteps";
    public static final String RECIPE_NAME_NUTELLA_PIE = "Nutella Pie";
    public static final String RECIPE_NAME_BROWNIES = "Brownies";
    public static final String RECIPE_NAME_YELLOW_CAKE = "Yellow Cake";
    public static final String RECIPE_NAME_CHEESE_CAKE = "Cheesecake";
    public static final String EXO_PLAYER_POSITION_KEY = "ExoPlayerPosition";
    public static final String EXO_PLAYER_IS_PAUSED_KEY = "ExoPlayerPaused";
    public static final HashMap<String , Integer> RECIPE_IMAGE_HASH_MAP =
            new HashMap<String, Integer>(){{
                put(Constants.RECIPE_NAME_NUTELLA_PIE, R.drawable.nutella_pie);
                put(Constants.RECIPE_NAME_BROWNIES, R.drawable.brownies);
                put(Constants.RECIPE_NAME_YELLOW_CAKE, R.drawable.yellow_cake);
                put(Constants.RECIPE_NAME_CHEESE_CAKE, R.drawable.cheese_cake);
            }};
}
