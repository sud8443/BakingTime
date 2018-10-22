package developersudhanshu.com.bakingtime.lifecycle_components;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = RecipeIngredientModel.class, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    public static RecipeDatabase sDBInstance;
    public static final String RECIPE_DATABASE_NAME = "RecipeDatabase";
    public static final Object LOCK = new Object();

    public static RecipeDatabase getInstance(Context context) {
        if (sDBInstance == null) {
            synchronized (LOCK) {
                sDBInstance = Room.databaseBuilder(context, RecipeDatabase.class,
                        RECIPE_DATABASE_NAME)
                        .build();
            }
        }
        return sDBInstance;
    }

    public abstract RecipeIngredientDao getRecipeDao();
}
