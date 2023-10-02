package inc.moe.foody.home_feature.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import inc.moe.foody.R;
import inc.moe.foody.model.Meal;

public class AllMealsAdapter extends RecyclerView.Adapter<AllMealsAdapter.ViewHolder> {
    List<Meal> meals;

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        Log.i("TAG", "onBindViewHolder: " + meals.get(0).getStrMeal());

    }

    public AllMealsAdapter(){

    }


    @NonNull
    @Override
    public AllMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_item_layout , parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AllMealsAdapter.ViewHolder holder, int position) {
         holder.categoryName.setText(meals.get(position).getStrMeal());
        Glide.with(holder.itemView).load(meals.get(position).getStrMealThumb()).apply(new RequestOptions().override(300,250))

                .into(holder.categoryImage);

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage ;
        TextView categoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryImage = itemView.findViewById(R.id.category_image);
        }
    }
}
