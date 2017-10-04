
package ht.pq.khanh.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class List implements Parcelable {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = null;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.dt);
        dest.writeParcelable(this.main, flags);
        dest.writeList(this.weather);
        dest.writeParcelable(this.clouds, flags);
        dest.writeParcelable(this.wind, flags);
        dest.writeString(this.dtTxt);
    }

    public List() {
    }

    protected List(Parcel in) {
        this.dt = (Integer) in.readValue(Integer.class.getClassLoader());
        this.main = in.readParcelable(Main.class.getClassLoader());
        this.weather = new ArrayList<Weather>();
        in.readList(this.weather, Weather.class.getClassLoader());
        this.clouds = in.readParcelable(Clouds.class.getClassLoader());
        this.wind = in.readParcelable(Wind.class.getClassLoader());
        this.dtTxt = in.readString();
    }

    public static final Parcelable.Creator<List> CREATOR = new Parcelable.Creator<List>() {
        @Override
        public List createFromParcel(Parcel source) {
            return new List(source);
        }

        @Override
        public List[] newArray(int size) {
            return new List[size];
        }
    };
}
