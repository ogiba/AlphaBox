package pl.alphabox.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by ogiba on 01.07.2017.
 */

public class AppModel {
    private String name;
    private Drawable icon;
    private double fileSize;

    public AppModel(String name, Drawable icon, double fileSize) {
        this.name = name;
        this.icon = icon;
        this.fileSize = fileSize;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public double getSize() {
        return fileSize;
    }
}
