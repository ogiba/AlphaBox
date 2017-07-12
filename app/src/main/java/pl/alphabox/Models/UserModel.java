package pl.alphabox.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ogiba on 09.07.2017.
 */

public class UserModel implements Parcelable {
    public String email;

    public UserModel() {
    }

    public UserModel(String email) {
        this.email = email;
    }

    private UserModel(Parcel in){
        this.email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
    }

    public static final Parcelable.Creator<UserModel> CREATOR
            = new Parcelable.Creator<UserModel>() {
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
