package inc.moe.foody.full_details_feature.view;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import inc.moe.foody.R;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.full_details_feature.presenter.DetailedMealPresenter;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;
import io.reactivex.rxjava3.core.Observable;

public class DetailedMeal extends Fragment implements IDetailedMeal {
    private RadioGroup fullDetailedRadioGroup;
    private TextView mealName , mealInstructions, mealIngredeients ;
    private YouTubePlayerView mealYoutubeVideo;
    private ImageView mealImage;
    private CardView mealCard;
    private ScrollView scrollView;
    private String idMeal;
    private DetailedMealPresenter detailedMealPresenter;
    private ShimmerFrameLayout imageShimmer, scrollViewShimmer;
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
        mealCard = view.findViewById(R.id.meal_card);
        mealName = view.findViewById(R.id.meal_name_detailed);
        mealImage = view.findViewById(R.id.meal_image_detailed);
        scrollView = view.findViewById(R.id.scroll_view);
        mealInstructions = view.findViewById(R.id.instructions);
        mealIngredeients = view.findViewById(R.id.ingredient_text_view);
        mealYoutubeVideo = view.findViewById(R.id.youtube_video);

        imageShimmer= view.findViewById(R.id.image_shimmer);
        scrollViewShimmer= view.findViewById(R.id.scroll_view_shimmer);
        imageShimmer.startShimmerAnimation();
        scrollViewShimmer.startShimmerAnimation();

        idMeal = DetailedMealArgs.fromBundle(getArguments()).getIdMeal();

        detailedMealPresenter = new DetailedMealPresenter(this ,
                Repo.getInstance(MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext()))
        );

        detailedMealPresenter.getFullDetailedMeal(idMeal);

        fullDetailedRadioGroup = view.findViewById(R.id.full_detailed_radio_group);




        fullDetailedRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                switch (checkedRadioButton.getText().toString()){
                    case "Instructions":
                        mealInstructions.setVisibility(View.VISIBLE);
                        mealIngredeients.setVisibility(View.INVISIBLE);
                        mealYoutubeVideo.setVisibility(View.INVISIBLE);
                        break;
                    case "Ingredient":
                        mealInstructions.setVisibility(View.INVISIBLE);
                        mealIngredeients.setVisibility(View.VISIBLE);
                        mealYoutubeVideo.setVisibility(View.INVISIBLE);
                        break;
                    case "Youtube":
                        mealInstructions.setVisibility(View.INVISIBLE);
                        mealIngredeients.setVisibility(View.INVISIBLE);
                        mealYoutubeVideo.setVisibility(View.VISIBLE);
                        break;



                }


            }



        });


    }

    @Override
    public void onFullDetailedMealFetch(Meal meal) {
        mealName.setText(meal.getStrMeal());
        Glide.with(getView()).load(meal.getStrMealThumb()).into(mealImage);
        scrollView.setVisibility(View.VISIBLE);
        mealCard.setVisibility(View.VISIBLE);
        scrollViewShimmer.setVisibility(View.GONE);
        imageShimmer.setVisibility(View.GONE);

        StringBuilder stringBuilder = new StringBuilder();

// Iterate through the ingredients and measures
        for (int i = 1; i <= 20; i++) {
            String ingredient = meal.getStrIngredient(i);
            String measure = meal.getStrMeasure(i);

            // Check if both ingredient and measure are not null
            if (!ingredient.isEmpty() && !measure.isEmpty()) {
                stringBuilder.append(measure).append(" : ").append(ingredient).append("\n");
            }
        }

        String mealIngredients = stringBuilder.toString();
        setDataForScenes(meal.getStrInstructions() , meal.getStrYoutube() , mealIngredients );

    }
    private void setDataForScenes(String instructions , String youtubeLink , String ingredients) {

        mealInstructions.setText(instructions);
        mealIngredeients.setText(ingredients);

        getLifecycle().addObserver(mealYoutubeVideo);

        mealYoutubeVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String[] parts = youtubeLink.split("\\?v=");
                if (parts.length >= 2) {
                    String query_string = parts[1];
                    youTubePlayer.loadVideo(query_string, 0);

                }
            }
        });
    }


    @Override
    public void onFullDetailedMealFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }
}