package pl.alphabox.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

/**
 * Created by robertogiba on 11.01.2018.
 */

public class UserFile implements Parcelable {
    public String userId;
    public String urlToFile;

    public UserFile(String userId, String urlToFile) {
        this.userId = userId;
        this.urlToFile = urlToFile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.urlToFile);
    }

    protected UserFile(Parcel in) {
        this.userId = in.readString();
        this.urlToFile = in.readString();
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
