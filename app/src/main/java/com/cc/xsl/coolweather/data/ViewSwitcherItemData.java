package com.cc.xsl.coolweather.data;

import android.support.annotation.DrawableRes;

public class ViewSwitcherItemData {
    private String name;
    @DrawableRes
    private int icon;

    public ViewSwitcherItemData(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
