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

    public UserFile() {
    }

    public UserFile(String userId, String urlToFile, String sharedByUser) {
        this.userId = userId;
        this.urlToFile = urlToFile;
        this.sharedByUser = sharedByUser;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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
    }

    protected UserFile(Parcel in) {
        this.userId = in.readString();
        this.urlToFile = in.readString();
        this.sharedByUser = in.readString();
        this.appName = in.readString();
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
