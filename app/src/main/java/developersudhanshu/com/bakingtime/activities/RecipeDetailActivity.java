package developersudhanshu.com.bakingtime.activities;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import developersudhanshu.com.bakingtime.R;
import developersudhanshu.com.bakingtime.adapters.RecipeStepRecyclerViewAdapter;
import developersudhanshu.com.bakingtime.fragments.RecipeStepDetailFragment;
import developersudhanshu.com.bakingtime.model.Ingredient;
import developersudhanshu.com.bakingtime.model.RecipeDetails;
import developersudhanshu.com.bakingtime.model.Step;
import developersudhanshu.com.bakingtime.services.RecipeWidgetUpdateService;
import developersudhanshu.com.bakingtime.utility.Constants;

public class RecipeDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rv_recipe_steps_act_recipe_details)
    RecyclerView recipeStepsRecyclerView;
    private RecipeStepRecyclerViewAdapter adapter;
    private ArrayList<Step> mSteps;
    @BindView(R.id.btn_recipe_ingredients_label_act_recipe_detail)
    Button recipeIngredients;
    @BindView(R.id.img_view_recipe_act_recipe_details)
    ImageView recipeDishImage;
    @BindView(R.id.toolbar_act_recipe_details)
    Toolbar toolbar;
    RecipeDetails recipeDetails;
    private boolean isTwoPaneLayout;
    @BindView(R.id.fl_recipe_step_detail_tablet_container)
    @Nullable
    FrameLayout stepDetailContainerOfTablet;
    private Parcelable recyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ButterKnife.bind(this);

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
        adapter = new RecipeStepRecyclerViewAdapter(this, mSteps);

        recipeStepsRecyclerView.setAdapter(adapter);
        recipeStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        if (stepDetailContainerOfTablet == null) {
            isTwoPaneLayout = false;
        }else {
            isTwoPaneLayout = true;
        }

        recipeIngredients.setOnClickListener(this);

        adapter.setOnItemClickListener(new RecipeStepRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                if (!isTwoPaneLayout) {
                    Intent recipeDetailActivityIntent = new Intent(RecipeDetailActivity.this,
                            RecipeStepDetailActivity.class);
                    recipeDetailActivityIntent.putParcelableArrayListExtra(Constants.RECIPE_STEP_DATA_EXTRA_KEY,
                            mSteps);
                    recipeDetailActivityIntent.putExtra(Constants.RECIPE_STEP_CHOSEN_TO_WATCH_EXTRA_KEY, position);
                    startActivity(recipeDetailActivityIntent);
                } else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fl_recipe_step_detail_tablet_container,
                            new RecipeStepDetailFragment(mSteps.get(position)))
                    .commit();
                }
            }
        });

        recipeDishImage.setImageResource(Constants.RECIPE_IMAGE_HASH_MAP.get(recipeDetails.getName()));
        toolbar.setTitle(recipeDetails.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (recyclerViewState != null) {
            recipeStepsRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        recyclerViewState = recipeStepsRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(Constants.RECYCLER_VIEW_STATE_KEY, recyclerViewState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        recyclerViewState = savedInstanceState.getParcelable(Constants.RECYCLER_VIEW_STATE_KEY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recipe_ingredients_label_act_recipe_detail:
                View view = View.inflate(this, R.layout.dialog_ingredient_list, null);
                AlertDialog dialog = new AlertDialog.Builder(this)
                                        .setTitle(getString(R.string.ingredients_label))
                                        .setView(view).create();
                TextView ingredientsText = view.findViewById(R.id.tv_recipe_ingredient_dialog);
                StringBuilder builder = new StringBuilder();
                for (Ingredient i: recipeDetails.getIngredients()) {
                    builder.append(Constants.BULLET_CODE + " " + i.getQuantity() + " "
                            + i.getMeasure() + " " + i.getIngredient() + "\n");
                }
                ingredientsText.setText(builder.toString());
                dialog.show();
                break;
        }
    }
}
