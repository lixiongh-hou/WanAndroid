package com.example.mvpbase.loading;

import com.example.mvpbase.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 17:21
 */
public class LoadingCallBack extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }
}
