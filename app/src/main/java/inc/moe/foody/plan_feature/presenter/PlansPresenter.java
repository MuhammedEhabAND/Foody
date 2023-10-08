package inc.moe.foody.plan_feature.presenter;

import android.os.Build;

import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.PlannedMeal;
import inc.moe.foody.model.PlansNetworkCallback;
import inc.moe.foody.plan_feature.view.IPlansView;

public class PlansPresenter implements IPlansPresenter , PlansNetworkCallback {


    private LocalDate selectedDate;

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    private IRepo iRepo;
    private IPlansView iPlansView;
    public PlansPresenter(LocalDate selectedDate , IRepo iRepo ,IPlansView iPlansView){
        this.selectedDate = selectedDate;
        this.iRepo = iRepo ;
        this.iPlansView = iPlansView;
    }

    public ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = null;
        int daysInMonth = 0;
        int dayOfWeek = 0;
        LocalDate firstOfMonth;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            yearMonth = YearMonth.from(date);
            daysInMonth = yearMonth.lengthOfMonth();

            firstOfMonth = selectedDate.withDayOfMonth(1);
            dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        }


        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }
    public String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date.format(formatter);
        }
        return "";
    }
    @Override
    public LiveData<List<PlannedMeal>> getPlannedMeal() {
        return iRepo.getAllPlannedMeal();
    }

    @Override
    public void getPlannedMealsFromFirebase() {
        iRepo.onGettingPlansFromFB(this);
    }

    @Override
    public void onGettingPlansSuccess(List<PlannedMeal> plannedMealList) {
       iPlansView.onGettingPlansSuccess(plannedMealList);
    }

    @Override
    public void onGettingPlansFailure(String errorMessage) {
        iPlansView.onGettingPlansFailure(errorMessage);
    }
}
