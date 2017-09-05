package com.android.funcsetting;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.funcsetting.db.AppDB;
import com.android.funcsetting.db.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Func Setting List
 */
public class MainActivity extends AppCompatActivity {
    private final String DEFAULT_TABLE = "appinfo_default_list";
    private final String TABLE = "appinfo_list";
    public static int defaultNum=0;
    private Switch funcSettingSwitch;
    private TextView switchOff;
    private RecyclerView funcSettingList;
    private List<AppInfo> list;
    private AppDB db;
    private FuncListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        DBManager manager = new DBManager(this);
        manager.openDatabase();
        adapter = new FuncListAdapter(this, getData());
        funcSettingList.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);  //用Callback构造ItemtouchHelper
        touchHelper.attachToRecyclerView(funcSettingList);
    }

    private void initListener() {
        funcSettingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    funcSettingSwitch.setText("on");
                    funcSettingList.setVisibility(View.VISIBLE);
                    switchOff.setVisibility(View.GONE);
                } else {
                    funcSettingSwitch.setText("off");
                    funcSettingList.setVisibility(View.GONE);
                    switchOff.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initView() {
        setTitle("Func shortcuts");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        funcSettingSwitch = (Switch) findViewById(R.id.func_setting_switch);
        switchOff = (TextView) findViewById(R.id.switch_off);
        funcSettingList = (RecyclerView) findViewById(R.id.func_setting_recycler);
        funcSettingList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (funcSettingSwitch.isChecked()) {
            funcSettingSwitch.setText("on");
            switchOff.setVisibility(View.GONE);
            funcSettingList.setVisibility(View.VISIBLE);
        } else {
            funcSettingList.setVisibility(View.GONE);
            switchOff.setVisibility(View.VISIBLE);
            funcSettingSwitch.setText("off");
        }
    }
    public List<AppInfo> getData(){
        db = AppDB.getInstance(this);
        list.clear();
        list.add(AppUtils.getDefaultAppInfoTitle());
        list.addAll(db.getDefaultApp(DEFAULT_TABLE));
        defaultNum= list.size()-1;
        list.add(AppUtils.getAppInfoTitle());
        list.addAll(db.getApp(TABLE));
        list.add(AppUtils.getFooterView());
        list =AppUtils.getAppInfo(this, list);
        return list;
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        list=getData();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(list!=null&&list.size()!=0&&db!=null){
            db.delete();
            db.addAll(list);
            db.closeDB();
        }
    }
}
