package inc.moe.foody.search_feature.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import inc.moe.foody.R;
import inc.moe.foody.model.Meal;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    List<Meal> meals;
    public SearchAdapter (){

    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }


    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_item_layout , parent,false);
        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Meal meal =meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(holder.itemView).load(meal.getStrMealThumb())
                .into(holder.mealImage);

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            mealName = itemView.findViewById(R.id.meal_name);
            mealImage = itemView.findViewById(R.id.meal_image);
        }

    }
}
