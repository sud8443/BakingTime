package developersudhanshu.com.bakingtime.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("HELLO", "Remote views of ListView called");
        return new RecipeWidgetIngredientListFactory(this.getApplicationContext());
    }
}
