package inc.moe.foody.model;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

import inc.moe.foody.R;


public class DataSource {
    public ArrayList<ImageData> getFlags() {
        ArrayList<ImageData> flags = new ArrayList<>();

        // Add the flags to the ArrayList
        flags.add(new ImageData(R.drawable.american, "American"));
        flags.add(new ImageData(R.drawable.british, "British"));
        flags.add(new ImageData(R.drawable.canidian, "Canadian"));
        flags.add(new ImageData(R.drawable.french, "French"));
        flags.add(new ImageData(R.drawable.chinese, "Chinese"));
        flags.add(new ImageData(R.drawable.croatian, "Croatian"));
        flags.add(new ImageData(R.drawable.dutch, "Dutch"));
        flags.add(new ImageData(R.drawable.egyptian, "Egyptian"));
        flags.add(new ImageData(R.drawable.filipino, "Filipino"));
        flags.add(new ImageData(R.drawable.greek, "Greek"));
        flags.add(new ImageData(R.drawable.indian, "Indian"));
        flags.add(new ImageData(R.drawable.irish, "Irish"));
        flags.add(new ImageData(R.drawable.italian, "italian"));
        flags.add(new ImageData(R.drawable.jamaican, "Jamaican"));
        flags.add(new ImageData(R.drawable.japan, "Japanese"));
        flags.add(new ImageData(R.drawable.kenyan, "Kenyan"));
        flags.add(new ImageData(R.drawable.malaysian, "Malaysian"));
        flags.add(new ImageData(R.drawable.mexican, "Mexican"));
        flags.add(new ImageData(R.drawable.moroccan, "Moroccan"));
        flags.add(new ImageData(R.drawable.polish, "Polish"));
        flags.add(new ImageData(R.drawable.portuguese, "Portuguese"));
        flags.add(new ImageData(R.drawable.russian, "Russian"));
        flags.add(new ImageData(R.drawable.spanish, "Spanish"));
        flags.add(new ImageData(R.drawable.thai, "Thai"));
        flags.add(new ImageData(R.drawable.tunisian, "Tunisian"));
        flags.add(new ImageData(R.drawable.turkish, "Turkish"));
        flags.add(new ImageData(R.drawable.vietnamese, "Vietnamese"));
        return flags;
    }
    public int getImageResourceIdByName(String name) {
        ArrayList<ImageData> flags = getFlags();

        for (ImageData imageData : flags) {
            if (imageData.getName().equalsIgnoreCase(name)) {
                return imageData.getResourceId();
            }
        }

        // If the name is not found, return a default resource ID or handle the error as needed
        return R.drawable.thai; // Replace with your default image resource ID
    }
}