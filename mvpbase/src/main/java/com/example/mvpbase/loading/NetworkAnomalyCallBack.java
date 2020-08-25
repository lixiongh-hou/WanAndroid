package com.example.mvpbase.loading;

import com.example.mvpbase.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @author: 雄厚
 * Date: 2020/8/17
 * Time: 14:41
 */
public class NetworkAnomalyCallBack extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.network_anomaly_layout;
    }
}
