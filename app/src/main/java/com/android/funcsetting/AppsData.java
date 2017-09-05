package com.android.funcsetting;

import java.io.Serializable;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Created by Administrator on 2017/9/4.
 */
public class AppsData implements Serializable {
    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    private List<String> list;


}
