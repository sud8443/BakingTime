package developersudhanshu.com.bakingtime.lifecycle_components;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import developersudhanshu.com.bakingtime.model.Step;

public class RecipeStepViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Step mStepDetail;

    public RecipeStepViewModelFactory(Step step) {
        mStepDetail = step;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeStepViewModel(mStepDetail);
    }
}
