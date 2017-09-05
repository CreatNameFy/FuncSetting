package com.android.funcsetting;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.List;

public class AppListActivity extends AppCompatActivity {
    private final int APP_LIST_RESULT = 100;
    private RecyclerView mAppsRecycler;
    private AppsData appsData;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == APP_LIST_RESULT) {
                List<AppInfo> list = (List<AppInfo>) msg.obj;
                AppListAdapter adapter = new AppListAdapter(AppListActivity.this, list);
                mAppsRecycler.setAdapter(adapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Func shortcuts");

        setContentView(R.layout.activity_app_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        appsData = (AppsData) getIntent().getExtras().getSerializable("app");
        initView();
        initData();
    }

    private void initView() {
        mAppsRecycler = (RecyclerView) findViewById(R.id.apps_recycler);
        mAppsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initData() {
        AppUtils.getAppListData(this, appsData, handler, APP_LIST_RESULT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
