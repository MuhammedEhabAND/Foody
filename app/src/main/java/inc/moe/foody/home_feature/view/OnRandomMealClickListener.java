package inc.moe.foody.home_feature.view;

import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;

public interface OnRandomMealClickListener {
   void insertToDatabase(Meal meal);
}
