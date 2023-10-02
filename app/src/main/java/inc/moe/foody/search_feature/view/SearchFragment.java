package inc.moe.foody.search_feature.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import inc.moe.foody.R;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.home_feature.view.OnImageClickListener;
import inc.moe.foody.home_feature.view.OnRandomMealClickListener;
import inc.moe.foody.home_feature.view.RandomMealAdapter;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;
import inc.moe.foody.search_feature.presenter.SearchPresenter;


public class SearchFragment extends Fragment implements ISearch  , OnRandomMealClickListener , OnImageClickListener {
    SearchPresenter searchPresenter ;
    ShimmerFrameLayout firstShimmer, secondShimmer;
    RecyclerView searchRV;
    LinearLayoutManager linearLayoutManager ;
    RandomMealAdapter searchAdapter;
    String categoryName;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstShimmer = view.findViewById(R.id.first_shimmer);
        secondShimmer = view.findViewById(R.id.second_shimmer);
        firstShimmer.startShimmerAnimation();
        secondShimmer.startShimmerAnimation();
        searchRV = view.findViewById(R.id.search_rv);
        linearLayoutManager = new LinearLayoutManager(getContext() );
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        searchAdapter = new RandomMealAdapter( this::insertToDatabase ,this);
        searchRV.setHasFixedSize(true);
        searchRV.setLayoutManager(linearLayoutManager);

        categoryName = SearchFragmentArgs.fromBundle(getArguments()).getCategoryName();
        Log.i("TAG", "onViewCreated: "+ categoryName);

        searchPresenter = new SearchPresenter(this ,
                Repo.getInstance( MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext())));
        searchPresenter.getMealsByCategoryOf(categoryName);



    }

    @Override
    public void searchByCategoryGotFromHome(List<Meal> meals) {
       if(meals != null){

           searchAdapter.setMealList(meals);
           searchAdapter.notifyDataSetChanged();
           searchRV.setAdapter(searchAdapter);
           secondShimmer.setVisibility(View.GONE);
           firstShimmer.setVisibility(View.GONE);
           searchRV.setVisibility(View.VISIBLE);
       }

    }

    @Override
    public void insertToDatabase(Meal meal) {

    }

    @Override
    public void navigateToFullDetailedMeal(String idMeal) {

    }
}