
package ht.pq.khanh.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp implements Parcelable {

    @SerializedName("day")
    @Expose
    private Double day;
    @SerializedName("min")
    @Expose
    private Double min;
    @SerializedName("max")
    @Expose
    private Double max;
    @SerializedName("night")
    @Expose
    private Double night;
    @SerializedName("eve")
    @Expose
    private Double eve;
    @SerializedName("morn")
    @Expose
    private Double morn;

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getNight() {
        return night;
    }

    public void setNight(Double night) {
        this.night = night;
    }

    public Double getEve() {
        return eve;
    }

    public void setEve(Double eve) {
        this.eve = eve;
    }

    public Double getMorn() {
        return morn;
    }

    public void setMorn(Double morn) {
        this.morn = morn;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.day);
        dest.writeValue(this.min);
        dest.writeValue(this.max);
        dest.writeValue(this.night);
        dest.writeValue(this.eve);
        dest.writeValue(this.morn);
    }

    public Temp() {
    }

    protected Temp(Parcel in) {
        this.day = (Double) in.readValue(Double.class.getClassLoader());
        this.min = (Double) in.readValue(Double.class.getClassLoader());
        this.max = (Double) in.readValue(Double.class.getClassLoader());
        this.night = (Double) in.readValue(Double.class.getClassLoader());
        this.eve = (Double) in.readValue(Double.class.getClassLoader());
        this.morn = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Temp> CREATOR = new Parcelable.Creator<Temp>() {
        @Override
        public Temp createFromParcel(Parcel source) {
            return new Temp(source);
        }

        @Override
        public Temp[] newArray(int size) {
            return new Temp[size];
        }
    };
}
