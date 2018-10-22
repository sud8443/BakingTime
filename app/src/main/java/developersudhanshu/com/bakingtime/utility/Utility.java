package developersudhanshu.com.bakingtime.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Utility {

    public static void updateSharedPreferencesRecipeId(Context context, int recipeId, String recipeName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        if (recipeId != Constants.INVALID_RECIPE_ID) {
            sharedPreferences.edit().putInt(Constants.RECIPE_ID_SHARED_PREFERENCES_KEY, recipeId).apply();
            sharedPreferences.edit().putString(Constants.RECIPE_NAME_SHARED_PREFERENCES_KEY, recipeName).apply();
        } else
            Log.d(Utility.class.getSimpleName(), "Invalid Recipe Id encountered");
    }

    public static int getRecipeIdFromSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getInt(Constants.RECIPE_ID_SHARED_PREFERENCES_KEY, Constants.INVALID_RECIPE_ID);
    }

    public static String getRecipeNameFromSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getString(Constants.RECIPE_NAME_SHARED_PREFERENCES_KEY, Constants.INVALID_RECIPE_NAME);
    }

    public static boolean isFirstLaunch(Context context) {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getBoolean(Constants.FIRST_LAUNCH_SHARED_PREFERENCES_KEY, true);
    }

    public static void setFirstLaunch(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Constants.FIRST_LAUNCH_SHARED_PREFERENCES_KEY, value).apply();
    }
}
