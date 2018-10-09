package developersudhanshu.com.bakingtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import developersudhanshu.com.bakingtime.adapters.RecipeStepRecyclerViewAdapter;
import developersudhanshu.com.bakingtime.model.RecipeDetails;
import developersudhanshu.com.bakingtime.model.Step;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView recipeIngredientsTextView;
    private RecyclerView recipeStepsRecyclerView;
    private RecipeStepRecyclerViewAdapter adapter;
    private ArrayList<Step> mSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mSteps = new ArrayList<>();

        setUpViews();

        if (getIntent() != null && getIntent().hasExtra(Constants.RECIPE_DATA_EXTRA_KEY)){
            RecipeDetails recipeDetails = getIntent().getParcelableExtra(Constants.RECIPE_DATA_EXTRA_KEY);
            recipeIngredientsTextView.setText(recipeDetails.getName());

            mSteps.addAll(recipeDetails.getSteps());
        }
    }

    private void setUpViews() {
        recipeIngredientsTextView = findViewById(R.id.tv_recipe_ingredients_act_recipe_details);
        recipeStepsRecyclerView = findViewById(R.id.rv_recipe_steps_act_recipe_details);

        adapter = new RecipeStepRecyclerViewAdapter(this, mSteps);

        recipeStepsRecyclerView.setAdapter(adapter);
        recipeStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
    }
}
