package inc.moe.foody.add_to_your_plan_feature.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.time.DayOfWeek;
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
import inc.moe.foody.network.MealClient;
import inc.moe.foody.plan_feature.presenter.PlansPresenter;
import inc.moe.foody.plan_feature.view.IPlansView;

public class DatedMealFragment extends Fragment implements IDatedMealView  {
    private DatedMealPresenter datedMealPresenter;
    private ImageView mealImage;
    private TextView mealName , dateTextView  ,dayDatedTextView;
    PlannedMeal myPlannedMeal;
    LocalDate selectedDate;
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

        datedMealPresenter = new DatedMealPresenter(
                Repo.getInstance(MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext())) ,this);
        myPlannedMeal = DatedMealFragmentArgs.fromBundle(getArguments()).getPlannedMeal();
        datedMealPresenter.getFullDetailedMeal(myPlannedMeal.getIdMeal());
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
    }

    @Override
    public void onFullDetailedMealFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }
}