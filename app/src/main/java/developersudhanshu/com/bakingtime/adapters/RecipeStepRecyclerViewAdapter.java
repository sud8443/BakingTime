package developersudhanshu.com.bakingtime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    private OnItemClickListener listener;
    private static int lastClickedPosition = -1;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.stepNumber.setText(String.valueOf(position + 1));
        holder.stepDescription.setText(mSteps.get(position).getShortDescription());
        holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_dark_background));
                if (lastClickedPosition != -1)
                    notifyItemChanged(lastClickedPosition);
                lastClickedPosition = position;
                listener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stepNumber, stepDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            stepNumber = itemView.findViewById(R.id.tv_step_number);
            stepDescription = itemView.findViewById(R.id.tv_step_desc);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
