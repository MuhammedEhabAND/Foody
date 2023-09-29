package inc.moe.foody.home_feature.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import inc.moe.foody.R;
import inc.moe.foody.home_feature.presenter.HomePresenter;
import inc.moe.foody.model.Category;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;


public class HomeFragment extends Fragment implements IView {
    RecyclerView allCategoriesRV;
    RecyclerView randomMealRV;
    HomePresenter homePresenter;
    CategoryAdapter categoryAdapter;
    RandomMealAdapter randomMealAdapter;
    LinearLayoutManager layoutManager;
    LinearLayoutManager layoutManager1;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allCategoriesRV = view.findViewById(R.id.categories_rv);
        randomMealRV = view.findViewById(R.id.random_meal_rv);
        layoutManager = new LinearLayoutManager(getContext()  );
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        layoutManager1 = new LinearLayoutManager(getContext()  );
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);

        categoryAdapter = new CategoryAdapter();
        randomMealAdapter = new RandomMealAdapter();

        homePresenter = new HomePresenter(this ,
                Repo.getInstance( MealClient.getInstance() ));


        allCategoriesRV.setHasFixedSize(true);
        allCategoriesRV.setLayoutManager(layoutManager);

        randomMealRV.setHasFixedSize(true);
        randomMealRV.setLayoutManager(layoutManager1);

        homePresenter.getAllCategories();
        //homePresenter.getRandomMeal();
    }

    @Override
    public void onCategoryFetch(List<Category> categories) {
        categoryAdapter.setCategoryList(categories);
        categoryAdapter.notifyDataSetChanged();
        allCategoriesRV.setAdapter(categoryAdapter);


    }

    @Override
    public void onRandomMealFetch(List<Meal> meals) {

//        randomMealAdapter.setMealList(meals);
//        randomMealAdapter.notifyDataSetChanged();
//        randomMealRV.setAdapter(randomMealAdapter);
}

    @Override
    public void onDataFetchFailed(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();

    }
}