package inc.moe.foody.search_feature.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import inc.moe.foody.R;
import inc.moe.foody.home_feature.view.OnCategoryClickListener;
import inc.moe.foody.home_feature.view.OnCountryClickListener;
import inc.moe.foody.home_feature.view.OnImageClickListener;
import inc.moe.foody.model.Category;
import inc.moe.foody.model.DataSource;
import inc.moe.foody.model.Ingredient;
import inc.moe.foody.model.Meal;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    DataSource dataSource = new DataSource();

    List<Meal> meals;
    List<Category> categories;
    List<Meal> countries;
    List<Ingredient> ingredients;
    OnImageClickListener onImageClickListener;
    OnCategoryClickListener onCategoryClickListener;
    OnCountryClickListener onCountryClickListener;
    OnIngredientClickListener onIngredientClickListener;
    public SearchAdapter (){

    }

    public SearchAdapter (OnImageClickListener onImageClickListener , OnCategoryClickListener onCategoryClickListener, OnCountryClickListener onCountryClickListener ,OnIngredientClickListener onIngredientClickListener){
        this.onImageClickListener = onImageClickListener;
        this.onCategoryClickListener = onCategoryClickListener;
        this.onCountryClickListener = onCountryClickListener;
        this.onIngredientClickListener = onIngredientClickListener;


    }
    public void setMeals(List<Meal> meals) {

        this.meals = meals;
        this.categories = null;
        this.countries = null ;
        this.ingredients = null;
    }

    public void setCategories(List<Category> categories) {

        this.meals = null;
        this.categories = categories;
        this.countries = null ;
        this.ingredients = null;
    }

    public void setCountries(List<Meal> countries) {

        this.meals = null;
        this.categories = null;
        this.countries = countries ;
        this.ingredients = null;
    }

    public void setIngredients(List<Ingredient> ingredients) {

        this.meals = null;
        this.categories = null;
        this.countries = null ;
        this.ingredients = ingredients;
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
        Meal meal ;
        Meal country;
        Category category;
        Ingredient ingredient;
        if(meals!=null){
            meal =meals.get(position);
            holder.mealName.setText(meal.getStrMeal());
            Glide.with(holder.itemView).load(meal.getStrMealThumb())
                    .into(holder.mealImage);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImageClickListener.navigateToFullDetailedMeal(meal.getIdMeal());
                }
            });
        }else if(countries !=null){
            country = countries.get(position);
            holder.mealName.setText(country.getStrArea());
            holder.mealImage.setImageResource(dataSource.getImageResourceIdByName(country.getStrArea()));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCountryClickListener.searchByCountryName(country.getStrArea());
                }
            });
        }else if(categories != null ){
            category = categories.get(position);
            holder.mealName.setText(category.getStrCategory());
            Glide.with(holder.itemView).load(category.getStrCategoryThumb())
                    .into(holder.mealImage);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCategoryClickListener.searchByCategoryName(category.getStrCategory());
                }
            });
        }else{
            ingredient =ingredients.get(position);
            holder.mealName.setText(ingredient.getStrIngredient());
            Glide.with(holder.itemView).load("https://www.themealdb.com/images/ingredients/" + ingredient.getStrIngredient() + ".png")
                    .into(holder.mealImage);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onIngredientClickListener.searchMealsByIngredient(ingredient.getStrIngredient());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if(meals !=null){
            return meals.size();
        }else if(countries !=null ){
            return countries.size();
        }else if(categories != null) {
            return categories.size();
        }else if(ingredients != null ){

            Log.i("TAG", "getItemCount: " +ingredients.size());
            return ingredients.size();
        }

        Log.i("TAG", "getItemCount: " +ingredients.size());
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            mealName = itemView.findViewById(R.id.search_meal_name);
            mealImage = itemView.findViewById(R.id.searched_meal_image);
            cardView =itemView.findViewById(R.id.meal_card_search);
        }

    }
}
