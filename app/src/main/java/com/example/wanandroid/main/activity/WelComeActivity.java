package com.example.wanandroid.main.activity;

import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseActivity;
import com.example.wanandroid.R;
import com.jaredrummler.android.widget.AnimatedSvgView;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 17:54
 */
@BindLayoutRes(R.layout.activity_welcom)
public class WelComeActivity extends BaseActivity {
    @BindView(R.id.textSvg)
    TextView mTextSvg;
    @BindView(R.id.imgSvg)
    AnimatedSvgView mImgSvg;
    @Override
    public void initView() {
        hideTitle();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_welcome);
        mTextSvg.startAnimation(animation);
        mImgSvg.setViewportSize(167, 220);
        mImgSvg.setTraceColor(ContextCompat.getColor(mContext, R.color.white));
        mImgSvg.start();
        new CountDownTimer(2500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
               gotoActivity(MainActivity.class);
               finish();
                overridePendingTransition(R.anim.act_main_enter_anim, 0);

            }
        }.start();
    }
}
