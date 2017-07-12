package pl.alphabox.Models;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ogiba on 01.07.2017.
 */

public class AppModel implements Parcelable {
    private String name;
    private double fileSize;
    private String apkUri;
    private Drawable icon;

    public AppModel(String name, String uri, Drawable icon, double fileSize) {
        this.name = name;
        this.apkUri = uri;
        this.icon = icon;
        this.fileSize = fileSize;
    }

    private AppModel(Parcel in) {
        this.name = in.readString();
        this.apkUri = in.readString();
        this.fileSize = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(apkUri);
        dest.writeDouble(fileSize);
    }

    public static final Parcelable.Creator<AppModel> CREATOR
            = new Parcelable.Creator<AppModel>() {
        public AppModel createFromParcel(Parcel in) {
            return new AppModel(in);
        }

        public AppModel[] newArray(int size) {
            return new AppModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public double getSize() {
        return fileSize;
    }

    public String getApkUri() {
        return apkUri;
    }
}
