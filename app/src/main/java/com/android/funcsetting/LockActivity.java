package com.android.funcsetting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.funcsetting.db.AppDB;
import com.android.funcsetting.db.DBManager;

import java.util.List;

public class LockActivity extends AppCompatActivity implements View.OnClickListener {
    private final String DEFAULT_TABLE = "appinfo_default_list";
    private List<AppInfo> list;
    private RelativeLayout mFirstLayout;
    private ImageView mFirstIcon;
    private RelativeLayout mSecondLayout;
    private ImageView mSecondIcon;
    private RelativeLayout mThirdLayout;
    private ImageView mThirdIcon;
    private RelativeLayout mFourthLayout;
    private ImageView mFourthIcon;
    private RelativeLayout mFifthLayout;
    private ImageView mFifthIcon;
    private AppDB db;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        context=  this;
        initView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
    class OnClik implements View.OnClickListener {
        int index;
        public OnClik(int index){
            this.index=index;
        }
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.first_icon:
                    startPager(index);
//                    Toast.makeText(LockActivity.this,"first_icon111",0).show();
                    break;
                case R.id.second_icon:
                    startPager(index);
//                    Toast.makeText(LockActivity.this,"second_icon222"+index,0).show();
                    break;
                case R.id.third_icon:
                    startPager(index);
//                    Toast.makeText(LockActivity.this,"third_icon333"+index,0).show();
                    break;
                case R.id.fourth_icon:
                    startPager(index);
//                    Toast.makeText(LockActivity.this,"fourth_icon444"+index,0).show();
                    break;
                case R.id.fifth_icon:
                    startPager(index);
//                    Toast.makeText(LockActivity.this,"fifth_icon555"+index,0).show();
                    break;
            }
        }
    }
    public void startPager(int index){
        AppInfo info=list.get(index);
        Toast.makeText(LockActivity.this,info.getPackerName()+"---------"+index,0).show();
        Intent intent=new Intent(info.getPackerName());
//        context.startActivity(intent);
    }
    private void initData() {
        list = getLockIconData();
        Log.e("size","----"+list.size());
        mFirstIcon.setImageDrawable(list.get(0).getDrawable());
        mFirstIcon.setOnClickListener(new OnClik(0));
        if (list.size() == 2) {
            mFifthLayout.setVisibility(View.VISIBLE);
            mFifthIcon.setImageDrawable(list.get(1).getDrawable());
            mFifthIcon.setOnClickListener(new OnClik(1));
        }
        if (list.size() == 3) {
            mFourthLayout.setVisibility(View.VISIBLE);
            mFifthLayout.setVisibility(View.VISIBLE);
            mFourthIcon.setImageDrawable(list.get(1).getDrawable());
            mFifthIcon.setImageDrawable(list.get(2).getDrawable());
            mFourthIcon.setOnClickListener(new OnClik(1));
            mFifthIcon.setOnClickListener(new OnClik(2));
        }
        if (list.size() == 4) {
            mSecondLayout.setVisibility(View.VISIBLE);
            mFourthLayout.setVisibility(View.VISIBLE);
            mFifthLayout.setVisibility(View.VISIBLE);
            mSecondIcon.setImageDrawable(list.get(1).getDrawable());
            mFourthIcon.setImageDrawable(list.get(2).getDrawable());
            mFifthIcon.setImageDrawable(list.get(3).getDrawable());
            mSecondIcon.setOnClickListener(new OnClik(1));
            mFourthIcon.setOnClickListener(new OnClik(2));
            mFifthIcon.setOnClickListener(new OnClik(3));

        }
        if (list.size() == 5) {
            mSecondLayout.setVisibility(View.VISIBLE);
            mThirdLayout.setVisibility(View.VISIBLE);
            mFourthLayout.setVisibility(View.VISIBLE);
            mFifthLayout.setVisibility(View.VISIBLE);
            mSecondIcon.setImageDrawable(list.get(1).getDrawable());
            mThirdIcon.setImageDrawable(list.get(2).getDrawable());
            mFourthIcon.setImageDrawable(list.get(3).getDrawable());
            mFifthIcon.setImageDrawable(list.get(4).getDrawable());
            mSecondIcon.setOnClickListener(new OnClik(1));
            mThirdIcon.setOnClickListener(new OnClik(2));
            mFourthIcon.setOnClickListener(new OnClik(3));
            mFifthIcon.setOnClickListener(new OnClik(4));
        }
    }

    private void initView() {
        mFirstLayout = (RelativeLayout) findViewById(R.id.first_layout);
        mFirstIcon = (ImageView) findViewById(R.id.first_icon);
        mSecondLayout = (RelativeLayout) findViewById(R.id.second_layout);
        mSecondIcon = (ImageView) findViewById(R.id.second_icon);
        mThirdLayout = (RelativeLayout) findViewById(R.id.third_layout);
        mThirdIcon = (ImageView) findViewById(R.id.third_icon);
        mFourthLayout = (RelativeLayout) findViewById(R.id.fourth_layout);
        mFourthIcon = (ImageView) findViewById(R.id.fourth_icon);
        mFifthLayout = (RelativeLayout) findViewById(R.id.fifth_layout);
        mFifthIcon = (ImageView) findViewById(R.id.fifth_icon);
    }

    public List<AppInfo> getLockIconData() {
        DBManager manager = new DBManager(this);
        manager.openDatabase();
        db = AppDB.getInstance(this);
        list = db.getDefaultApp(DEFAULT_TABLE);
        return AppUtils.getAppInfo(this, list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.first_icon:
                Toast.makeText(LockActivity.this,"first_icon111",0).show();
                break;
            case R.id.second_icon:
                Toast.makeText(LockActivity.this,"second_icon222",0).show();
                break;
            case R.id.third_icon:
                Toast.makeText(LockActivity.this,"third_icon333",0).show();
                break;
            case R.id.fourth_icon:
                Toast.makeText(LockActivity.this,"fourth_icon444",0).show();
                break;
            case R.id.fifth_icon:
                Toast.makeText(LockActivity.this,"fifth_icon555",0).show();
                break;
        }
    }
}
