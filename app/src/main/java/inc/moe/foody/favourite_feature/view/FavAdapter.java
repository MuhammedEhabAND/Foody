package inc.moe.foody.favourite_feature.view;

import android.util.Log;
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
import inc.moe.foody.home_feature.view.OnImageClickListener;
import inc.moe.foody.home_feature.view.OnRandomMealClickListener;
import inc.moe.foody.model.Meal;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    List<Meal> meals;

    public void setMeals(List<Meal> meals) {
        this.meals= meals;

    }

    OnFavMealClickListener onMealClickListener;
    OnImageClickListener onImageClickListener;
    public FavAdapter(OnFavMealClickListener onMealClickListener ,OnImageClickListener onImageClickListener){
        this.onMealClickListener = onMealClickListener;
        this.onImageClickListener =onImageClickListener;
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fav_item_layout , parent,false);
        FavAdapter.ViewHolder viewHolder = new FavAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
        Meal meal =meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Log.i("TAG", "setMeals: " + meal.getIdMeal());

        Glide.with(holder.itemView).load(meal.getStrMealThumb())
                .into(holder.mealThumb);
        holder.mealThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClickListener.navigateToFullDetailedMeal(meal.getIdMeal());
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMealClickListener.removeFromDB(meal);

            }
        });
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealThumb;
        ImageView deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.favourite_meal_name);
            mealThumb = itemView.findViewById(R.id.fav_meal_image);
            deleteBtn = itemView.findViewById(R.id.delete_image);
        }
    }
}
