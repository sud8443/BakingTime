package developersudhanshu.com.bakingtime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import developersudhanshu.com.bakingtime.fragments.RecipeStepDetailFragment;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int stepsWithVideo = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        Toast.makeText(this, "Watch the step on: " +
                getIntent().getStringExtra(Constants.RECIPE_STEP_URL_EXTRA_KEY),
                Toast.LENGTH_SHORT).show();
        viewPager = findViewById(R.id.view_pager_act_recipe_step_detail);

        viewPager.setAdapter(new RecipeStepDetailPagerAdapter(getSupportFragmentManager()));
    }

    public class RecipeStepDetailPagerAdapter extends FragmentStatePagerAdapter {

        public RecipeStepDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new RecipeStepDetailFragment();
        }

        @Override
        public int getCount() {
            return stepsWithVideo;
        }
    }
}
