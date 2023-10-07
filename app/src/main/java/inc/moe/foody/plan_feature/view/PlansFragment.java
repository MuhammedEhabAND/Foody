package inc.moe.foody.plan_feature.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;

import inc.moe.foody.R;
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
    MyPlannedMeals myPlannedMeals;
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
        myPlannedMeals =new MyPlannedMeals();
        initUI();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = LocalDate.now();
            plansPresenter = new PlansPresenter(selectedDate ,
                    Repo.getInstance(MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext())) , this);
        }
        setMonthView();


    }
    private void initUI()
    {


        calendarRecyclerView = getView().findViewById(R.id.calendarRecyclerView);
        monthYearText = getView().findViewById(R.id.monthYearTV);
        previousBtn = getView().findViewById(R.id.back_btn);
        forwardBtn = getView().findViewById(R.id.forward_btn);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plansPresenter.onPreviousButtonPressed();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    selectedDate.minusMonths(1);
                }
                setMonthView();
            }
        });
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plansPresenter.onForwardButtonPressed();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    selectedDate.plusMonths(1);
                }
                setMonthView();

            }
        });
    }
    private void setMonthView()
    {

        monthYearText.setText(plansPresenter.monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = plansPresenter.daysInMonthArray(selectedDate);

        calendarAdapter = new CalendarAdapter(daysInMonth, this , myPlannedMeals.getPlannedMeals());
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
                Navigation.findNavController(getView()).navigate(actionPlansFragmentToDatedMealFragment);
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}