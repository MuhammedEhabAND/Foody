package inc.moe.foody.plan_feature.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import inc.moe.foody.R;
import inc.moe.foody.auth_feature.view.MainActivity;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.model.MyPlannedMeals;
import inc.moe.foody.model.PlannedMeal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;
import inc.moe.foody.plan_feature.presenter.PlansPresenter;

public class PlansFragment extends Fragment implements OnAddToPlanListener, IPlansView {
    private RecyclerView calendarRecyclerView ;
    private TextView monthYearText;
    private Button previousBtn ,forwardBtn;
    private LocalDate selectedDate;
    private CalendarAdapter calendarAdapter;
    private PlansPresenter plansPresenter ;
    private DatabaseReference userDatabase;
    FirebaseAuth firebaseAuth ;
    private List<PlannedMeal> myPlannedMeals ;
    FirebaseUser currentUser;
    boolean isUser = false;
    public PlansFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plans, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = LocalDate.now();
            plansPresenter = new PlansPresenter(selectedDate ,
                    Repo.getInstance(MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext())) , this);
        }
        if(FirebaseAuth.getInstance()!= null){
            firebaseAuth = FirebaseAuth.getInstance();
            currentUser = firebaseAuth.getCurrentUser();
            isUser = true;

        }else{
            isUser = false;
        }
        if(isUser){
            myPlannedMeals =new ArrayList<>();
            plansPresenter.getPlannedMeal().observe(getViewLifecycleOwner(), new Observer<List<PlannedMeal>>() {
                @Override
                public void onChanged(List<PlannedMeal> plannedMeals) {
                    if (plannedMeals.size()==0){
                        plansPresenter.getPlannedMealsFromFirebase();
                    }
                    for(PlannedMeal plannedMeal :plannedMeals){
                        if(plannedMeal.getUserId().equals(currentUser.getUid())){
                            myPlannedMeals.add(plannedMeal);
                            setMonthView();
                        }
                    }
                }
            });
        }
        initUI();
        setMonthView();



//        setMonthView();


    }
    private void initUI()
    {


        calendarRecyclerView = getView().findViewById(R.id.calendarRecyclerView);
        monthYearText = getView().findViewById(R.id.monthYearTV);

    }
    private void setMonthView()
    {

        monthYearText.setText(plansPresenter.monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = plansPresenter.daysInMonthArray(selectedDate);
        ArrayList<String> daysUsed = new ArrayList<>();
        for(PlannedMeal plannedMeal : myPlannedMeals){
                daysUsed.add(plannedMeal.getDayOfMonth());
        }
        calendarAdapter = new CalendarAdapter(daysInMonth, this  , daysUsed);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
            String idMeal = PlansFragmentArgs.fromBundle(getArguments()).getIdMeal();
            if(!idMeal.equals("default")){
                String message = "Selected Favourite " + dayText + " " + plansPresenter.monthYearFromDate(selectedDate);
                PlannedMeal plannedMeal =new PlannedMeal(
                        idMeal,
                        dayText,
                        String.valueOf(plansPresenter.getSelectedDate().getMonthValue()),
                        String.valueOf(plansPresenter.getSelectedDate().getYear()));
                PlansFragmentDirections.ActionPlansFragmentToDatedMealFragment actionPlansFragmentToDatedMealFragment =
                        PlansFragmentDirections.actionPlansFragmentToDatedMealFragment(plannedMeal);
                actionPlansFragmentToDatedMealFragment.setPlannedMeal(plannedMeal);
                actionPlansFragmentToDatedMealFragment.setType("default");
                Navigation.findNavController(getView()).navigate(actionPlansFragmentToDatedMealFragment);
            }else{
                for(PlannedMeal plannedMeal :myPlannedMeals){
                    if(dayText.equals(plannedMeal.getDayOfMonth())){
                        PlansFragmentDirections.ActionPlansFragmentToDatedMealFragment actionPlansFragmentToDatedMealFragment =
                                PlansFragmentDirections.actionPlansFragmentToDatedMealFragment(plannedMeal);

                        actionPlansFragmentToDatedMealFragment.setPlannedMeal(plannedMeal);
                        actionPlansFragmentToDatedMealFragment.setType(plannedMeal.getType());
                        Navigation.findNavController(getView()).navigate(actionPlansFragmentToDatedMealFragment);

                    }
                }
            }
        }
    }

    @Override
    public void onGettingPlansSuccess(List<PlannedMeal> plannedMealList) {
            myPlannedMeals = plannedMealList;
        ArrayList<String> daysInMonth = plansPresenter.daysInMonthArray(selectedDate);
        ArrayList<String> daysUsed = new ArrayList<>();
        for(PlannedMeal plannedMeal : plannedMealList){
            Log.i("TAG", "onGettingPlansSuccess: " + plannedMeal.getDayOfMonth());
            daysUsed.add(plannedMeal.getDayOfMonth());
        }
        calendarAdapter = new CalendarAdapter(daysInMonth, this  , daysUsed);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

//        setMonthView();
    }


    @Override
    public void onGettingPlansFailure(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }
}