package pl.alphabox.models;

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
    private String versionName;

    public AppModel(String name, String uri, Drawable icon, double fileSize) {
        this.name = name;
        this.apkUri = uri;
        this.icon = icon;
        this.fileSize = fileSize;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public double getSize() {
        return fileSize;
    }

    public String getApkUri() {
        return apkUri;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeDouble(this.fileSize);
        dest.writeString(this.apkUri);
        dest.writeString(this.versionName);
    }

    protected AppModel(Parcel in) {
        this.name = in.readString();
        this.fileSize = in.readDouble();
        this.apkUri = in.readString();
        this.versionName = in.readString();
    }

    public static final Creator<AppModel> CREATOR = new Creator<AppModel>() {
        @Override
        public AppModel createFromParcel(Parcel source) {
            return new AppModel(source);
        }

        @Override
        public AppModel[] newArray(int size) {
            return new AppModel[size];
        }
    };
}
