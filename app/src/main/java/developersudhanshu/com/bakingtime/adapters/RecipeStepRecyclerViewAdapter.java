package developersudhanshu.com.bakingtime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import developersudhanshu.com.bakingtime.R;
import developersudhanshu.com.bakingtime.model.Step;

public class RecipeStepRecyclerViewAdapter extends RecyclerView.Adapter<RecipeStepRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Step> mSteps;

    public RecipeStepRecyclerViewAdapter(Context context, ArrayList<Step> steps) {
        mContext = context;
        mSteps = steps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recipe_step,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.stepNumber.setText(String.valueOf(position + 1));
        holder.stepDescription.setText(mSteps.get(position).getShortDescription());
        if (!TextUtils.isEmpty(mSteps.get(position).getVideoURL())){
            holder.watchVideo.setVisibility(View.VISIBLE);
            // Set an OnClickListener to open another activity to show the video playing in it
        }else{
            holder.watchVideo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stepNumber, stepDescription;
        Button watchVideo;

        public ViewHolder(View itemView) {
            super(itemView);
            stepNumber = itemView.findViewById(R.id.tv_step_number);
            stepDescription = itemView.findViewById(R.id.tv_step_desc);
            watchVideo = itemView.findViewById(R.id.btn_watch_video);
        }
    }
}
