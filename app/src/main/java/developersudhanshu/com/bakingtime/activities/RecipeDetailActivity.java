package developersudhanshu.com.bakingtime.activities;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import developersudhanshu.com.bakingtime.R;
import developersudhanshu.com.bakingtime.adapters.RecipeStepRecyclerViewAdapter;
import developersudhanshu.com.bakingtime.model.RecipeDetails;
import developersudhanshu.com.bakingtime.model.Step;
import developersudhanshu.com.bakingtime.services.RecipeWidgetUpdateService;
import developersudhanshu.com.bakingtime.utility.Constants;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView recipeIngredientsTextView;
    private RecyclerView recipeStepsRecyclerView;
    private RecipeStepRecyclerViewAdapter adapter;
    private ArrayList<Step> mSteps;
    private ImageView recipeDishImage;
    private Toolbar toolbar;
    RecipeDetails recipeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mSteps = new ArrayList<>();

        if (getIntent() != null){
            if (getIntent().hasExtra(Constants.RECIPE_DATA_EXTRA_KEY)) {
                recipeDetails = getIntent().getParcelableExtra(Constants.RECIPE_DATA_EXTRA_KEY);

                mSteps.addAll(recipeDetails.getSteps());

                // Updating the recipeId in shared preference through a service only when opened the
                // activity through the main screen
                Intent updateRecipeId = new Intent(this, RecipeWidgetUpdateService.class);
                if (checkIfUpdateServiceIsRunningOrNot(RecipeWidgetUpdateService.class)) {
                    // Stopping the service before restarting the service again to update
                    // the widget
                    stopService(updateRecipeId);
                }

                updateRecipeId.setAction(RecipeWidgetUpdateService.ACTION_UPDATE_RECIPE_ID_FOR_WIDGET);
                updateRecipeId.putExtra(Constants.RECIPE_ID_INTENT_EXTRA_KEY, recipeDetails.getRecipeId());
                updateRecipeId.putExtra(Constants.RECIPE_NAME_INTENT_EXTRA_KEY, recipeDetails.getName());
                startService(updateRecipeId);
            }
            // TODO: Write the code to open the DetailActivity when clicked on the widget
        }

//        RecipeWidgetUpdateService.startActionUpdateWidget(this);
        setUpViews();
    }

    public boolean checkIfUpdateServiceIsRunningOrNot(Class<?> serviceClass) {
        // Method to check whether a service is running in the background or not
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void setUpViews() {
        recipeStepsRecyclerView = findViewById(R.id.rv_recipe_steps_act_recipe_details);
        recipeDishImage = findViewById(R.id.img_view_recipe_act_recipe_details);
        toolbar = findViewById(R.id.toolbar_act_recipe_details);

        adapter = new RecipeStepRecyclerViewAdapter(this, mSteps);

        recipeStepsRecyclerView.setAdapter(adapter);
        recipeStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        adapter.setOnItemClickListener(new RecipeStepRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent recipeDetailActivityIntent = new Intent(RecipeDetailActivity.this,
                        RecipeStepDetailActivity.class);
                recipeDetailActivityIntent.putParcelableArrayListExtra(Constants.RECIPE_STEP_DATA_EXTRA_KEY,
                        mSteps);
                recipeDetailActivityIntent.putExtra(Constants.RECIPE_STEP_CHOSEN_TO_WATCH_EXTRA_KEY, position);
                startActivity(recipeDetailActivityIntent);
            }
        });

        recipeDishImage.setImageResource(Constants.RECIPE_IMAGE_HASH_MAP.get(recipeDetails.getName()));
        toolbar.setTitle(recipeDetails.getName());
    }
}
