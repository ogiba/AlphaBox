package pl.alphabox.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by ogiba on 01.07.2017.
 */

public class AppModel {
    private String name;
    private Drawable icon;

    public AppModel(String name, Drawable icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }
}
