package inc.moe.foody.home_feature.view;

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
import inc.moe.foody.model.Category;
import inc.moe.foody.model.Meal;

public class RandomMealAdapter extends RecyclerView.Adapter<RandomMealAdapter.ViewHolder> {
        List<Meal> mealList;
        OnRandomMealClickListener onMealClickListener;
        OnImageClickListener onImageClickListener;

public void setMealList(List<Meal> mealList) {

    this.mealList = mealList;
        }

public RandomMealAdapter(OnRandomMealClickListener onMealClickListener , OnImageClickListener onImageClickListener) {
    this.onMealClickListener = onMealClickListener;
    this.onImageClickListener =onImageClickListener;
}

@NonNull
@Override
public RandomMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.daily_inspiration_item_layout , parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
        }

@Override
public void onBindViewHolder(@NonNull RandomMealAdapter.ViewHolder holder, int position) {
            Meal meal = mealList.get(position);
            holder.mealName.setText(meal.getStrMeal());
            Glide.with(holder.itemView).load(meal.getStrMealThumb())
                    .into(holder.mealThumb);
            holder.mealThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImageClickListener.navigateToFullDetailedMeal(meal.getIdMeal());
                }
            });
            holder.favImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMealClickListener.insertToDatabase(meal);
                    holder.favImage.setImageResource(R.drawable.favourite_red);
                }
            });

}


@Override
public int getItemCount() {
        return mealList.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView mealName ;
    ImageView mealThumb;
    ImageView favImage;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mealName = itemView.findViewById(R.id.meal_name);
        mealThumb = itemView.findViewById(R.id.meal_image);
        favImage = itemView.findViewById(R.id.favImage);

    }
}
}

