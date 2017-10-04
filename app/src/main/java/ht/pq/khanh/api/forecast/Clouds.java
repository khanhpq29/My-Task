
package ht.pq.khanh.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds implements Parcelable {

    @SerializedName("all")
    @Expose
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.all);
    }

    public Clouds() {
    }

    protected Clouds(Parcel in) {
        this.all = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Clouds> CREATOR = new Parcelable.Creator<Clouds>() {
        @Override
        public Clouds createFromParcel(Parcel source) {
            return new Clouds(source);
        }

        @Override
        public Clouds[] newArray(int size) {
            return new Clouds[size];
        }
    };
}
