package inc.moe.foody.full_details_feature.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import inc.moe.foody.R;
import inc.moe.foody.auth_feature.view.MainActivity;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.full_details_feature.presenter.DetailedMealPresenter;
import inc.moe.foody.home_feature.view.OnRandomMealClickListener;
import inc.moe.foody.model.DataSource;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;
import inc.moe.foody.utils.Cache;
import inc.moe.foody.utils.ShowingSnackbar;

public class DetailedMeal extends Fragment implements IDetailedMeal  {
    private RadioGroup fullDetailedRadioGroup;
    private TextView mealName ,mealCountryName, mealInstructions, mealIngredeients ;
    private YouTubePlayerView mealYoutubeVideo;
    private ImageView mealImage , mealCountryImage;
    private CardView mealCard;
    private ScrollView scrollView;
    private String idMeal;
    private DetailedMealPresenter detailedMealPresenter;
    private ShimmerFrameLayout imageShimmer, scrollViewShimmer;
    private Button addToFavBtn , addToYourPlanBtn;
    private DetailedMealDirections.ActionDetailedMealToPlansFragment action;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    View myView;
    boolean isUser = false;
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
        myView = view;
        if(FirebaseAuth.getInstance().getCurrentUser()!= null){
            firebaseAuth =FirebaseAuth.getInstance();
            currentUser =firebaseAuth.getCurrentUser();
            isUser = true;
        }else{
            isUser = false;
        }
        mealCard = view.findViewById(R.id.meal_card);
        mealName = view.findViewById(R.id.meal_name_detailed);
        mealImage = view.findViewById(R.id.meal_image_detailed);
        mealCountryName = view.findViewById(R.id.meal_country_name);
        mealCountryImage = view.findViewById(R.id.origin_country);
        scrollView = view.findViewById(R.id.scroll_view);
        mealInstructions = view.findViewById(R.id.instructions);
        mealIngredeients = view.findViewById(R.id.ingredient_text_view);
        mealYoutubeVideo = view.findViewById(R.id.youtube_video);
        addToFavBtn = view.findViewById(R.id.add_to_fav);
        addToYourPlanBtn = view.findViewById(R.id.add_to_your_plan);

            addToYourPlanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isUser){

                    action =DetailedMealDirections.actionDetailedMealToPlansFragment();

                    detailedMealPresenter.addToPlans();
                    }else{
                        new MaterialAlertDialogBuilder(getContext())
                                .setTitle("Oops")
                                .setMessage("It seems that you haven't logged in yet ,Umm what are waiting for?")
                                .setNegativeButton("Cancel", (dialog, which) -> {
                                    // Respond to negative button press
                                })
                                .setPositiveButton("Log in", (dialog, which) -> {
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                })
                                .show();

                    }

                }
            });

        addToFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUser){

                detailedMealPresenter.insertMealToFav();
//                Snackbar snackbar = Snackbar.make(v , Cache.getInstance().getCurrentMeal().getStrMeal() + " saved." , Snackbar.LENGTH_SHORT);
//                snackbar.show();
                }else{
                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("Oops")
                            .setMessage("It seems that you haven't logged in yet ,Umm what are waiting for?")
                            .setNegativeButton("Cancel", (dialog, which) -> {
                                // Respond to negative button press
                             })
                            .setPositiveButton("Log in", (dialog, which) -> {
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            })
                            .show();

                }

            }
        });
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

                        mealCountryImage.setVisibility(View.INVISIBLE);
                        mealCountryName.setVisibility(View.INVISIBLE);
                        break;
                    case "Ingredient":
                        mealInstructions.setVisibility(View.INVISIBLE);
                        mealIngredeients.setVisibility(View.VISIBLE);
                        mealYoutubeVideo.setVisibility(View.INVISIBLE);

                        mealCountryImage.setVisibility(View.INVISIBLE);
                        mealCountryName.setVisibility(View.INVISIBLE);
                        break;
                    case "Youtube":
                        mealInstructions.setVisibility(View.INVISIBLE);
                        mealIngredeients.setVisibility(View.INVISIBLE);
                        mealYoutubeVideo.setVisibility(View.VISIBLE);
                        mealCountryImage.setVisibility(View.VISIBLE);
                        mealCountryName.setVisibility(View.VISIBLE);
                        break;



                }


            }



        });


    }
    @Override
    public void onFullDetailedMealFetch(Meal meal) {
        mealName.setText(meal.getStrMeal());

        Glide.with(myView).load(meal.getStrMealThumb()).into(mealImage);
        scrollView.setVisibility(View.VISIBLE);
        mealCard.setVisibility(View.VISIBLE);
        scrollViewShimmer.setVisibility(View.GONE);
        imageShimmer.setVisibility(View.GONE);
        DataSource dataSource = new DataSource();
        mealCountryImage.setImageResource(dataSource.getImageResourceIdByName(meal.getStrArea()));
        mealCountryName.setText(meal.getStrArea());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= 20; i++) {
            String ingredient = meal.getStrIngredient(i);
            String measure = meal.getStrMeasure(i);

            // Check if both ingredient and measure are not null
            if (ingredient!= null && measure!=null) {
                if(!ingredient.isEmpty() && !measure.isEmpty())
                stringBuilder.append(measure).append(" : ").append(ingredient).append("\n");
            }
        }

        String mealIngredients = stringBuilder.toString();
        setDataForScenes(meal.getStrInstructions() , meal.getStrYoutube() , mealIngredients  );

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
        ShowingSnackbar.showSnackbar(getView() , errorMessage);
    }

    @Override
    public void navigateToCalendarSuccess(String idMeal) {
        action.setIdMeal(idMeal);
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void navigateToCalendarFailure(String errorMessage) {
        ShowingSnackbar.showSnackbar(getView(), errorMessage);
    }

    @Override
    public void onAddedToFavFBSuccess(String addedMessage) {
        ShowingSnackbar.showSnackbar(getView() ,addedMessage);
    }

    @Override
    public void onAddedToFavFBFailure(String errorMessage) {
        ShowingSnackbar.showSnackbar(getView() , errorMessage);
    }

}