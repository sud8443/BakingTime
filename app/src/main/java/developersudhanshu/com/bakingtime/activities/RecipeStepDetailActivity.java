package developersudhanshu.com.bakingtime.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import developersudhanshu.com.bakingtime.R;
import developersudhanshu.com.bakingtime.fragments.RecipeStepDetailFragment;
import developersudhanshu.com.bakingtime.model.Step;
import developersudhanshu.com.bakingtime.utility.Constants;

public class RecipeStepDetailActivity extends AppCompatActivity {

    // TODO: When this activity ends the previous activity should show the appropriate step in the view

    private int stepsWithVideo;
    private int stepChosenToWatch = -1;
    private ArrayList<Step> mSteps;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        if(getIntent() != null) {
            if (getIntent().hasExtra(Constants.RECIPE_STEP_CHOSEN_TO_WATCH_EXTRA_KEY))
                stepChosenToWatch = getIntent().getIntExtra(Constants.RECIPE_STEP_CHOSEN_TO_WATCH_EXTRA_KEY, -1);

            if (getIntent().hasExtra(Constants.RECIPE_STEP_DATA_EXTRA_KEY))
                mSteps = getIntent().getParcelableArrayListExtra(Constants.RECIPE_STEP_DATA_EXTRA_KEY);
        }

        if(mSteps != null)
            stepsWithVideo = mSteps.size();

        if (savedInstanceState == null) {
            // TODO: Did this because on Rotation the Activity gets recreated and first the same
            // fragment gets recreated too then then another fragment gets created and replaces the first one
            // (due to activity recreation) so to avoid this we will only add the fragment once and
            // next time the same fragment will be reused.
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.fl_act_recipe_step_detail,
                            new RecipeStepDetailFragment(mSteps.get(stepChosenToWatch))).
                    commit();
        }

    }

//    public class RecipeStepDetailPagerAdapter extends FragmentStatePagerAdapter {
//
//        public RecipeStepDetailPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return new RecipeStepDetailFragment(mSteps.get(position));
//        }
//
//        @Override
//        public int getCount() {
//            return stepsWithVideo;
//        }
//    }
}
