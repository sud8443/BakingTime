package developersudhanshu.com.bakingtime.widget;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import developersudhanshu.com.bakingtime.R;
import developersudhanshu.com.bakingtime.lifecycle_components.RecipeDatabase;
import developersudhanshu.com.bakingtime.lifecycle_components.RecipeIngredientModel;
import developersudhanshu.com.bakingtime.utility.Constants;
import developersudhanshu.com.bakingtime.utility.Utility;

public class RecipeWidgetIngredientListFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<RecipeIngredientModel> ingredients;
    private int recipeId; // Id of recipe which was last visited
    private RecipeDatabase mDb;
    private final static String TAG = RecipeWidgetIngredientListFactory.class.getSimpleName();

    public RecipeWidgetIngredientListFactory(Context context) {
        mContext = context;
        mDb = RecipeDatabase.getInstance(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // Re-query the recipe database and fetch the new recipe's content
        recipeId = Utility.getRecipeIdFromSharedPreferences(mContext);
        if (recipeId != Constants.INVALID_RECIPE_ID) {
            ingredients = new ArrayList<>(mDb.getRecipeDao().loadIngredientsById(recipeId));
        } else{
            Log.d(TAG, "Invalid Recipe Id");
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_ingredient_list);
        remoteViews.setTextViewText(R.id.widget_tv_ingredient_quantity,
                Constants.BULLET_CODE + String.valueOf(ingredients.get(position).getQuantity()));
        remoteViews.setTextViewText(R.id.widget_tv_ingredient_measure, ingredients.get(position).getMeasure());
        remoteViews.setTextViewText(R.id.widget_tv_ingredient_name, ingredients.get(position).getIngredient());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
