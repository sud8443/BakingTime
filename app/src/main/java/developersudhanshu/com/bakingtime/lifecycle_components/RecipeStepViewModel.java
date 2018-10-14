package developersudhanshu.com.bakingtime.lifecycle_components;

import android.arch.lifecycle.ViewModel;

import developersudhanshu.com.bakingtime.model.Step;

public class RecipeStepViewModel extends ViewModel {

    private Step mStepDetail;

    public RecipeStepViewModel(Step step) {
        mStepDetail = step;
    }

    public Step getStepDetail() {
        return mStepDetail;
    }
}
