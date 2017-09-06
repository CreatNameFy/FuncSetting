package com.android.funcsetting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/9/1.
 */
public class AppUtils {
    public static final String DEFAULT_APPS_TITLE = "Shortcut on lock screen";
    public static final String APPS_TITLE = "Available shortcut";
    public static final String FOOTER_VIEW = "add apps";
    public static final int DEFAULT_APPS_TITLE_INDEX = 0;
    public static final int DEFAULT_APPS = 1;
    public static final int APPS_TITLE_INDEX = 2;
    public static final int APPS = 3;
    public static final int FOOTER_INDEX = 4;
    public static final int APPS_LIST = 5;

    public static List<AppInfo> getAppInfo(Activity activity, List<AppInfo> list) {
        for (AppInfo info : list) {
            if (info.getIndex() == APPS || info.getIndex() == DEFAULT_APPS|| info.getIndex() == APPS_LIST) {
                PackageManager pm = activity.getApplication().getPackageManager();
                try {
                    ApplicationInfo licationInfo = pm.getApplicationInfo(info.getPackerName(), PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES);
                    info.setDrawable(licationInfo.loadIcon(pm));
                    info.setAppName(licationInfo.loadLabel(pm).toString());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    Log.e("NameNotFoundException", e.toString());
                }

            }
        }
        return list;
    }

    public static AppInfo getAppInfoTitle() {
        AppInfo info = new AppInfo();
        info.setPackerName(APPS_TITLE);
        info.setIndex(APPS_TITLE_INDEX);
        return info;
    }

    public static AppInfo getDefaultAppInfoTitle() {
        AppInfo info = new AppInfo();
        info.setPackerName(DEFAULT_APPS_TITLE);
        info.setIndex(DEFAULT_APPS_TITLE_INDEX);
        return info;
    }
    public static AppInfo getFooterView() {
        AppInfo info = new AppInfo();
        info.setPackerName(FOOTER_VIEW);
        info.setIndex(FOOTER_INDEX);
        return info;
    }

    public static void getAppListData(final Activity activity, final AppsData data, final Handler handler,final int APP_LIST_RESULT) {
//        final List<PackageInfo> packages = activity.getPackageManager().getInstalledPackages(0);
        final List<ApplicationInfo> infos = activity.getPackageManager().getInstalledApplications(1);
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<ApplicationInfo> applicationInfos=new ArrayList<>();
                // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
                Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
                resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                // 通过getPackageManager()的queryIntentActivities方法遍历,得到所有能打开的app的packageName
                List<ResolveInfo>  resolveinfoList = activity.getPackageManager()
                        .queryIntentActivities(resolveIntent, 0);
                Set<String> allowPackages=new HashSet();
                for (ResolveInfo resolveInfo:resolveinfoList){
                    allowPackages.add(resolveInfo.activityInfo.packageName);
                }
                for (ApplicationInfo app:infos) {
                    if (allowPackages.contains(app.packageName)){
                        applicationInfos.add(app);
                    }
                }

                List<AppInfo> list=new ArrayList<>();
                for (int j = 0; j < applicationInfos.size(); j++) {
                    ApplicationInfo info=applicationInfos.get(j);
                    AppInfo app=new AppInfo();
                    for (int i = 0; i < data.getList().size(); i++) {
                        if(info.packageName.equals(data.getList().get(i))) {
                            app.setContanin(true);
                            break;
                        }else{
                            app.setContanin(false);
                        }

                    }
                    app.setPackerName(info.packageName);
                    app.setIndex(APPS_LIST);
                    list.add(app);
                }
                if(list.size()>0){
                    list= getAppInfo(activity,list);
                    Message msg=Message.obtain();
                    msg.obj=list;
                    msg.what=APP_LIST_RESULT;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    public static void showDialog(final Context context, String content){
        AlertDialog.Builder dialog=new AlertDialog.Builder(context,R.style.Theme_AppCompat_Light_Dialog_Alert_Self);
        dialog.setMessage(content);
        dialog.setPositiveButton("go to it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }
    public  static void startApp(Activity activity,String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = activity.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList =activity. getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            activity.startActivity(intent);
        }
    }
}
