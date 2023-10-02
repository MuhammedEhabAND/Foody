package inc.moe.foody.model;

public class ImageData {
    private int resourceId;
    private String name;

    public ImageData(int resourceId, String name) {
        this.resourceId = resourceId;
        this.name = name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getName() {
        return name;
    }
}