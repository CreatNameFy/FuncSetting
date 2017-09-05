package com.android.funcsetting;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/8/29.
 */
public interface ItemTouchHelperAdapter {
    //数据交换
   void onItemMove(int fromPosition, int toPosition);

    //数据删除
    void onItemDissmiss(int position) ;
    //drag或者swipe选中
    void onItemSelect(RecyclerView.ViewHolder viewHolder);
}
