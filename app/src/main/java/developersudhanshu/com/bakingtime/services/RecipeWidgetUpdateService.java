package developersudhanshu.com.bakingtime.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import developersudhanshu.com.bakingtime.utility.Constants;
import developersudhanshu.com.bakingtime.utility.Utility;
import developersudhanshu.com.bakingtime.widget.RecipeIngredientWidget;

public class RecipeWidgetUpdateService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public static final String ACTION_UPDATE_RECIPE_ID_FOR_WIDGET =
            "developersudhanshu.com.bakingtime.action.update_recipe_id";
    public static final String ACTION_UPDATE_RECIPE_WIDGETS =
            "developersudhanshu.com.bakingtime.action.update_recipe_widgets";

    public RecipeWidgetUpdateService() {
        super("BakingApp");
    }

    public static void startActionUpdateWidget(Context context) {
        Intent startUpdateWidgetAction = new Intent(context, RecipeWidgetUpdateService.class);
        startUpdateWidgetAction.setAction(ACTION_UPDATE_RECIPE_WIDGETS);
        context.startService(startUpdateWidgetAction);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_UPDATE_RECIPE_ID_FOR_WIDGET:
                    Utility.updateSharedPreferencesRecipeId(this,
                            intent.getIntExtra(Constants.RECIPE_ID_INTENT_EXTRA_KEY,
                                    Constants.INVALID_RECIPE_ID),
                            intent.getStringExtra(Constants.RECIPE_NAME_INTENT_EXTRA_KEY));
                    Log.d("HELLO", "Service started to update recipe id" +
                                    intent.getIntExtra(Constants.RECIPE_ID_INTENT_EXTRA_KEY,
                                            Constants.INVALID_RECIPE_ID) + " " +
                            intent.getStringExtra(Constants.RECIPE_NAME_INTENT_EXTRA_KEY));
//                    startActionUpdateWidget(this);
                    updateRecipeWidget();
                    break;
                case ACTION_UPDATE_RECIPE_WIDGETS:
                    updateRecipeWidget();
                    break;
            }
        }
        android.os.Debug.waitForDebugger();
    }

    private void updateRecipeWidget() {
        // Steps to update Recipe Widget
        // STEP 1: Get the recipe id from Shared preferences whose content is to be displayed
        final int recipeId = Utility.getRecipeIdFromSharedPreferences(this);
        final String recipeName = Utility.getRecipeNameFromSharedPreferences(this);

        // STEP 2: Make a call to update the recipe list
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int appIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientWidget.class));
        RecipeIngredientWidget.updateRecipeListWidgets(this, appWidgetManager, appIds, recipeId, recipeName);
    }
}
