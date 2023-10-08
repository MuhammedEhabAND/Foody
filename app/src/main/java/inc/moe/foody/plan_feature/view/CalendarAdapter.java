package inc.moe.foody.plan_feature.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import inc.moe.foody.R;
import inc.moe.foody.model.MyPlannedMeals;
import inc.moe.foody.model.PlannedMeal;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{
    private final ArrayList<String> daysOfMonth;
    private final OnAddToPlanListener onAddToPlanListener;

    private List<String> usedDays;
    public CalendarAdapter(ArrayList<String> daysOfMonth, OnAddToPlanListener onAddToPlanListener, List<String> usedDays)
    {

        this.daysOfMonth = daysOfMonth;
        this.onAddToPlanListener = onAddToPlanListener;
        this.usedDays = usedDays;
    }

    @NonNull
    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.callender_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        CalendarAdapter.ViewHolder viewHolder = new CalendarAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.ViewHolder holder, int position) {

        holder.day.setText(daysOfMonth.get(position));
        for(String usedDay :usedDays){
            if(holder.day.getText().equals(usedDay)){
                holder.planedMeal.setVisibility(View.VISIBLE);
            }
        }
        holder.day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddToPlanListener.onItemClick(position ,daysOfMonth.get(position));
//                holder.planedMeal.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView day;
        private ImageView planedMeal;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            day = itemView.findViewById(R.id.cellDayText);
            planedMeal= itemView.findViewById(R.id.planned_meal);
            planedMeal.setImageResource(R.drawable.favourite_red);
        }

    }
}
