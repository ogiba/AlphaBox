package pl.alphabox.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by robertogiba on 11.01.2018.
 */

public class UserFile implements Parcelable {
    public String userId;
    public String urlToFile;
    public String sharedByUser;
    public String appName;
    public double apkSize;
    public long shareTime;

    public UserFile() {
    }

    public UserFile(String userId, String urlToFile, String sharedByUser) {
        this.userId = userId;
        this.urlToFile = urlToFile;
        this.sharedByUser = sharedByUser;
    }

    public UserFile setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public UserFile setApkSize(double apkSize) {
        this.apkSize = apkSize;
        return this;
    }

    public UserFile setShareTime(long shareTime) {
        this.shareTime = shareTime;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.urlToFile);
        dest.writeString(this.sharedByUser);
        dest.writeString(this.appName);
        dest.writeDouble(this.apkSize);
        dest.writeLong(this.shareTime);
    }

    protected UserFile(Parcel in) {
        this.userId = in.readString();
        this.urlToFile = in.readString();
        this.sharedByUser = in.readString();
        this.appName = in.readString();
        this.apkSize = in.readDouble();
        this.shareTime = in.readInt();
    }

    public static final Parcelable.Creator<UserFile> CREATOR = new Parcelable.Creator<UserFile>() {
        @Override
        public UserFile createFromParcel(Parcel source) {
            return new UserFile(source);
        }

        @Override
        public UserFile[] newArray(int size) {
            return new UserFile[size];
        }
    };
}
