package pl.alphabox.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ogiba on 09.07.2017.
 */

public class User implements Parcelable {
    public String id;
    public String email;

    private boolean selected;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    private User(Parcel in){
        this.id = in.readString();
        this.email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
