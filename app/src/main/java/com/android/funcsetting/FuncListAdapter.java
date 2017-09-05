package com.android.funcsetting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/29.
 */
public class FuncListAdapter extends RecyclerView.Adapter<FuncListAdapter.AppInfoHodler> implements ItemTouchHelperAdapter {
    private List<AppInfo> data;
    private Context mContext;

    public FuncListAdapter(Context context, List<AppInfo> data) {
        this.data = data;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {

        return data.get(position).getIndex();
    }

    @Override
    public AppInfoHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == AppUtils.DEFAULT_APPS_TITLE_INDEX || viewType == AppUtils.APPS_TITLE_INDEX || viewType == AppUtils.FOOTER_INDEX) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_title, parent, false);
        } else if (viewType == AppUtils.DEFAULT_APPS) {
            view = LayoutInflater.from(mContext).inflate(R.layout.default_item, parent, false);
        } else if (viewType == AppUtils.APPS) {
            view = LayoutInflater.from(mContext).inflate(R.layout.app_item, parent, false);
        }
        return new AppInfoHodler(view, viewType);
    }

    @Override
    public void onBindViewHolder(final AppInfoHodler holder, final int position) {
        final int viewType = getItemViewType(position);
        final AppInfo info = data.get(position);
        if (viewType == AppUtils.DEFAULT_APPS_TITLE_INDEX || viewType == AppUtils.APPS_TITLE_INDEX ||
                viewType == AppUtils.FOOTER_INDEX) {
            holder.title.setText(info.getPackerName());
            if (viewType == AppUtils.FOOTER_INDEX) {
                holder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> list=new ArrayList<String>();
                        for (AppInfo info:data) {
                            list.add(info.getPackerName());
                        }
                        Intent intent = new Intent(mContext, AppListActivity.class);
                        AppsData appsData=new AppsData();
                        appsData.setList(list);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("app",appsData);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
            }else if(viewType == AppUtils.DEFAULT_APPS_TITLE_INDEX ){
                holder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, LockActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            }
        } else {
            holder.app_name.setText(info.getAppName());
            holder.app_icon.setImageDrawable(info.getDrawable());
            if (viewType == AppUtils.DEFAULT_APPS) {
                holder.right_icon.setImageResource(R.mipmap.remove);
            } else if (viewType == AppUtils.APPS) {
                holder.right_icon.setImageResource(R.mipmap.add);
            }
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = "";
                    if (viewType == AppUtils.DEFAULT_APPS) {
                        if (MainActivity.defaultNum > 1) {
                            msg = "remove";
                            deleteData(position);
                        } else {
                            msg = "已经剩下最后一个了";
                        }
                    } else if (viewType == AppUtils.APPS) {
                        if (MainActivity.defaultNum == 5) {
                            msg = "默认数量已经是最大了";
                        } else {
                            msg = "add";
                            addData(position);
                        }
                    }
                    Toast.makeText(mContext, msg, 0).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (data.get(fromPosition).getIndex() == AppUtils.DEFAULT_APPS &&
                data.get(toPosition).getIndex() == AppUtils.DEFAULT_APPS) {
            if (fromPosition < data.size() && toPosition < data.size()) {
                Collections.swap(data, fromPosition, toPosition);
                notifyItemMoved(fromPosition, toPosition);
            }
            Log.e("dissmiss","------------"+fromPosition);
        }
    }

    @Override
    public void onItemDissmiss(int position) {
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder viewHolder) {

    }


    public void deleteData(int pos) {
        AppInfo info = data.get(pos);
        info.setIndex(AppUtils.APPS);
        data.remove(pos);
        data.add(data.size() - 1, info);
        MainActivity.defaultNum--;
        notifyDataSetChanged();
    }

    public void addData(int pos) {
        AppInfo info = data.get(pos);
        info.setIndex(AppUtils.DEFAULT_APPS);
        data.remove(pos);
        data.add(MainActivity.defaultNum + 1, info);
        MainActivity.defaultNum++;
        notifyDataSetChanged();
    }

    public class AppInfoHodler extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView app_name;
        public ImageView app_icon;
        public ImageView right_icon;
        public RelativeLayout item;

        public AppInfoHodler(View itemView, int viewType) {
            super(itemView);
            if (viewType == AppUtils.DEFAULT_APPS_TITLE_INDEX || viewType == AppUtils.APPS_TITLE_INDEX
                    || viewType == AppUtils.FOOTER_INDEX) {
                title = (TextView) itemView.findViewById(R.id.title);
            } else {
                app_name = (TextView) itemView.findViewById(R.id.app_name);
                right_icon = (ImageView) itemView.findViewById(R.id.right_icon);
                app_icon = (ImageView) itemView.findViewById(R.id.app_icon);
                item = (RelativeLayout) itemView.findViewById(R.id.item);
            }

        }
    }
}
