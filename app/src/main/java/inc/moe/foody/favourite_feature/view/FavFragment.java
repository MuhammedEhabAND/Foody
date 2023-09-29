package inc.moe.foody.favourite_feature.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import inc.moe.foody.R;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.favourite_feature.presenter.FavouritePresenter;
import inc.moe.foody.favourite_feature.presenter.IFavouritePresenter;
import inc.moe.foody.home_feature.view.OnRandomMealClickListener;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;


public class FavFragment extends Fragment implements OnFavMealClickListener {
    RecyclerView favMealsRV;
    FavAdapter adapter;
    IFavouritePresenter favPresenter;
    GridLayoutManager layoutManager;
    ShimmerFrameLayout shimmerFrameLayout;

    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favMealsRV = view.findViewById(R.id.fav_rv);
        shimmerFrameLayout =view.findViewById(R.id.fav_shimmer);
        shimmerFrameLayout.startShimmerAnimation();
        layoutManager = new GridLayoutManager(getContext() ,2 );
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        adapter = new FavAdapter(this);
        favPresenter= new FavouritePresenter(
                Repo.getInstance( MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext())));


        favMealsRV.setHasFixedSize(true);
        favMealsRV.setLayoutManager(layoutManager);
        favPresenter.getFavMeals().observe(getActivity(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                adapter.setMeals(meals);
                adapter.notifyDataSetChanged();
                favMealsRV.setAdapter(adapter);
                shimmerFrameLayout.setVisibility(View.GONE);
                favMealsRV.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public void removeFromDB(Meal meal) {

        Snackbar snackbar = Snackbar.make(getView() ,meal.getStrMeal()+" deleted." ,Snackbar.LENGTH_SHORT);
        snackbar.show();
        favPresenter.removeFromDataBase(meal);

    }
}