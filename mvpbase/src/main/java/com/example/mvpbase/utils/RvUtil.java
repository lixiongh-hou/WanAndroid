package com.example.mvpbase.utils;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 16:38
 */
public class RvUtil {

    private RvUtil() {
        // no instance
    }

    /**
     * 解决嵌套问题
     */
    public static void solveNestQuestion(RecyclerView rv) {
        // 解决数据加载不全的问题
        rv.setNestedScrollingEnabled(false);
        rv.setHasFixedSize(true);
        // 解决数据加载完成后，没有停留在顶部的问题
        rv.setFocusable(false);
    }

    public static FlexboxLayoutManager getLayoutManager(Context context){
        FlexboxLayoutManager manager = new FlexboxLayoutManager(context);
        //设置主轴排列方式
        manager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setAlignItems(AlignItems.STRETCH);
        return manager;
    }


}
