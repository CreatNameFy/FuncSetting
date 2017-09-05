package com.android.funcsetting;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends AppCompatActivity {
    private final int APP_LIST_RESULT = 100;
    private RecyclerView mAppsRecycler;
    private AppsData appsData;
    private List<AppInfo> list;
    private List<AppInfo> searchList;
    private AppListAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == APP_LIST_RESULT) {
                list = (List<AppInfo>) msg.obj;
                dialog.dismiss();
                adapter = new AppListAdapter(AppListActivity.this, list);
                mAppsRecycler.setAdapter(adapter);
            }
        }
    };
    private ProgressDialog dialog;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //使用菜单填充器获取menu下的菜单资源文件
        getMenuInflater().inflate(R.menu.search_item, menu);
        //获取搜索的菜单组件
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //设置搜索的事件
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("onQueryTextSubmit", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (list != null && list.size() > 0) {
                    if (searchList == null) {
                        searchList = new ArrayList<AppInfo>();
                    }
                    if (searchList.size() > 0) {
                        searchList.clear();
                    }
                    for (AppInfo info : list) {
                        if (info.getPackerName().contains(newText) ||
                                info.getAppName().contains(newText)
                                || info.getIntroduction().contains(newText)) {
                            searchList.add(info);
                        }

                    }
                    if (searchList.size() > 0 && adapter != null) {
                        adapter.updateData(searchList);
                    }else{
                        Toast.makeText(AppListActivity.this,"no search",0).show();
                    }
                }

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void initView() {
        mAppsRecycler = (RecyclerView) findViewById(R.id.apps_recycler);
        mAppsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initData() {
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.show();
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
