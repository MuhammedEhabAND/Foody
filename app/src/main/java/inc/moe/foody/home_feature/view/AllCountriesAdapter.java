package inc.moe.foody.home_feature.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import inc.moe.foody.R;
import inc.moe.foody.model.Country;
import inc.moe.foody.model.DataSource;
import inc.moe.foody.model.ImageData;
import inc.moe.foody.model.Meal;

public class AllCountriesAdapter extends RecyclerView.Adapter<AllCountriesAdapter.ViewHolder> {
    DataSource dataSource = new DataSource();
    List<Meal> countries;
    OnCountryClickListener onCountryClickListener;
    public AllCountriesAdapter(OnCountryClickListener onCountryClickListener){
        this.onCountryClickListener = onCountryClickListener;
    }

    public void setCountries(List<Meal> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public AllCountriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.country_item_layout , parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AllCountriesAdapter.ViewHolder holder, int position) {
        Meal country = countries.get(position);
        holder.countryName.setText(country.getStrArea());
        holder.countryImage.setImageResource(dataSource.getImageResourceIdByName(country.getStrArea()));
        holder.countryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCountryClickListener.searchByCountryName(country.getStrArea());
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryName ;
        ImageView countryImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country_name);
            countryImage = itemView.findViewById(R.id.country_image);

        }
    }
}
