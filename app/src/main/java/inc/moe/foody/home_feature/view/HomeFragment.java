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

import inc.moe.foody.R;
import inc.moe.foody.home_feature.presenter.HomePresenter;
import inc.moe.foody.model.Category;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;


public class HomeFragment extends Fragment implements IView {
    RecyclerView allCategoriesRV;
    HomePresenter homePresenter;
    CategoryAdapter categoryAdapter;
    LinearLayoutManager layoutManager;

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
        layoutManager = new LinearLayoutManager(getContext()  );
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        categoryAdapter = new CategoryAdapter();

        homePresenter = new HomePresenter(this ,
                Repo.getInstance( MealClient.getInstance() ));


        allCategoriesRV.setHasFixedSize(true);
        allCategoriesRV.setLayoutManager(layoutManager);

        homePresenter.getAllCategories();

    }

    @Override
    public void onDataFetch(List<Category> categories) {
        categoryAdapter.setCategoryList(categories);
        categoryAdapter.notifyDataSetChanged();
        allCategoriesRV.setAdapter(categoryAdapter);
    }

    @Override
    public void onDataFetchFailed(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();

    }
}