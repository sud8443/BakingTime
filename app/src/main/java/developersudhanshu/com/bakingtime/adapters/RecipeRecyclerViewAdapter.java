package developersudhanshu.com.bakingtime.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import developersudhanshu.com.bakingtime.R;
import developersudhanshu.com.bakingtime.model.RecipeData;

/**
 * Created by HP on 06-09-2018.
 */

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<RecipeData> mData;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecipeRecyclerViewAdapter(Context context, ArrayList<RecipeData> data) {
        this.mContext = context;
        this.mData = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_recipe_card, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.recipeName.setText(mData.get(position).getName());
        holder.recipeServings.setText(String.valueOf(mData.get(position).getServings()));
        holder.recipeSteps.setText(String.valueOf(mData.get(position).getSteps()));

        if (mData.get(position).getImageResourceId() != 0){
            holder.dishImage.setImageResource(mData.get(position).getImageResourceId());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName, recipeServings, recipeSteps;
        ImageView dishImage;

        public ViewHolder(View view){
            super(view);
            recipeName = view.findViewById(R.id.tv_recipe_name_main_recipe_list);
            recipeServings = view.findViewById(R.id.tv_recipe_servings_main_recipe_list);
            recipeSteps = view.findViewById(R.id.tv_recipe_steps_main_recipe_list);
            dishImage = view.findViewById(R.id.img_view_main_recipe_list);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
