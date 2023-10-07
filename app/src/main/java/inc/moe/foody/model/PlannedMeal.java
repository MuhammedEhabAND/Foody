package inc.moe.foody.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PlannedMeal implements Parcelable {



    private String idMeal;
    private String dayOfMonth;
    private String month;
    private String year;

    protected PlannedMeal(Parcel in) {
        idMeal = in.readString();
        dayOfMonth = in.readString();
        month = in.readString();
        year = in.readString();
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

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
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

    public PlannedMeal(String idMeal, String dayOfMonth, String month, String year) {
        this.idMeal = idMeal;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

    public PlannedMeal() {
    }

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
    }
}
