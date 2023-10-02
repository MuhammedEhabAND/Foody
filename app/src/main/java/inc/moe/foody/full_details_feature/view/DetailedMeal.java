package inc.moe.foody.full_details_feature.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import inc.moe.foody.R;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.full_details_feature.presenter.DetailedMealPresenter;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;
import inc.moe.foody.search_feature.view.SearchFragmentArgs;

public class DetailedMeal extends Fragment implements IDetailedMeal {
    private Scene instructionsScene , youtubeScene , ingredientsScene;
    private RadioGroup fullDetailedRadioGroup;
    private FrameLayout sceneManager;
    private TextView mealName , instructionsOfMeal , ingredientsOfMeal;
    private ImageView mealImage;
    private String idMeal;
    private DetailedMealPresenter detailedMealPresenter;
    private View instructionsScene1;

    public DetailedMeal() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_meal, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         sceneManager = view.findViewById(R.id.scene_manager);
        mealName = view.findViewById(R.id.meal_name_detailed);
        mealImage = view.findViewById(R.id.meal_image_detailed);
        idMeal = DetailedMealArgs.fromBundle(getArguments()).getIdMeal();

        detailedMealPresenter = new DetailedMealPresenter(this ,
                Repo.getInstance(MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext()))
        );

        detailedMealPresenter.getFullDetailedMeal(idMeal);

        fullDetailedRadioGroup = view.findViewById(R.id.full_detailed_radio_group);

        instructionsScene1 = LayoutInflater.from(getContext()).inflate(R.layout.instructions, null );
        youtubeScene = Scene.getSceneForLayout(sceneManager , R.layout.youtube, getContext());
        ingredientsScene = Scene.getSceneForLayout(sceneManager , R.layout.ingredients, getContext());



        sceneManager.addView(instructionsScene1);
        instructionsOfMeal = instructionsScene1.findViewById(R.id.instructions);
//
//        TransitionManager.go(instructionsScene);
//        fullDetailedRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton checkedRadioButton = group.findViewById(checkedId);
//
//                switchScene(checkedRadioButton.getText().toString());
//            }
//
//            private void switchScene(String tapName) {
//                Transition fade = new ChangeBounds();
//
//                switch (tapName){
//                    case "Instructions":
//                        TransitionManager.go(instructionsScene, fade);
//                        break;
//                    case "Youtube":
//                        TransitionManager.go(youtubeScene , fade);
//                        break;
//                    case "Ingredient":
//                        TransitionManager.go(ingredientsScene , fade);
//                        break;
//                    default:
//                        TransitionManager.go(instructionsScene);
//                }
//
//            }
//        });


    }

    @Override
    public void onFullDetailedMealFetch(Meal meal) {
        mealName.setText(meal.getStrMeal());
        Glide.with(getView()).load(meal.getStrMealThumb()).into(mealImage);
        instructionsOfMeal.setText(meal.getStrInstructions());
    }

    @Override
    public void onFullDetailedMealFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }
}