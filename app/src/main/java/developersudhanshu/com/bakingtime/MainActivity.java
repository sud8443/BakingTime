package developersudhanshu.com.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.util.ArrayList;

import developersudhanshu.com.bakingtime.adapters.RecipeRecyclerViewAdapter;
import developersudhanshu.com.bakingtime.model.RecipeData;
import developersudhanshu.com.bakingtime.model.RecipeDetails;
import developersudhanshu.com.bakingtime.model.RecipeResponse;
import developersudhanshu.com.bakingtime.networking.APIClient;
import developersudhanshu.com.bakingtime.networking.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mainRecipeList;
    private ArrayList<RecipeData> recipeDataArrayList;
    private RecipeRecyclerViewAdapter adapter;
    private ArrayList<RecipeDetails> recipeDetailsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeDataArrayList = new ArrayList<>();
        recipeDetailsArrayList = new ArrayList<>();

        setUpRecyclerView();

        fetchingRecipeDataAndUpdatingViews();
    }

    private void fetchingRecipeDataAndUpdatingViews() {
        APIInterface apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
        apiInterface.getRecipeData().enqueue(new Callback<ArrayList<RecipeResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeResponse>> call, Response<ArrayList<RecipeResponse>> response) {
                if(response.isSuccessful()){

                    // Iterating through the recipe data to get individual recipe's
                    recipeDataArrayList.clear();
                    recipeDetailsArrayList.clear();
                    for(RecipeResponse recipe: response.body()){
                        RecipeData recipeData = new RecipeData(recipe.getName(),
                                recipe.getSteps().size(), recipe.getServings());
                        recipeDataArrayList.add(recipeData);
                        recipeDetailsArrayList.add(new RecipeDetails(recipe.getName(),
                                recipe.getIngredients(), recipe.getSteps()));
                    }
                    setUpImagePaths();
                    adapter.notifyDataSetChanged();
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

    private void setUpImagePaths() {
        for (RecipeData recipe: recipeDataArrayList){
            if (recipe.getName().equals("Nutella Pie")){
                recipe.setImageResourceId(R.drawable.nutella_pie);
            }else if(recipe.getName().equals("Brownies")){
                recipe.setImageResourceId(R.drawable.brownies);
            }else if(recipe.getName().equals("Yellow Cake")){
                recipe.setImageResourceId(R.drawable.yellow_cake);
            }else if(recipe.getName().equals("Cheesecake")){
                recipe.setImageResourceId(R.drawable.cheese_cake);
            }
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
