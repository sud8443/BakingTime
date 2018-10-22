package developersudhanshu.com.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import developersudhanshu.com.bakingtime.R;
import developersudhanshu.com.bakingtime.activities.RecipeDetailActivity;
import developersudhanshu.com.bakingtime.model.RecipeDetails;
import developersudhanshu.com.bakingtime.services.RecipeWidgetUpdateService;
import developersudhanshu.com.bakingtime.utility.Constants;
import developersudhanshu.com.bakingtime.utility.Utility;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int recipeId, String recipeName) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_widget);

        Log.d("HELLO", "Widget is getting updated: recipeName: " + recipeName);
        Intent ingredientListIntent = new Intent(context, RecipeWidgetService.class);
        views.setRemoteAdapter(R.id.widget_ingredient_list, ingredientListIntent);

        if (recipeId != Constants.INVALID_RECIPE_ID) {
            views.setTextViewText(R.id.widget_recipe_name, recipeName);
        }

        views.setImageViewResource(R.id.widget_recipe_image, Constants.RECIPE_IMAGE_HASH_MAP.get(recipeName));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_ingredient_list);

        views.setEmptyView(R.id.widget_ingredient_list, R.id.tv_widget_empty_view);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        RecipeWidgetUpdateService.startActionUpdateWidget(context);
        updateRecipeListWidgets(context, appWidgetManager, appWidgetIds,
                Utility.getRecipeIdFromSharedPreferences(context),
                Utility.getRecipeNameFromSharedPreferences(context));
    }

    public static void updateRecipeListWidgets(Context context,
                                               AppWidgetManager appWidgetManager,
                                               int[] appWidgetIds,
                                               int recipeId,
                                               String recipeName) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeId, recipeName);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

