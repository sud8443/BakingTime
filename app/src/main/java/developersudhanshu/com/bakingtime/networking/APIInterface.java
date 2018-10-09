package developersudhanshu.com.bakingtime.networking;

import java.util.ArrayList;

import developersudhanshu.com.bakingtime.model.RecipeResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by HP on 06-09-2018.
 */

public interface APIInterface {

    // Interface to define the API end points
    @GET("baking.json")
    Call<ArrayList<RecipeResponse>> getRecipeData();
}
