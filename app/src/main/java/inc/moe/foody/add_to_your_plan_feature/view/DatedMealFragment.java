package inc.moe.foody.add_to_your_plan_feature.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import inc.moe.foody.R;
import inc.moe.foody.add_to_your_plan_feature.presenter.DatedMealPresenter;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.PlannedMeal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.DatedMealNetworkCallback;
import inc.moe.foody.network.MealClient;

public class DatedMealFragment extends Fragment implements IDatedMealView  , DatedMealNetworkCallback {
    private DatedMealPresenter datedMealPresenter;
    private ImageView mealImage;
    private TextView mealName , dateTextView  ,dayDatedTextView;
    private FloatingActionButton floatingActionButton;
    private PlannedMeal myPlannedMeal;
    private ChipGroup chipGroup;
    private Chip breakfastChip;
    private Chip lunchChip;
    private Chip dinnerChip;
    private String currentType;
    private String selectedType;
    public DatedMealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dated_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealImage =view.findViewById(R.id.meal_image_dated);
        mealName = view.findViewById(R.id.meal_name_dated);
        dateTextView = view.findViewById(R.id.dateTextId);
        dayDatedTextView = view.findViewById(R.id.dayDateTextId);
        chipGroup = view.findViewById(R.id.chipGroup);

        datedMealPresenter = new DatedMealPresenter(
                Repo.getInstance(MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext())) ,this);
        myPlannedMeal = DatedMealFragmentArgs.fromBundle(getArguments()).getPlannedMeal();
        datedMealPresenter.getMealById(myPlannedMeal.getIdMeal());
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        breakfastChip = view.findViewById(R.id.breakfast);
        dinnerChip = view.findViewById(R.id.dinner);
        lunchChip = view.findViewById(R.id.lunch);
        chipGroup.setSingleSelection(true);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, int checkedId) {
                Chip checkedChip = group.findViewById(checkedId);
                myPlannedMeal.setType(checkedChip.getText().toString());
                selectedType = checkedChip.getText().toString();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentType == null ) {
                    if(selectedType == null){
                        Snackbar snackbar = Snackbar.make(v , "you have to select a type" , Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }else{
                    datedMealPresenter.addPlannedMeal(myPlannedMeal);
                    }
                } else {
                    datedMealPresenter.removePlannedMeal(myPlannedMeal);
                }
            }
        });
    }

    @Override
    public void onFullDetailedMealFetch(Meal meal) {
        Glide.with(getView()).load(meal.getStrMealThumb()).error(R.drawable.error).into(mealImage);
        mealName.setText(meal.getStrMeal());
        int day = Integer.parseInt(myPlannedMeal.getDayOfMonth());
        int month = Integer.parseInt(myPlannedMeal.getMonth());
        String strDay = null;
        String strMonth = null;
        if(day<10){
            strDay = "0"+String.valueOf(day);
        }else{
            strDay = String.valueOf(day);
        }
        if(month<10){
            strMonth = "0"+String.valueOf(month);
        }else{
            strMonth =String.valueOf(month);
        }

        String dateString =strDay+"-"+strMonth+"-"+myPlannedMeal.getYear();

        // Create a DateTimeFormatter to parse the input string
        DateTimeFormatter formatter = null;
        String dayOfWeekName ="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate date = LocalDate.parse(dateString, formatter);

            dayOfWeekName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

        }
        dateTextView.setText(myPlannedMeal.getDayOfMonth() +" / " + myPlannedMeal.getMonth()+" / "+myPlannedMeal.getYear());
        dayDatedTextView.setText(dayOfWeekName);
        if(DatedMealFragmentArgs.fromBundle(getArguments()).getType().equals("default")){
            floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);

        }else{
            floatingActionButton.setImageResource(R.drawable.ic_baseline_delete_24);

            currentType =DatedMealFragmentArgs.fromBundle(getArguments()).getType();
            switch(currentType){
                case "breakfast":
                    breakfastChip.setChecked(true);
                    break;
                case "dinner":
                    dinnerChip.setChecked(true);

                    break;
                case "lunch":
                    lunchChip.setChecked(true);

                    break;

            }

        }

    }

    @Override
    public void onFullDetailedMealFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    @Override
    public void onSuccessAddPlannedMeal(String addedMessage) {

        Navigation.findNavController(getView()).navigate(DatedMealFragmentDirections.actionDatedMealFragmentToPlansFragment());
        Snackbar snackbar = Snackbar.make(getView() , addedMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();
        }

    @Override
    public void onSuccessAddPlanFb(String addedMessage) {
        Snackbar snackbar = Snackbar.make(getView() , addedMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onFailureAddPlanFB(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    @Override
    public void onSuccessRemovePlanFb(String message) {
        Snackbar snackbar = Snackbar.make(getView() , message, Snackbar.LENGTH_SHORT);
        snackbar.show();

    }
}
