package developersudhanshu.com.bakingtime.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import developersudhanshu.com.bakingtime.R;
import developersudhanshu.com.bakingtime.adapters.RecipeRecyclerViewAdapter;
import developersudhanshu.com.bakingtime.lifecycle_components.AppExecutors;
import developersudhanshu.com.bakingtime.lifecycle_components.RecipeDatabase;
import developersudhanshu.com.bakingtime.lifecycle_components.RecipeIngredientModel;
import developersudhanshu.com.bakingtime.model.Ingredient;
import developersudhanshu.com.bakingtime.model.RecipeData;
import developersudhanshu.com.bakingtime.model.RecipeDetails;
import developersudhanshu.com.bakingtime.model.RecipeResponse;
import developersudhanshu.com.bakingtime.networking.APIClient;
import developersudhanshu.com.bakingtime.networking.APIInterface;
import developersudhanshu.com.bakingtime.utility.Constants;
import developersudhanshu.com.bakingtime.utility.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mainRecipeList;
    private ArrayList<RecipeData> recipeDataArrayList;
    private RecipeRecyclerViewAdapter adapter;
    private ArrayList<RecipeDetails> recipeDetailsArrayList;
    private RecipeDatabase mDb;
    private AppExecutors executors;
    private CardView loadingLayoutMainScreen;
    private Parcelable recyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeDataArrayList = new ArrayList<>();
        recipeDetailsArrayList = new ArrayList<>();
        loadingLayoutMainScreen = findViewById(R.id.loading_layout_main_screen);
        loadingLayoutMainScreen.setVisibility(View.VISIBLE);

        setUpRecyclerView();

        fetchingRecipeDataAndUpdatingViews();
        mDb = RecipeDatabase.getInstance(this);
        executors = AppExecutors.getsInstance(this);
    }

    private void fetchingRecipeDataAndUpdatingViews() {
        APIInterface apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
        apiInterface.getRecipeData().enqueue(new Callback<ArrayList<RecipeResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeResponse>> call, final Response<ArrayList<RecipeResponse>> response) {
                if(response.isSuccessful()){

                    // Iterating through the recipe data to get individual recipe's
                    loadingLayoutMainScreen.setVisibility(View.GONE);
                    recipeDataArrayList.clear();
                    recipeDetailsArrayList.clear();
                    for(RecipeResponse recipe: response.body()){
                        RecipeData recipeData = new RecipeData(recipe.getName(),
                                recipe.getSteps().size(), recipe.getServings());
                        recipeDataArrayList.add(recipeData);
                        recipeDetailsArrayList.add(new RecipeDetails(recipe.getId(),
                                recipe.getName(),
                                recipe.getIngredients(), recipe.getSteps()));
                    }
                    setUpImagePaths();
                    adapter.notifyDataSetChanged();

                    if (recyclerViewState != null) {
                        mainRecipeList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                    }

                    // Saving the recipes to the database
                    if (Utility.isFirstLaunch(MainActivity.this)) {
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                for (RecipeResponse recipe: response.body()) {
                                    for (Ingredient ingredient : recipe.getIngredients()) {
                                        mDb.getRecipeDao().insertRecipe(
                                                new RecipeIngredientModel(recipe.getId(), recipe.getName(),
                                                        ingredient.getQuantity(), ingredient.getMeasure(),
                                                        ingredient.getIngredient()));
                                    }
                                }
                            }
                        });
                        Utility.setFirstLaunch(MainActivity.this, false);
                    }
                }else{
                    Log.v("RESPONSE", "Response failed");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeResponse>> call, Throwable t) {
                Log.v("RESPONSE", "in onFailure, message: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (recyclerViewState != null) {
            mainRecipeList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        recyclerViewState = mainRecipeList.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(Constants.RECYCLER_VIEW_STATE_KEY, recyclerViewState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        recyclerViewState = savedInstanceState.getParcelable(Constants.RECYCLER_VIEW_STATE_KEY);
    }

    private void setUpImagePaths() {
        for (RecipeData recipe: recipeDataArrayList){
            recipe.setImageResourceId(Constants.RECIPE_IMAGE_HASH_MAP.get(recipe.getName()));
        }
    }

    // Method to setup Recycler view, add Adapters and any animation if required
    private void setUpRecyclerView() {
        mainRecipeList = findViewById(R.id.rv_recipe_list);

        adapter = new RecipeRecyclerViewAdapter(this, recipeDataArrayList);

        mainRecipeList.setAdapter(adapter);
        mainRecipeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Setting onItemClick listener on the item
        adapter.setOnItemClickListener(new RecipeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                if(recipeDetailsArrayList.get(position) != null) {
                    Intent recipeDetailIntent = new Intent(MainActivity.this, RecipeDetailActivity.class);
                    recipeDetailIntent.putExtra(Constants.RECIPE_DATA_EXTRA_KEY, recipeDetailsArrayList.get(position));
                    startActivity(recipeDetailIntent);
                }
            }
        });
    }
}
