package inc.moe.foody.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName ="planned_meal"  , primaryKeys = {"dayOfMonth" ,"type"})

public class PlannedMeal implements Parcelable {


    private String idMeal;
    @NonNull
    private String dayOfMonth;
    private String month;
    private String year;
    @NonNull
    private String type;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    protected PlannedMeal(Parcel in) {
        idMeal = in.readString();
        dayOfMonth = in.readString();
        month = in.readString();
        year = in.readString();
        type = in.readString();
    }

    public static final Creator<PlannedMeal> CREATOR = new Creator<PlannedMeal>() {
        @Override
        public PlannedMeal createFromParcel(Parcel in) {
            return new PlannedMeal(in);
        }

        @Override
        public PlannedMeal[] newArray(int size) {
            return new PlannedMeal[size];
        }
    };

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    @NonNull
    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(@NonNull String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    public PlannedMeal(String idMeal, @NonNull String dayOfMonth, String month, String year) {
        this.idMeal = idMeal;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

//    public PlannedMeal(String idMeal, @NonNull String dayOfMonth, String month, String year, @NonNull String type) {
//        this.idMeal = idMeal;
//        this.dayOfMonth = dayOfMonth;
//        this.month = month;
//        this.year = year;
//        this.type = type;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(idMeal);
        dest.writeString(dayOfMonth);
        dest.writeString(month);
        dest.writeString(year);
        dest.writeString(type);
    }
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("idMeal", idMeal);
        map.put("dayOfMonth", dayOfMonth);
        map.put("month", month);
        map.put("year", year);
        map.put("type", type);
        map.put("userId", userId);
        return map;
    }

}
