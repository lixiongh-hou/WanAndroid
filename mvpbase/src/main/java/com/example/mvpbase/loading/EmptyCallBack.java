package com.example.mvpbase.loading;

import com.example.mvpbase.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 15:13
 */
public class EmptyCallBack extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }
}
