package com.android.funcsetting;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/1.
 *
 */
public class AppInfo implements Serializable{
    private int icon;
    private boolean isContanin;

    public boolean isContanin() {
        return isContanin;
    }

    public void setContanin(boolean contanin) {
        isContanin = contanin;
    }

    private String appName;
    private Drawable drawable;
    private String  packerName;
    private int rightIcon;
    /*0default apps title 1:default apps 2:apps title 3:apps */
    private int index;
    public String getIntroduction() {
        if(introduction==null){
            introduction="";
        }
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    private String introduction;

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getPackerName() {
        return packerName;
    }

    public void setPackerName(String packerName) {
        this.packerName = packerName;
    }

    public int getIcon() {
        return icon;

    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getAppName() {
        if(appName==null)
            appName="";
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(int rightIcon) {
        this.rightIcon = rightIcon;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


}
