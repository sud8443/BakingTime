package developersudhanshu.com.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
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
    private ImageView recipeDishImage;
    private Toolbar toolbar;
    RecipeDetails recipeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mSteps = new ArrayList<>();

        if (getIntent() != null && getIntent().hasExtra(Constants.RECIPE_DATA_EXTRA_KEY)){
            recipeDetails = getIntent().getParcelableExtra(Constants.RECIPE_DATA_EXTRA_KEY);

            mSteps.addAll(recipeDetails.getSteps());
        }

        setUpViews();
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
