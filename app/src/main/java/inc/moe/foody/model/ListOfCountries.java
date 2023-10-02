package inc.moe.foody.model;

import java.util.ArrayList;

public class ListOfCountries {
    ArrayList<Country> countries;

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public ListOfCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }
}
