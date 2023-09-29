package inc.moe.foody.model;

import java.util.ArrayList;

public class ListOfCategories {
    private ArrayList<Category> categories ;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ListOfCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}

